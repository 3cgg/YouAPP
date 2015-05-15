/**
 * 
 */
package j.jave.framework.components.login.action.jsp;

import java.io.File;
import java.net.URI;
import java.util.Date;

import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserExtend;
import j.jave.framework.components.login.service.UserExtendService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.io.JFile;
import j.jave.framework.utils.JDateUtils;
import j.jave.framework.utils.JStringUtils;
import j.jave.framework.utils.JURIUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author J
 */
@Controller(value="user.useraction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserJSPAction extends JSPActionSupport {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserExtendService userExtendService;
	
	public String uploadImage() throws Exception{
		SessionUser sessionUser=getSessionUser();
		UserExtend userExtend=userExtendService.getUserExtendByUserId(getServiceContext(), sessionUser.getId());
		
		if(userExtend==null){
			User dbUser=userService.getUserByName(null, sessionUser.getUserName());
			userExtend=new UserExtend();
			userExtend.setUserId(sessionUser.getId());
			userExtend.setUserName(dbUser.getUserName());
		}
		userExtend.setUserImage(getParameter("userImage"));
		
		if(JStringUtils.isNullOrEmpty(userExtend.getId())){
			userExtendService.saveUserExtend(getServiceContext(), userExtend);
		}
		else{
			userExtendService.updateUserExtend(getServiceContext(), userExtend);
		}
		setSuccessMessage(EDIT_SUCCESS);
		return "/WEB-INF/jsp/login/image-user.jsp";
	} 

	
	
	public JFile downloadImage(){
		SessionUser sessionUser=getSessionUser();
		UserExtend userExtend=userExtendService.getUserExtendByUserId(getServiceContext(), sessionUser.getId());
		
		JFile file=null;
		
		if(userExtend!=null){
			String imageFile=userExtend.getUserImage();
			byte[] bytes= JURIUtils.getBytes(imageFile);
			file=new JFile(new File(userExtend.getUserImage()),userExtend.getUserName()+"-"+JDateUtils.formatWithSeconds(new Date()));
			file.setFileContent(bytes);
		}
		return file;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
