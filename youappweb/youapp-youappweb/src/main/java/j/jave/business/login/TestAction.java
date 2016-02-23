package j.jave.business.login;

import java.util.Map;

import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPAction;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value="test.action")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value="/test.action")
public class TestAction extends JSPAction {

	public TestAction(){
		System.out.println("LoginAction");
	}
	
	@RequestMapping(value="/login")
	public void login(User user,String key){
		System.out.println(user.getId()+"[--]"+key);
	}
	
	@RequestMapping(value="/show")
	public void show(){
		System.out.println("---------show method-----------");
	}
	
	
	@RequestMapping(value="/test")
	public void login(User user,
			@RequestParam(value="userd") User userd,String key,TestModel testModel,
			String[] alist,
			Integer ss,
			Double[] ssdd,
			Map<String, Object> maps,Map<Integer, Object> mapsss){
		System.out.println(user.getId()+"[--]"+key);
	}
	
	
}
