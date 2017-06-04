package com.mico.workutils.doc;



import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.annotation.DocsConfig;
import com.mico.workutils.annotation.MetaDocConfig;
import com.mico.workutils.helper.VelocityHelper;
import com.mico.workutils.util.FileUtils;
import com.mico.workutils.util.ObjectUtils;
import com.mico.workutils.util.StrUtils;
import com.mico.workutils.util.Validator;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author laids on 2016-12-24 for velocity.
 */

@MetaDocConfig(sourceFolder = "E:\\WorkSpace\\velocity\\src\\ExportData\\java", packageName = "com.mico.doc")
@DocsConfig(cname = "文档工具", sourceFolder = "E:\\WorkSpace\\velocity\\src\\ExportData\\java",
        packageName = "com.mico.helper", className = "DocHelper"
)
public class DocHelper {

    public static Logger log = Logger.getLogger(DocHelper.class.getName());
    public static Map<String, Object> nameCommand = new HashMap<String, Object>();

    @Test
    public void testDoc() throws Exception {
        MetaDocConfig annotation = DocHelper.class.getAnnotation(MetaDocConfig.class);
        String sourceFolder = annotation.sourceFolder();
        String packageName = annotation.packageName();
        List<File> fileList = FileUtils.getFileList(sourceFolder + File.separator + packageName.replace(".", File.separator), new ArrayList<File>());
        try {
            for (File file : fileList) {
                String name = file.getName();
                int index = -1;
                if (-1 != (index = name.indexOf("."))) {
                    name = name.substring(0, index);
                    Class<?> forName = Class.forName(packageName + "." + name);
                    if (!Modifier.isInterface(forName.getModifiers())) {
                        Object command = forName.newInstance();
                        nameCommand.put(name, command);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        DocsConfig dc =DocHelper.class.getAnnotation(DocsConfig.class);
        StringBuffer soureFile = getSoureFile(dc.sourceFolder(), dc.packageName(), dc.className());
        Stack<Validator.Pair> sc = new Stack<Validator.Pair>();
        boolean pair = Validator.isPair(sc, soureFile.toString(), "/**", "*/");

        if(!pair){
            log.info("注释非文档注释,请检查规范！");
            return;
        }
        List<String> allNote = getAllNote(soureFile, "/**", "*/");

        List<StringBuffer> allDoc = new ArrayList<StringBuffer>();

        for (String note : allNote) {
            if (note.contains("@api")) {
                note = note.replace("/**", "");
                note = note.replace("*/", "");
                note = note.replace("*", "");
                allDoc.add(new StringBuffer(note));
            } else {
                continue;
            }
        }

        Set<String> commands = nameCommand.keySet();
        Map<String, List<String>> stringApi = null;
        Map<String, Map<String, String>> docs = new HashMap<String, Map<String, String>>();
        List<Map<String, List<Object>>> result = new ArrayList<Map<String, List<Object>>>();
        for (StringBuffer sb : allDoc) {
            stringApi = new HashMap<String, List<String>>();
            List<String> noteList = getDocUnit(sb.toString());
            for (String cm : commands) {
                for (String str : noteList) {

                    String upper = str.trim().toUpperCase();
                    String cmdUpper = cm.toUpperCase();
                    boolean b = upper.startsWith("@" + cmdUpper);
                    if (b) {
                        if (str.contains(StrUtils.getLineSeparator())) {
                            str = str.replace(StrUtils.getLineSeparator(), "<br/>");
                        }
                        if (null != stringApi.get(cm)) {
                            List<String> maps = stringApi.get(cm);
                            maps.add(str);
                        } else {
                            List<String> maps = new ArrayList<String>();
                            maps.add(str);
                            stringApi.put(cm, maps);
                        }
                    }
                }
            }
            /**
             * 一个注释块的内容,比如一个源文件可能会有多个API注释块，就会有多个这种map
             */
            Map<String, List<Object>> stringListMap = generateDoc(stringApi);
            result.add(stringListMap);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("api", result);
        System.out.println(map);
        /**
         * {api=[
         *       //apidoc 列表
         *       {
         *        ApiDoc=[com.mico.doc.ApiDoc@589a111f],
         *        ApiParam=[com.mico.doc.ApiParam@6ada37aa],
         *        ApiSuccess=[
         *                      com.mico.doc.ApiSuccess@1c7cb0b4,
         *                      com.mico.doc.ApiSuccess@3e350808,
         *                      com.mico.doc.ApiSuccess@ba26520,
         *                      com.mico.doc.ApiSuccess@529d1dd7,
         *                      com.mico.doc.ApiSuccess@491238a0
         *                     ],
         *       ApiPermission=[com.mico.doc.ApiPermission@4b43b5da]
         *       },
         *
         *       //第二个apidoc
         *       {
         *       ApiDoc=[com.mico.doc.ApiDoc@4a5821d0],
         *       ApiParam=[
         *                 com.mico.doc.ApiParam@601404d1,
         *                 com.mico.doc.ApiParam@4248669e,
         *                 com.mico.doc.ApiParam@1f493111,
         *                 com.mico.doc.ApiParam@7e346322,
         *                 com.mico.doc.ApiParam@21588de9,
         *                 com.mico.doc.ApiParam@3ccccd19
         *                 ],
         *       ApiSuccess=[
         *                 com.mico.doc.ApiSuccess@29ad437e,
         *                 com.mico.doc.ApiSuccess@2e2a1a92,
         *                 com.mico.doc.ApiSuccess@4e367eeb,
         *                 com.mico.doc.ApiSuccess@5bc69e6a,
         *                 com.mico.doc.ApiSuccess@4cca7038,
         *                 com.mico.doc.ApiSuccess@7e8d93ea
         *                 ],
         *       ApiPermission=[com.mico.doc.ApiPermission@ea288f]
         *       }
         *    ]
         * }
         */
        String byTemplate = VelocityHelper.createByTemplate("/doc.vm", map, null);
        System.out.println(byTemplate);


//        String createByTemplate = VelocityHelper.createByTemplate("/interfaceDoc.vm", paramMap, null);

    }


    public static Map<String, List<Object>> generateDoc(Map<String, List<String>> cmdStr) {
        Map<String, List<Object>> rmap = new HashMap<String, List<Object>>();
        Set<String> cmds = cmdStr.keySet();
        try {
            for (String cmd : cmds) {
                Doc api = (Doc) nameCommand.get(cmd);
                List<String> strs = cmdStr.get(cmd);
                for (String str : strs) {

                    Map<String, String> result = api.getDoc(str);
                    Set<String> keys = result.keySet();
                    for (String key : keys) {
                        if (null != result.get(key)) {
                            String replace = result.get(key).replace("<br/>", "");
                            result.put(key, replace.trim());
                        }
                    }

                    Object apiObject = ObjectUtils.stringMapToObject(result, api.getClass());

                    /**
                     * ApiPara 和ApiSuccess特殊处理
                     */
                    if (apiObject instanceof ApiParam || apiObject instanceof ApiSuccess) {
                        String type = "";
                        List<Map<String,Object>> docs = null;
                        Class target;
                        if (apiObject instanceof ApiParam) {
                            ApiParam apiParam = (ApiParam) apiObject;
                            type = apiParam.getType();
                            target = ApiParam.class;
                        } else {
                            ApiSuccess apiParam = (ApiSuccess) apiObject;
                            type = apiParam.getType();
                            target = ApiSuccess.class;
                        }


                        if (type.contains("@")) {
                            String entityName = type.replace("@", "");
                            docs = processEntityParam(entityName);
                            if (null != docs && docs.size() > 0) {
                                List<Object> objs = new ArrayList<Object>();
                                for(Map<String,Object> map : docs){
                                    objs.add(ObjectUtils.mapToObject(map, target));
                                }
                                if (null != rmap.get(cmd)) {
                                    rmap.get(cmd).addAll(objs);
                                } else {
                                    rmap.put(cmd, objs);
                                }
                            }
                        }else{
                            if (null != rmap.get(cmd)) {
                                rmap.get(cmd).add(apiObject);
                            } else {
                                List<Object> maps = new ArrayList<Object>();
                                maps.add(apiObject);
                                rmap.put(cmd, maps);
                            }
                        }


                        continue;
                    }


                    /**
                     * 非实体参数
                     */
                    if (null != rmap.get(cmd)) {
                        rmap.get(cmd).add(apiObject);
                    } else {

                        List<Object> maps = new ArrayList<Object>();
                        maps.add(apiObject);
                        rmap.put(cmd, maps);


                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rmap;
    }

    /**
     * @ 测试注释1
     */
    public static StringBuffer getSoureFile(String basePath, String packageName, String classNmae) {
        try {
            String replace = packageName.replace(".", File.separator);
            File file = new File(basePath + File.separator + replace + File.separator + classNmae + ".java");
            FileInputStream fr = new FileInputStream(file);
            byte[] buffer = new byte[fr.available()];
            fr.read(buffer);

            return new StringBuffer(new String(buffer, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String,Object>> processEntityParam(String entityName) {
        DatabaseSchame schame = DatabaseSchame.getSchame(StrUtils.entityName2TableName(entityName));
        Map<String, String> pronameKeyProtype = schame.getPronameKeyProtype();
        Map<String, String> pronameColcomments = schame.getPronameColcomments();
        Set<String> keySet = pronameKeyProtype.keySet();
        List<Map<String,Object>> docs = new ArrayList<Map<String,Object>>();


        for (String key : keySet) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("param", key);
            map.put("type", pronameKeyProtype.get(key));
            map.put("desc", pronameColcomments.get(key));

            docs.add(map);
        }


        return docs;
    }

    public static List<String> getAllNote(StringBuffer source, String startTag, String endTag) {
        List<String> list = new ArrayList<String>();
        int index = 0;
        int end = 0;
        while (-1 != end && -1 != (index = source.indexOf(startTag, end))) {
            int temp = source.indexOf(endTag, index + endTag.length());
            if (-1 != temp) {
                end = temp + 2;
                String doc = source.substring(index, end);
                list.add(doc);
            } else {
                end = temp;
            }
        }
        return list;
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

                log.info("一行 = [" + trim + "]");
                //总长度
                int s = str.length();
                //开始
                if (trim.startsWith("@api")) {//xia个注释
                    endIndex = index;

                    if (endIndex != startIndex) {
                        list.add(str.substring(startIndex, endIndex));
                    }
                    startIndex = index;
                } else if ((index + line.length()) == s) {
                    list.add(str.substring(startIndex, index + line.length()));
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
