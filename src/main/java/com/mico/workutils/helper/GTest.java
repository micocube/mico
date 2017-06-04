package com.mico.workutils.helper;

import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.util.PropertiesUtil;
import com.mico.workutils.util.StrUtils;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mico on 2016-11-26.
 */
public class GTest {

    private static Map<String, Object> paramMap;
    private static Map<String, File> taskMap = new HashMap<String, File>();

    static {
        PropertiesUtil.loads("/config/templateConfig.properties","/database.properties","/batchMapper.properties");
        paramMap = PropertiesUtil.map;
        // 根据表名获取实体类名
        paramMap.put("entityName", StrUtils.processDataBaseLable(paramMap.get("tableName").toString(), true));
        // 创建时间
        paramMap.put("createTime", new Date(System.currentTimeMillis()).toString());
        // 通用dao的名称
        paramMap.put("basicDaoName", paramMap.get("basicDao").toString()
                .substring(paramMap.get("basicDao").toString().lastIndexOf(".") + 1));
        // 通用daoImpl的名称
        paramMap.put("basicDaoImplName", paramMap.get("basicDaoImpl").toString()
                .substring(paramMap.get("basicDaoImpl").toString().lastIndexOf(".") + 1));
        // 通用serevice的名称
        paramMap.put("basicServiceName", paramMap.get("basicService").toString()
                .substring(paramMap.get("basicService").toString().lastIndexOf(".") + 1));
        // 通用serviceImpl的名称
        paramMap.put("basicServiceImplName", paramMap.get("basicServiceImpl").toString()
                .substring(paramMap.get("basicServiceImpl").toString().lastIndexOf(".") + 1));
        // 小写的实体类名
        paramMap.put("smallEntityName", StrUtils.processDataBaseLable(paramMap.get("tableName").toString(), false));
    }


    @Test
    public void test() throws Exception{
        DatabaseSchame schame = DatabaseSchame.getSchame();
        System.out.println("args = [" + schame + "]");
        paramMap.put("primaryKeyName", schame.getPrimaryKeyName());
        paramMap.put("columns", schame.getColumns());
        paramMap.put("colnameProname", schame.getColnameProname());
        paramMap.put("propertiesType", schame.getPronameProtype());
        paramMap.put("pronameKeyProtype", schame.getPronameKeyProtype());
        paramMap.put("propertiesComments", schame.getPronameColcomments());
        String batchSelectVagueField = MapUtils.getString(paramMap, "batchSelectVagueField");
        String[] split = batchSelectVagueField.split(",");
        Set<String> set = new HashSet<String>();
        for (String str : split){
            set.add(str);
        }
        paramMap.put("batchSelectVagueField",set);
        String batchSelectBatchFetchField = MapUtils.getString(paramMap, "batchSelectBatchFetchField");
        String[] batchFetchField = batchSelectBatchFetchField.split(",");
        Set<String> batchFetchFields = new HashSet<String>();
        for (String str : split){
            batchFetchFields.add(str);
        }
        paramMap.put("batchSelectBatchFetchField",batchFetchFields);

        String batchTimeSectionQueryField = MapUtils.getString(paramMap, "batchTimeSectionQueryField");
        String[] timeSectionQueryField = batchTimeSectionQueryField.split(",");
        Set<String> timeSectionQueryFields = new HashSet<String>();
        for (String str : split){
            timeSectionQueryFields.add(str);
        }
        paramMap.put("batchTimeSectionQueryField",timeSectionQueryFields);

        String str = VelocityHelper.createByTemplate("batchMapper.vm", paramMap, null);
        System.out.println(str);
        System.out.print(Double.MAX_VALUE);

    }
}
