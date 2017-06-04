package com.mico.workutils.helper;

import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.annotation.MappersConfig;
import com.mico.workutils.util.StrUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@MappersConfig(entityPackage = "com.jtyjy.mmhome.domain")
public class MapperHelper {
    private static Map<String, Object> paramMap = new HashMap<String, Object>();
    static {
        MappersConfig annotation = MapperHelper.class.getAnnotation(MappersConfig.class);
        paramMap.put("sourceFolder",annotation.sourceFolder());
        paramMap.put("auth",annotation.auth());
        paramMap.put("cname",annotation.cname());
        paramMap.put("entityPackage",annotation.entityPackage());
    }

    @Test
    public void test() throws Exception {
        DatabaseSchame schame = DatabaseSchame.getSchame();

        paramMap.put("tableName",schame.getTableName());
        paramMap.put("primaryKeyName", schame.getPrimaryKeyName());
        paramMap.put("columns", schame.getColumns());
        paramMap.put("columnMap", schame.getColnameProname());
        paramMap.put("propertiesType", schame.getPronameProtype());
        paramMap.put("pronameKeyProtype", schame.getPronameKeyProtype());
        paramMap.put("propertiesComments", schame.getPronameColcomments());
        paramMap.put("entityName",schame.getEntityName());
        paramMap.put("smallEntityName", StrUtils.initCap(schame.getEntityName()));
        paramMap.put("jdbcPrimaryKey",schame.getJdbcPrimaryKey());


        String createByTemplate = VelocityHelper.createByTemplate("/mappers.vm", paramMap, null);

        System.out.println(createByTemplate);

    }

}
