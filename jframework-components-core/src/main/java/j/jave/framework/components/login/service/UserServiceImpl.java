/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.commons.security.JDESedeCipher;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.UserMapper;
import j.jave.framework.components.login.model.User;
import j.jave.framework.mybatis.JMapper;

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
	private UserMapper userMapper;
	
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
	public List<User> getUsersByPage(ServiceContext context, JPagination pagination) {
		return userMapper.getUsersByPage(pagination);
	}
	
	@Override
	public User getUserById(ServiceContext context, String id) {
		return getById(context, id);
	}
	
	@Override
	protected JMapper<User> getMapper() {
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
		String encriptPassword=JDESedeCipher.get().encrypt(passwrod);
		user.setPassword(encriptPassword);
		user.setUserName(user.getUserName().trim());
		saveUser(context, user);  // with encrypted password 
	}
}
