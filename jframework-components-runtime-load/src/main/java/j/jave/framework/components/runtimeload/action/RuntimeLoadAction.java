package j.jave.framework.components.runtimeload.action;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.components.param.service.ParamService;
import j.jave.framework.components.runtimeload.jnerface.Key;
import j.jave.framework.components.runtimeload.model.RuntimeLoad;
import j.jave.framework.components.runtimeload.model.RuntimeLoadSearchCriteria;
import j.jave.framework.components.runtimeload.service.RuntimeLoadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller(value="runtimeload.runtimeloadaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RuntimeLoadAction extends JSPActionSupport {
	
	private RuntimeLoad  runtimeLoad;
	
	/**
	 * for search criteria 
	 */
	private RuntimeLoadSearchCriteria runtimeLoadSearchCriteria;
	
	@Autowired
	private RuntimeLoadService runtimeLoadService;
	
	@Autowired
	private ParamService paramService;

	public String getCompomentInfo(){
		
		String app=Key.get().getApp();
		System.out.println(app);
		String component=Key.get().getComponent();
		System.out.println(component);
		int version=Key.get().getVersion();
		System.out.println(version);
		System.out.println(Key.get().getKey());
		
		return JJSON.get().format(Key.get());
		
	}
	
	public String running(){
		return runtimeLoadService.running();
	}
	
}
