package j.jave.framework.components.login.action.jsp;

import j.jave.framework.components.web.jsp.JSPAction;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value="login.moduleaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ModuleAction extends JSPAction {
	
	private static final long serialVersionUID = -2957381293291663541L;

	public String modules(){
		return "/WEB-INF/jsp/login/modules.jsp";
	}
	
	
}
