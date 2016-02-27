/**
 * 
 */
package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageAware;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.login.repo.UserRepo;
import j.jave.securityutil.securityclient.JRSSecurityHelper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="userService.transation")
public class UserServiceImpl extends ServiceSupport<User> implements UserService {
	
	@Autowired
	private UserRepo<?> userMapper;
	
	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return userMapper.getUserByNameAndPassword(userName, password);
	}
	
		
	@Override
	public void saveUser(ServiceContext context, User user) throws JServiceException {
		saveOnly(context, user);
	}
	
	@Override
	public void updateUser(ServiceContext context, User user)
			throws JServiceException {
		updateOnly(context, user);
	}
		
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return userMapper.getUserByName(userName);
	}
	
	
	@Override
	public JPage<User> getUsersByPage(ServiceContext context, JPageable pagination) {
		JPageImpl<User> page=new JPageImpl<User>();
		List<User> users=userMapper.getUsersByPage(pagination);
		page.setContent(users);
		return page;
	}
	
	@Override
	public User getUserById(ServiceContext context, String id) {
		return getById(context, id);
	}
	
	@Override
	public JIPersist<?, User> getRepo() {
		return userMapper;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	
	@Override
	public void register(ServiceContext context,User user) throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(user.getUserName())){
			throw new JServiceException("用户名不能为空");
		}
		
		if(JStringUtils.isNullOrEmpty(user.getPassword())){
			throw new JServiceException("密码不能为空");
		}		
		
		if(!user.getPassword().equals(user.getRetypePassword())){
			throw new JServiceException("两次输入的密码不一样");
		}
		
		User dbUser=getUserByName(context, user.getUserName().trim());
		if(dbUser!=null){
			throw new JServiceException("用户已经存在");
		}
		
		String passwrod=user.getPassword().trim();
		String encriptPassword=null;
		try {
			encriptPassword = JRSSecurityHelper.encryptOnDESede(passwrod);
		} catch (Exception e) {
			throw new JServiceException(e);
		}
		user.setPassword(encriptPassword);
		user.setUserName(user.getUserName().trim());
		saveUser(context, user);  // with encrypted password 
	}
}
