package com.mico.workutils.helper;



import com.mico.workutils.DatabaseOperation.DatabaseSchame;
import com.mico.workutils.util.PropertiesUtil;
import com.mico.workutils.util.StrUtils;
import org.junit.Test;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CodeHelper {
	private static Map<String, Object> paramMap;
	private static Map<String, File> taskMap = new HashMap<String, File>();

	static {
		PropertiesUtil.loads("/config/templateConfig.properties");
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
    public void test() throws Exception {

		DatabaseSchame schame = DatabaseSchame.getSchame();

		paramMap.put("primaryKeyName", schame.getPrimaryKeyName());
		paramMap.put("columns", schame.getColumns());
		paramMap.put("columnMap", schame.getColnameProname());
		paramMap.put("propertiesType", schame.getPronameProtype());
		paramMap.put("pronameKeyProtype", schame.getPronameKeyProtype());
		paramMap.put("propertiesComments", schame.getPronameColcomments());

//		taskMap.put("/dao.vm", StrUtils.getPathFromPackage(paramMap.get("projectSrcPath").toString(),
//				paramMap.get("daoPackage").toString(), paramMap.get("entityName") + "Dao.java"));
//		taskMap.put("/daoImpl.vm", StrUtils.getPathFromPackage(paramMap.get("projectSrcPath").toString(),
//				paramMap.get("daoImplPackage").toString(), paramMap.get("entityName") + "DaoImpl.java"));
//		taskMap.put("/service.vm", StrUtils.getPathFromPackage(paramMap.get("projectSrcPath").toString(),
//				paramMap.get("servicePackage").toString(), paramMap.get("entityName") + "Service.java"));
//		taskMap.put("/serviceImpl.vm", StrUtils.getPathFromPackage(paramMap.get("projectSrcPath").toString(),
//				paramMap.get("serviceImplPackage").toString(), paramMap.get("entityName") + "ServiceImpl.java"));
//		taskMap.put("/controller.vm", StrUtils.getPathFromPackage(paramMap.get("projectSrcPath").toString(),
//				paramMap.get("controllerPackage").toString(), paramMap.get("entityName") + "Controller.java"));
//		taskMap.put("/mappers.vm", StrUtils.getPathFromPackage(paramMap.get("mappersPath").toString(),
//				paramMap.get("mappersPackage").toString(), paramMap.get("entityName") + ".xml"));
//		taskMap.put("/interfaceDoc.vm", null);
		taskMap.put("/mappers.vm", null);

		VelocityHelper.threadsCreater(taskMap, paramMap);
		List<Future<String>> threadsCreater = VelocityHelper.threadsCreater(taskMap, paramMap);
		for (Future<String> f : threadsCreater) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		// System.out.println(propertiesComments);
		// System.out.println(propertiesType);

	}

}
