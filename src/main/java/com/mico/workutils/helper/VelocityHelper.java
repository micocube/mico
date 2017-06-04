package com.mico.workutils.helper;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.mico.workutils.util.FileUtils;
import com.mico.workutils.util.PropertiesUtil;

public class VelocityHelper {
	private static VelocityEngine ve;
	static{
		ve = new VelocityEngine();
	}
	
	
	
	public static List<Future<String>> threadsCreater(Map<String,File> task,Map<String,?> param) {
		ExecutorService pool = Executors.newCachedThreadPool();
		Set<String> keySet = task.keySet();
		List<Future<String>> rs = new ArrayList<Future<String>>();
		for(String vmp : keySet){
			Future<String> submit = pool.submit(new CreateTask(vmp,param,task.get(vmp)));
			rs.add(submit);
		}
		pool.shutdown();
		return rs;
	}
	
	
	
	
	public static String createByTemplate(String vmPath,Map<String,?> param,File target)  {
		/**
		 * 模板引擎
		 */
		 
		 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 InputStream is = PropertiesUtil.class.getResourceAsStream("/velocity.properties");
		 Properties dbProps = new Properties();
		 try {
			dbProps.load(is);
			ve.init(dbProps);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Template t = ve.getTemplate(vmPath);
		 VelocityContext ctx = new VelocityContext();
		 
		if (null!=param) {
			Set<String> keySet = param.keySet();
			for (String key : keySet) {
				ctx.put(key, param.get(key));
			} 
		}
		StringWriter sw = new StringWriter();
		 
		 t.merge(ctx, sw);
		 if (null!=target) {
			 FileUtils.writeFile(target, sw.toString());
		}
		return sw.toString();
	}
}
