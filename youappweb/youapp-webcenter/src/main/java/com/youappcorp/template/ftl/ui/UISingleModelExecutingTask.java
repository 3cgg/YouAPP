package com.youappcorp.template.ftl.ui;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.TemplateTask;
import com.youappcorp.template.ftl.TemplateUtil;
import com.youappcorp.template.ftl.ui.add.UIAddTask;
import com.youappcorp.template.ftl.ui.edit.UIEditTask;
import com.youappcorp.template.ftl.ui.list.UIListTask;
import com.youappcorp.template.ftl.ui.view.UIViewTask;

import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataOnTask;
import me.bunny.modular._p.taskdriven.tkdd.flow.JFlowContext;
import me.bunny.modular._p.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UISingleModelExecutingTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		for(ModelConfig modelConfig:getInternalConfig()){
			JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
			
			UIPreparedConfigTask uiPreparedConfigTask=new UIPreparedConfigTask();
			simpleLinkedFlowImpl.put(uiPreparedConfigTask);
			
			UIListTask uiListTask=new UIListTask();
			simpleLinkedFlowImpl.put(uiListTask);
			
			UIAddTask uiAddTask=new UIAddTask();
			simpleLinkedFlowImpl.put(uiAddTask);
			
			UIEditTask uiEditTask=new UIEditTask();
			simpleLinkedFlowImpl.put(uiEditTask);
			
			UIViewTask uiViewTask=new UIViewTask();
			simpleLinkedFlowImpl.put(uiViewTask);
			
			
			JFlowContext flowContext=getFlowContext();
			TemplateUtil.setModelConfig(flowContext, modelConfig);
			
			simpleLinkedFlowImpl.start(flowContext);
		}
        return true;
	}

}
