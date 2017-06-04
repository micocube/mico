package com.mico.workutils.helper;

import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.DatabaseOperation.Select;
import com.mico.workutils.annotation.DocsConfig;
import com.mico.workutils.annotation.MappersConfig;
import com.mico.workutils.util.StrUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;


@DocsConfig(cname = "文档工具", sourceFolder = "E:\\WorkSpace\\velocity\\src\\ExportData\\java",
        packageName = "com.mico.helper", className = "DocHelper"
)
public class DocHelper {
    private static Map<String, Object> paramMap = new HashMap<String, Object>();

    static {
        DocsConfig annotation = DocHelper.class.getAnnotation(DocsConfig.class);
        paramMap.put("sourceFolder", annotation.sourceFolder());
        paramMap.put("auth", annotation.auth());
        paramMap.put("cname", annotation.cname());
        paramMap.put("packageName", annotation.packageName());
        paramMap.put("sourceFolder", annotation.sourceFolder());
        paramMap.put("className", annotation.className());
    }


    /**
     * 类名和类注释对应
     * 方法和方法注释对应
     */

    @Test
    private void test() throws Exception{
        DatabaseSchame schame = DatabaseSchame.getSchame();

        paramMap.put("tableName", schame.getTableName());
        paramMap.put("primaryKeyName", schame.getPrimaryKeyName());
        paramMap.put("columns", schame.getColumns());
        paramMap.put("columnMap", schame.getColnameProname());
        paramMap.put("propertiesType", schame.getPronameProtype());
        paramMap.put("pronameKeyProtype", schame.getPronameKeyProtype());
        paramMap.put("propertiesComments", schame.getPronameColcomments());
        paramMap.put("entityName", schame.getEntityName());
        paramMap.put("smallEntityName", StrUtils.initCap(schame.getEntityName()));
        paramMap.put("jdbcPrimaryKey", schame.getJdbcPrimaryKey());

        StringBuffer soureFile = getSoureFile(paramMap.get("sourceFolder").toString(), paramMap.get("packageName").toString(), paramMap.get("className").toString());


        List<String> allNote = getAllNote(soureFile,"/**","*/");

        List<StringBuffer> allDoc = new ArrayList<StringBuffer>();



        System.out.println(allNote);


        String createByTemplate = VelocityHelper.createByTemplate("/interfaceDoc.vm", paramMap, null);

        //System.out.println(createByTemplate);


    }


    public static StringBuffer generateDoc(String note){
        StringBuffer sb = new StringBuffer();

        BufferedReader reader = new BufferedReader(new StringReader(note));
        note.indexOf("");


        return  sb;
    }

    public static StringBuffer getSoureFile(String basePath, String packageName, String classNmae) {
        try {
            String replace = packageName.replace(".", File.separator);
            File file = new File(basePath + File.separator + replace + File.separator + classNmae + ".java");
            FileReader fr = new FileReader(file);
            char[] buffer = new char[1024];
            StringBuffer sb = new StringBuffer();
            String str = "";
            while (-1 != (fr.read(buffer))) {
                str = new String(buffer);
                sb.append(str);
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllNote(StringBuffer source,String startTag,String endTag) {
        List<String> list = new ArrayList<String>();
        int index = 0;
        int end = 0;
        while (-1 != end && -1!=(index = source.indexOf(startTag, end))) {
            int temp = source.indexOf(endTag, index);
            if(-1!=temp){
                end = temp+2;
                String doc = source.substring(index, end);
                list.add(doc);

            }else{
                end = temp;
            }
        }
        return list;
    }


    /**
     * @apiDoc {post} /admin/user/list [测试文档] 测试
     *只有使用@api标注的注释块才会在解析之后生成文档，title会被解析为导航菜单(@apiGroup)下的小菜单
     *method可以有空格，如{POST GET}
     *@apiParam {Integer} [testId] 测试id
     *@apiSuccess {@mm_order} [订单] 订单
     *@apiPermission [name]
     *name必须独一无二，描述@api的访问权限，如admin/anyone
     */

    public Integer test2(Integer test, Integer test2,Select select) {

        return 0;
    }

    /**
     * @apiDoc {post} /admin/user/list [用户管理] 查询用户
     *只有使用@api标注的注释块才会在解析之后生成文档，title会被解析为导航菜单(@apiGroup)下的小菜单
     *method可以有空格，如{POST GET}
     *@apiParam {@mmOrderBill} [testId] 测试id
     *@apiParam {Integer} [testId2] 测试id2
     *@apiPermission [name]
     *name必须独一无二，描述@api的访问权限，如admin/anyone
     *@apiSuccess {@mmDeliveryHistory} [礼品实体] 礼品实体
     *@apiSuccess {Integer} [giftId] 实体id
     */
    public void test3(){

    }
}
