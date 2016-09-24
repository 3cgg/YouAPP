
package com.youappcorp.template.ftl;

import com.youappcorp.template.ftl.Config.FieldConfig;

import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class PreparedConfigTask extends TemplateTask {

	@Override
	protected Object doRun() throws Exception {
		
		Config config=getConfig();
		
		JAssert.isNotNull(config.getUiRelativePath(), " ui relative path is missing ... ");
		JAssert.isNotEmpty(config.getUiRelativePath(), " ui relative path is missing ... ");
		
		config.addUIField(new FieldConfig("createTimeStart", "创建时间（起）",KeyNames.FIELD_TYPE_DATE));
		config.addUIField(new FieldConfig("createTimeEnd", "创建时间（止）",KeyNames.FIELD_TYPE_DATE));
		config.addUIField(new FieldConfig("updateTimeStart", "更新时间（起）",KeyNames.FIELD_TYPE_DATE));
		config.addUIField(new FieldConfig("updateTimeEnd", "更新时间（止）",KeyNames.FIELD_TYPE_DATE));
		
		Class<? extends InternalConfigStrategy> clazz= getConfig().getInternalConfigStrategyClass();
		InternalConfigStrategy configStrategy= clazz.newInstance();
		InternalConfig internalConfig=configStrategy.config(getFlowContext());
		setInternalConfig(internalConfig);
		return null;
	}

}
