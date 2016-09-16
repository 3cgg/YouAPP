package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

public class TemplateRunner {

	public static void start(Config config) throws Exception {
		JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
		
		PreparedConfigTask preparedConfigTask= new PreparedConfigTask();
		simpleLinkedFlowImpl.put(preparedConfigTask);
		
		SingleModelExecutingTask singleModelExecutingTask=new SingleModelExecutingTask();
		simpleLinkedFlowImpl.put(singleModelExecutingTask);
		
		ServiceTask serviceTask=new ServiceTask();
		simpleLinkedFlowImpl.put(serviceTask);
		
		FileWriterTask fileWriterTask=new FileWriterTask();
		simpleLinkedFlowImpl.put(fileWriterTask);
		
		JFlowContext  flowContext =new JFlowContext();
		TemplateUtil.setConfig(flowContext, config);
		
		simpleLinkedFlowImpl.start(flowContext);
	}
	
}
