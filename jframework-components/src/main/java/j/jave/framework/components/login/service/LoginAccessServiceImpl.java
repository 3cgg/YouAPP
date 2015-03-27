/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.login.model.User;
import j.jave.framework.security.APPCipher;
import j.jave.framework.utils.JUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="loginAccessService")
public class LoginAccessServiceImpl implements LoginAccessService {

	@Autowired
	private UserService userService;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.UserAccessService#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public String validate(String name, String password)
			throws ServiceException {
		if(JUtils.isNullOrEmpty(name)){
			throw new ServiceException("用户名不能为空");
		}
		
		if(JUtils.isNullOrEmpty(password)){
			throw new ServiceException("密码不能为空");
		}
		String encryptPassword=APPCipher.get().encrypt(password.trim());
		User user= userService.getUserByNameAndPassword(name.trim(), encryptPassword);
		if(user!=null) return  JUtils.unique()+"-"+name; 
		return "";
	}
	
	
	@Override
	public boolean isNeedLoginRole(String url) throws ServiceException {
		if("/login.loginaction/toRegister".equals(url)){
			return false;
		}
		else if("/login.loginaction/register".equals(url)){
			return false;
		}
		else if("/login.loginaction/login".equals(url)){
			return false;
		}
		else if("/login.loginaction/toLogin".equals(url)){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.LoginAccessService#register(j.jave.framework.components.login.model.User)
	 */
	@Override
	public void register(ServiceContext context,User user) throws ServiceException {
		
		if(JUtils.isNullOrEmpty(user.getUserName())){
			throw new ServiceException("用户名不能为空");
		}
		
		if(JUtils.isNullOrEmpty(user.getPassword())){
			throw new ServiceException("密码不能为空");
		}		
		
		if(!user.getPassword().equals(user.getRetypePassword())){
			throw new ServiceException("两次输入的密码不一样");
		}
		
		User dbUser=userService.getUserByName(context, user.getUserName().trim());
		if(dbUser!=null){
			throw new ServiceException("用户已经存在");
		}
		
		String passwrod=user.getPassword().trim();
		String encriptPassword=APPCipher.get().encrypt(passwrod);
		user.setPassword(encriptPassword);
		user.setUserName(user.getUserName().trim());
		userService.saveUser(context, user);  // with encrypted password 
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.LoginAccessService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public String login(String name, String password) throws ServiceException {
		String ticket=validate(name, password);
		if(JUtils.isNullOrEmpty(ticket)){
			throw new ServiceException("用户名或者密码不正确");
		}
		return ticket;
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.LoginAccessService#authorize(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authorize(String resource, String name) {
		
		if("zhongjin".equals(name)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
}
