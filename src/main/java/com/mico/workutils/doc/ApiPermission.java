package com.mico.workutils.doc;

import com.mico.workutils.util.StrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author laids on 2016-12-23 for velocity.
 */
public class ApiPermission implements Doc {
    private String permissionName;
    private String desc;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> docDefinition() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("permissionName","(?<=\\[)(\\S+)(?=\\])");
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
