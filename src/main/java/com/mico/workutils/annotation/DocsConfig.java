package com.mico.workutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mico on 2016-12-20.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocsConfig {
    public String auth() default "laids";
    public String cname() default "";
    public String sourceFolder() default "";
    public String packageName() default "";
    public String className() default "";

}
