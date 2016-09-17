package com.youappcorp.template.ftl;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

public class TemplateRunner {
	
	private final static JLogger LOGGER=JLoggerFactory.getLogger(TemplateRunner.class);

	public static void start(Config config) throws Exception {
		JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
		
		PreparedConfigTask preparedConfigTask= new PreparedConfigTask();
		simpleLinkedFlowImpl.put(preparedConfigTask);
		
		SingleModelExecutingTask singleModelExecutingTask=new SingleModelExecutingTask();
		simpleLinkedFlowImpl.put(singleModelExecutingTask);
		
		ServiceTask serviceTask=new ServiceTask();
		simpleLinkedFlowImpl.put(serviceTask);
		
		ServiceImplTask serviceImplTask=new ServiceImplTask();
		simpleLinkedFlowImpl.put(serviceImplTask);
		
		ControllerTask controllerTask=new ControllerTask();
		simpleLinkedFlowImpl.put(controllerTask);
		
		FileWriterTask fileWriterTask=new FileWriterTask();
		simpleLinkedFlowImpl.put(fileWriterTask);
		
		JFlowContext  flowContext =new JFlowContext();
		TemplateUtil.setConfig(flowContext, config);
		
		simpleLinkedFlowImpl.start(flowContext);
		LOGGER.info("tempate is processed completely.");
	}
	
}
