package com.mico.workutils.helper;

import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.util.PropertiesUtil;
import com.mico.workutils.util.StrUtils;
import org.junit.Test;

import java.sql.Date;
import java.util.Map;
import java.util.Set;

public class EntityHelper {
    private static Map<String, Object> paramMap;

    static {
        PropertiesUtil.loads("/config/templateConfig.properties");
        paramMap = PropertiesUtil.map;
    }


    @Test
    public void test() throws Exception {


        DatabaseSchame schame = DatabaseSchame.getSchame();
        String parse = parse(schame);
        System.out.println(parse);
    }

    /**
     * 功能：生成实体类主体代码
     *
     * @param schame
     * @return
     */
    public static String parse(DatabaseSchame schame) {
        StringBuffer sb = new StringBuffer();

        sb.append("package " + paramMap.get("entityPackage") + ";\r\n");
        //判断是否导入工具包  
        if (schame.isF_util()) {
            sb.append("import java.util.Date;\r\n");
        }
        if (schame.isF_sql()) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        //注释部分  
        sb.append("   /**\r\n");
        sb.append("    * " + paramMap.get("tableName") + " " + paramMap.get("cname") + " 实体类\r\n");
        sb.append("    * " + new Date(System.currentTimeMillis()) + " " + paramMap.get("auth") + "\r\n");
        sb.append("    */ \r\n");
        //实体部分  
        sb.append("\r\n\r\npublic class " + schame.getEntityName() + "{\r\n");
        processAllAttrs(schame.getPronameKeyProtype(), schame.getPronameColcomments(), sb);//属性
        processAllMethod(schame.getPronameKeyProtype(), sb);//get set方法
        sb.append("}\r\n");

        return sb.toString();
    }


    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private static void processAllAttrs(Map<String, String> pronameProtype, Map<String, String> pronameColcomments, StringBuffer sb) {
        Set<String> keySet = pronameProtype.keySet();

        for (String key : keySet) {
            sb.append("\t //" + pronameColcomments.get(key) + "\r\n");
            sb.append("\tprivate " + pronameProtype.get(key) + " " + key + ";\r\n");
        }
        sb.append("\r\n");

    }


    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private static void processAllMethod(Map<String, String> pronameProtype, StringBuffer sb) {
        Set<String> keySet = pronameProtype.keySet();

        for (String key : keySet) {
            sb.append("\tpublic void set" + StrUtils.initCap(key) + "(" + pronameProtype.get(key) + " " +
                    key + "){\r\n");
            sb.append("\tthis." + key + "=" + key + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + pronameProtype.get(key) + " get" + StrUtils.initCap(key) + "(){\r\n");
            sb.append("\t\treturn " + key + ";\r\n");
            sb.append("\t}\r\n");
        }

    }


}
