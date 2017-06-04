package com.mico.workutils.doc;

import com.mico.workutils.util.StrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author laids on 2016-12-23 for velocity.
 */
public class ApiParam implements Doc  {
    private String param;
    private String type;
    private String desc;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> docDefinition() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("param","(?<=\\[)(\\S+)(?=\\])");
        map.put("type","(?<=\\{)(\\S+)(?=\\})");
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
