package com.mico.workutils.doc;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author laids on 2016-12-24 for velocity.
 */
public class TestDoc {
    @Test
    public void test() throws Exception {
        String str = "@apiDoc {post} /admin/user/list [用户管理] 查询用户\r\n" +
                "只有使用@api标注的注释块才会在解析之后生成文档，title会被解析为导航菜单(@apiGroup)下的小菜单\r\n" +
                "method可以有空格，如{POST GET}\r\n" +
                "@apiParam {@table mm_gift} [testId] 测试id\r\n'" +
                "@apiParam {Integer} [testId2] 测试id2\r\n" +
                "@apiPermission name\r\n" +
                "name必须独一无二，描述@api的访问权限，如admin/anyone";
        List<String> list = getDocUnit(str);

        System.out.println("args = " + list + "");
//        String target = "北京市(海淀区)(朝阳区)\r\n(西城区)".replace(File.pathSeparatorChar + "", "<br/>");
//        String regex = "(?<=\\().*$";
//        String s = StrUtils.regexMatchFirst(target, regex);
//        System.out.print(s);

        ApiParam api = new ApiParam();
        Map<String, String> doc = api.getDoc("@apiParam {@mm_gift} [testId] 测试id");

        System.out.println(doc);

    }

    public static List<String> getDocUnit(String str) {
        BufferedReader br = new BufferedReader(new StringReader(str));
        String line = null;
        int startIndex = 0;
        int endIndex = 0;
        List<String> list = new ArrayList<String>();
        try {
            line = br.readLine();
            while (null != line) {
                //当前行的开始下标
                Integer index = str.indexOf(line);

                String trim = line.trim();

                System.out.println("一行 = [" + trim + "]");
                //总长度
                int s = str.length();
                //开始
                if (trim.startsWith("@api")) {//xia个注释
                    endIndex = index;

                    if (endIndex != startIndex) {
                        list.add(str.substring(startIndex, endIndex));
                    }
                    startIndex = index;
                }

                line = br.readLine();
                if (null == line) {
                    list.add(str.substring(endIndex));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
