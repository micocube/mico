package com.mico.workutils.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ObjectUtils {
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null)
            return null;
        Object obj = null;
        try {
            obj = beanClass.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Object stringMapToObject(Map<String, String> map, Class<?> beanClass) {
        if (map == null)
            return null;
        Object obj = null;
        try {
            obj = beanClass.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null)
            return null;
        return new org.apache.commons.beanutils.BeanMap(obj);
    }
}