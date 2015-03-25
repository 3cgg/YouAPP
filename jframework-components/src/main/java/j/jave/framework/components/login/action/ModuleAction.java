package j.jave.framework.components.login.action;

import j.jave.framework.components.views.HTTPAction;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value="login.moduleaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ModuleAction extends HTTPAction {
	
	public String modules(){
		return "/WEB-INF/jsp/login/modules.jsp";
	}
	
	
}
