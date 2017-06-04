package com.mico.workutils.doc;

import java.util.Map;

public interface Doc {
    public Map<String, String> docDefinition();

    public Map<String,String> getDoc(String src);
}