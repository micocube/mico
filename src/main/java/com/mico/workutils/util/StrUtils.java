package com.mico.workutils.util;

import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 功能：将输入字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    public static String initLowerCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }

        return new String(ch);
    }

    /**
     * 处理jdbc的列名，具体需求而定
     *
     * @param str
     * @param capFirst 是否大写第一个字母
     * @return 处理后的名称
     */
    public static String processDataBaseLable(String str, boolean capFirst) {
        StringBuffer sb = new StringBuffer();
        String[] split = str.split("_");
        if (split.length != 1) {
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    if (capFirst) {
                        sb.append(StrUtils.initCap(split[i]));
                    } else {
                        sb.append(split[i]);
                    }
                } else {
                    sb.append(StrUtils.initCap(split[i]));
                }
            }
        } else {
            if (capFirst) {
                sb.append(StrUtils.initCap(str));
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 以大写字母分割字符串
     *
     * @param args 目标字符串
     * @return 以大写字符分割的字符串数组
     */
    public static String[] splitWithCapital(String args) {
        return args.split("(?<!^)(?=[A-Z])");
    }

    /**
     * 实体类名到表名，遇到大写字母就分割
     * @param args
     * @return
     */
    public static String entityName2TableName(String args) {
        String[] strings = splitWithCapital(args);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            if (i == strings.length - 1) {
                sb.append(initLowerCase(str));
            }else{
                sb.append(initLowerCase(str));
                sb.append("_");
            }
        }
        return sb.toString();
    }

    @Test
    public void test() throws Exception {

        String mmMessage = entityName2TableName("mmUserMessage");
        System.out.println(mmMessage);
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    public static String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("double")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        } else if (sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        }

        return null;
    }


    public static File getPathFromPackage(String basePath, String packageName, String fileName) {
        String replaceAll = packageName.replace(".", File.separator);
        File file = new File(basePath + File.separator + replaceAll);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(basePath + File.separator + replaceAll + File.separator + fileName);
        return file;
    }

    /**
     * @param source
     * @param regex
     * @return
     */
    public static String regexMatchFirst(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        String str = null;
        if (matcher.find()) {
            str = matcher.group();
        }
        return str;
    }


    /**
     * 获取换行符
     * @return
     */
    public static String getLineSeparator() {
        String lineSeparator = System.getProperty("line.separator");
        return lineSeparator;
    }

}
