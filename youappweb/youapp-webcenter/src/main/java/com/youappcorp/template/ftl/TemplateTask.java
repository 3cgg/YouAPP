package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JBaseTask;

public abstract class TemplateTask extends JBaseTask {

	protected Config getConfig(){
		Config config=(Config) getTaskContext().getFlowContext().get("_TEMPLATE_CONFIG");
		return config;
	}
	
}
