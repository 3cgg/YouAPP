package com.youappcorp.template.ftl;

import com.youappcorp.template.ftl.ui.UISingleModelExecutingTask;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

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
		
		UISingleModelExecutingTask uiSingleModelExecutingTask=new UISingleModelExecutingTask();
		simpleLinkedFlowImpl.put(uiSingleModelExecutingTask);
		
		FileWriterTask fileWriterTask=new FileWriterTask();
		simpleLinkedFlowImpl.put(fileWriterTask);
		
		JFlowContext  flowContext =new JFlowContext();
		TemplateUtil.setConfig(flowContext, config);
		
		simpleLinkedFlowImpl.start(flowContext);
		LOGGER.info("tempate is processed completely.");
	}
	
}
