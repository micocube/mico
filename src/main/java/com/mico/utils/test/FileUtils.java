package com.mico.test;

import java.io.*;

/**
 * @author laids on 2017-01-20 for test.
 */
public class FileUtils {

    public static void write(File file,String content,String charset){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                    file), charset);
            osw.append(content);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
