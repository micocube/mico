package com.mico.workutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mico on 2016-12-19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JdbcConnection {
    /**
     * 数据表名称注解
     * @return
     */
    public String url() ;
    public String userName();
    public String password();
    public String tableName();
    public String tablePrimaryKey();
    public String jdbcDriver();
    public String entityName() default "";
    public String ignoreJdbcColumn() default "";
}