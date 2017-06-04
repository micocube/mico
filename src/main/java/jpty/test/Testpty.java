package jpty.test;



import jpty.JPty;
import jpty.Pty;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by mico on 17-4-26.
 *
 * @author laids
 * @email: 15170092060@163.com
 * @date: 17-4-26
 * @time : 上午9:27
 * @project: coding
 */
public class Testpty {
    // The command to run in a PTY...
    static String[] cmd = { "/bin/bash","-l"};
    // The initial environment to pass to the PTY child process...
    static String[] env = { "TERM=xterm" };

    final  static CountDownLatch latch = new CountDownLatch(1);





    static Pty pty;




    @Test
    public void test() throws Exception{


         pty = JPty.execInPTY( cmd[0], cmd, env );



        // Asynchronously check whether the output of the process is captured
        // properly...
        Thread t1 = new Thread() {
            public void run() {
                InputStream is = pty.getInputStream();

                try {
                    int ch;
                    while (pty.isChildAlive() && (ch = is.read()) >= 0) {
                        if (ch >= 0) {
                            System.out.write(ch);
                            System.out.flush();
                        }
                    }

                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        // Asynchronously wait for a little while, then close the Pty, which should
        // force our child process to be terminated...
        Thread t2 = new Thread() {
            public void run() {
                OutputStream os = pty.getOutputStream();

                try {
                    int ch;
                    while (pty.isChildAlive() && (ch = System.in.read()) >= 0) {
                        if (ch >= 0) {
                            os.write(ch);
                            os.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();




        t1.join();
        t2.join();
        latch.await(600, TimeUnit.HOURS);
    }
}
