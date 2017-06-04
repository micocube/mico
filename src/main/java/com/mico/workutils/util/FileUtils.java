package com.mico.workutils.util;

import com.mico.workutils.helper.VelocityHelper;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileUtils {

    public static void writeFile(File target, String str) {
        OutputStreamWriter out;
        try {
            out = new OutputStreamWriter(new FileOutputStream(target), "UTF-8");
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> getFileList(String strPath, List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(), filelist); // 获取文件绝对路径
                } else if (fileName.endsWith("java")) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }


    public static void traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                } else {
//                    System.out.println("文件:" + file2.getAbsolutePath());
                    folderNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                if(null != files)
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        fileNum++;
                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
                        folderNum++;
                    }
                }
            }
        } else {
//            System.out.println("文件不存在!");
        }
//        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

    }


    public static void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if(null != files)
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }

        } else {
           // System.out.println("文件不存在!");
        }
    }
    @Test
    public void test() throws Exception {
//        List<File> fileList = getFileList("E:\\WorkSpace\\velocity\\src\\ExportData\\java\\com\\mico", new ArrayList<File>());
//        System.out.println("args = [" + fileList + "]");
        long t1 = 0;
        long t2 = 0;
        long t3 = 0;
        long t4 = 0;
        try {
            t1 = System.currentTimeMillis();

//            traverseFolder1("C:\\");
//            t2 = System.currentTimeMillis();
//            traverseFolder2("C:\\");
            t3 = System.currentTimeMillis();

            ExecutorService pool = Executors.newCachedThreadPool();
//        File[] files = File.listRoots();
//        for(File file: files){
//            final Future<List<File>> submit = pool.submit(new CreateTask(file.getAbsolutePath()));
//
//        }

            pool.submit(new CreateTask("C:\\"));
            pool.shutdown();
            t4 = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.print("t2-t1:"+(t2-t1)+"\tt3-t2:"+(t3-t2)+"\tt4-t3:"+(t4-t3));

        }


    }

}
 class CreateTask implements Callable<List<File>> {

    private String dir;
    private List<File> fileList = new ArrayList<File>();

    public CreateTask(String dir) {
        super();
        this.dir=dir;
    }





    public List<File> call()  {
        final long timeMillis = System.currentTimeMillis();
        File file = new File(this.dir);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                } else {
//                    System.out.println("文件:" + file2.getAbsolutePath());
                    this.fileList.add(file2);
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                if(null != files)
                    for (File file2 : files) {
                        if (file2.isDirectory()) {
//                            System.out.println("文件夹:" + file2.getAbsolutePath());
                            list.add(file2);
                        } else {
                            this.fileList.add(file2);
//                            System.out.println("文件:" + file2.getAbsolutePath());
                        }
                    }
            }
        } else {
//            System.out.println("文件不存在!");
        }
        final long timeMillis1 = System.currentTimeMillis();
        System.out.print("ssssssssssss"+(timeMillis1-timeMillis));
        return fileList;
    }

}