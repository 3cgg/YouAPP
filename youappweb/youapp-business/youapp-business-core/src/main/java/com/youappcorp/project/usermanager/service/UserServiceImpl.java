/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.repo.UserRepo;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;

/**
 * @author Administrator
 *
 */
@Service(value="userService.transation.jpa")
public class UserServiceImpl extends InternalServiceSupport<User> implements UserService {
	
	@Autowired
	private UserRepo<?> userMapper;
	
	@Autowired
	private UserExtendService userExtendService;
	
	private DESedeCipherService deSedeCipherService=
			JServiceHubDelegate.get().getService(this, DESedeCipherService.class);
	
	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return userMapper.getUserByNameAndPassword(userName, password);
	}
	
		
	@Override
	public void saveUser(ServiceContext context, User user) throws BusinessException {
		try{
			saveOnly(context, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void updateUser(ServiceContext context, User user)
			throws BusinessException {
		try{
			updateOnly(context, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
		
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return userMapper.getUserByName(userName);
	}
	
	
	@Override
	public JPage<User> getUsersByPage(ServiceContext context, UserSearchCriteria userSearchCriteria,JPageable pagination) {
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
	public JIPersist<?, User, String> getRepo() {
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
	public void register(ServiceContext context,User user,UserExtend userExtend) throws BusinessException {
		try{
			
			if(JStringUtils.isNullOrEmpty(user.getUserName())){
				throw new BusinessException("用户名不能为空");
			}
			
			if(JStringUtils.isNullOrEmpty(user.getPassword())){
				throw new BusinessException("密码不能为空");
			}		
			
			User dbUser=getUserByName(context, user.getUserName().trim());
			if(dbUser!=null){
				throw new BusinessException("用户已经存在");
			}
			
			String passwrod=user.getPassword().trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			user.setPassword(encriptPassword);
			user.setUserName(user.getUserName().trim());
			saveUser(context, user);  // with encrypted password
			
			//save user extend
			userExtend.setUserId(user.getId());
			userExtendService.saveUserExtend(context, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	@Override
	public void resetPassword(ServiceContext context, String userId,String password) {
		try {
			User dbUser=getUserById(context, userId);
			if(dbUser==null){
				throw new BusinessException("用户不存在");
			}
			
			String passwrod=password.trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			
			dbUser.setPassword(encriptPassword);
			saveOnly(context, dbUser);
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
