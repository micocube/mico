package com.mico.utils.process;
/**
* LocalCommandExecutor.java
*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.*;

public class LocalCommandExecutor {

    static final Logger logger = LoggerFactory.getLogger(LocalCommandExecutor.class);

    static ExecutorService pool = Executors.newSingleThreadExecutor();


//    static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS,
//            new SynchronousQueue<Runnable>());

    public ExecuteResult executeCommand(String command, long timeout) {
        Process process = null;
        InputStream pIn = null;
        InputStream pErr = null;
        StreamGobbler outputGobbler = null;
        StreamGobbler errorGobbler = null;
        Future<Integer> executeFuture = null;
        try {
            logger.info(command.toString());
            process = Runtime.getRuntime().exec(command);
            final Process p = process;

            // close process's output stream.
            p.getOutputStream().close();

            pIn = process.getInputStream();
            outputGobbler = new StreamGobbler(pIn, "OUTPUT");
            outputGobbler.start();

            pErr = process.getErrorStream();
            errorGobbler = new StreamGobbler(pErr, "ERROR");
            errorGobbler.start();

            // create a Callable for the command's Process which can be called by an Executor
            Callable<Integer> call = new Callable<Integer>() {
                public Integer call() throws Exception {
                    p.waitFor();
                    return p.exitValue();
                }
            };

            // submit the command's call and get the result from a
            executeFuture = pool.submit(call);
            int exitCode = executeFuture.get(timeout, TimeUnit.MILLISECONDS);
            return new ExecuteResult(exitCode, outputGobbler.getContent());

        } catch (IOException ex) {
            String errorMessage = "The command [" + command + "] execute failed.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } catch (TimeoutException ex) {
            String errorMessage = "The command [" + command + "] timed out.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } catch (ExecutionException ex) {
            String errorMessage = "The command [" + command + "] did not complete due to an execution error.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } catch (InterruptedException ex) {
            String errorMessage = "The command [" + command + "] did not complete due to an interrupted error.";
            logger.error(errorMessage, ex);
            return new ExecuteResult(-1, null);
        } finally {
            if (executeFuture != null) {
                try {
                    executeFuture.cancel(true);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            if (pIn != null) {
                this.closeQuietly(pIn);
                if (outputGobbler != null && !outputGobbler.isInterrupted()) {
                    outputGobbler.interrupt();
                }
            }
            if (pErr != null) {
                this.closeQuietly(pErr);
                if (errorGobbler != null && !errorGobbler.isInterrupted()) {
                    errorGobbler.interrupt();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void closeQuietly(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            logger.error("exception", e);
        }
    }

    /**
     * 执行结果封装
     */
    public static class ExecuteResult {
        private int exitCode;
        private String executeOut;

        public ExecuteResult(int exitCode, String executeOut) {
            this.exitCode = exitCode;
            this.executeOut = executeOut;
        }

        public int getExitCode() {
            return exitCode;
        }

        public void setExitCode(int exitCode) {
            this.exitCode = exitCode;
        }

        public String getExecuteOut() {
            return executeOut;
        }

        public void setExecuteOut(String executeOut) {
            this.executeOut = executeOut;
        }

        @Override
        public String toString() {
            return "ExecuteResult{" +
                    "exitCode=" + exitCode +
                    ", executeOut='" + executeOut + '\'' +
                    '}';
        }
    }

    /**
     * StreamGobbler类，用来完成stream的管理
     */
    public static class StreamGobbler extends Thread {
        private static Logger logger = LoggerFactory.getLogger(StreamGobbler.class);
        private InputStream inputStream;
        private String streamType;
        private StringBuilder buf;
        private volatile boolean isStopped = false;

        /**
         * @param inputStream the InputStream to be consumed
         * @param streamType  the stream type (should be OUTPUT or ERROR)
         */
        public StreamGobbler(final InputStream inputStream, final String streamType) {
            this.inputStream = inputStream;
            this.streamType = streamType;
            this.buf = new StringBuilder();
            this.isStopped = false;
        }

        /**
         * Consumes the output from the input stream and displays the lines consumed
         * if configured to do so.
         */
        @Override
        public void run() {
            try {
                // 默认编码为UTF-8
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    this.buf.append(line + "\n");
                }
            } catch (IOException ex) {
                logger.trace("Failed to successfully consume and display the input stream of type " + streamType + ".", ex);
            } finally {
                this.isStopped = true;
                synchronized (this) {
                    notify();
                }
            }
        }

        public String getContent() {
            if (!this.isStopped) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ignore) {
                        ignore.printStackTrace();
                    }
                }
            }
            return this.buf.toString();
        }
    }
}



