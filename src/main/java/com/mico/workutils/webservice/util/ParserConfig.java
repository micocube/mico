package com.mico.workutils.webservice.util;

import com.mico.workutils.webservice.annotation.WebService;

import java.lang.annotation.Annotation;

/**
 * @author laids on 2017-01-09 for velocity.
 */
public class ParserConfig {

    public static String getWebServiceConfig(Class clazz){
        Annotation annotation = clazz.getAnnotation(WebService.class);
        if(null != annotation){
            WebService service = (WebService)annotation;
            return service.value();
        }else{
            return null;
        }

    }
}
