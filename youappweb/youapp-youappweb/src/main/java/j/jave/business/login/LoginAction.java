package j.jave.business.login;

import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPAction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value="login.action")
@RequestMapping(value="/login.action")
public class LoginAction extends JSPAction {

	public LoginAction(){
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
}
