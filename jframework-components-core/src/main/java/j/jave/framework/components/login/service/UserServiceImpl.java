/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.exception.ServiceException;
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
@Service(value="userService")
public class UserServiceImpl extends ServiceSupport<User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.UserService#getUserByNameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return userMapper.getUserByNameAndPassword(userName, password);
	}
	
		
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.UserService#save(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.login.model.User)
	 */
	@Override
	public void saveUser(ServiceContext context, User user) throws ServiceException {
		saveOnly(context, user);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.UserService#updateUser(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.login.model.User)
	 */
	@Override
	public void updateUser(ServiceContext context, User user)
			throws ServiceException {
		updateOnly(context, user);
	}
		
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return userMapper.getUserByName(userName);
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.UserService#getUsersByPage(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.login.model.User)
	 */
	@Override
	public List<User> getUsersByPage(ServiceContext context, User user) {
		return userMapper.getUsersByPage(user);
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

}
