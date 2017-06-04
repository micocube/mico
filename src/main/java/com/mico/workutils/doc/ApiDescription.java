package com.mico.workutils.doc;


import com.mico.workutils.util.StrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author laids on 2016-12-23 for velocity.
 */
public class ApiDescription implements Doc {
    public Map<String, String> docDefinition() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title","(?<=\\[)(\\S+)(?=\\])");
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
