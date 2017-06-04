package com.mico.workutils.doc;



import com.mico.workutils.util.StrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author laids on 2016-12-23 for velocity.
 *         获取文档的 标题,请求地址,请求方式
 */
public class ApiDoc implements Doc {

    private String title;
    private String method;
    private String url;
    private String desc;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> docDefinition() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title","(?<=\\[)(\\S+)(?=\\])");
        map.put("method","(?<=\\{)(\\S+)(?=\\})");
        map.put("url","(?<=\\}).*?(?=\\[)");
        map.put("desc","(?<=\\]).*$");
        return map;
    }

    public Map<String, String> getDoc(String src) {
        Map<String, String> map = docDefinition();
        Map<String,String> rs = new HashMap<String, String>();
        Set<String> strings = map.keySet();
        for(String str : strings){
            String result = StrUtils.regexMatchFirst(src, map.get(str));
            rs.put(str,result);
        }
        return rs;
    }



}
