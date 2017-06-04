package com.mico.workutils.helper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

public class CreateTask implements Callable<String>{

	private String vmPath;
	private File target;
	private Map<String,?> param;
	
	public CreateTask(String vmPath,Map<String,?> param,File target) {
		super();
		this.vmPath = vmPath;
		this.target = target;
		this.param = param;
	}





	public String call()  {
		return VelocityHelper.createByTemplate(vmPath,param,target);
	}
	
}