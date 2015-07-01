package j.jave.framework.components.login.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.utils.JUniqueUtils;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.UserRoleMapper;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userRoleServiceImpl.transation")
public class UserRoleServiceImpl extends ServiceSupport<UserRole> implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	protected JMapper<UserRole> getMapper() {
		return this.userRoleMapper;
	}
	
	@Override
	public List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return userRoleMapper.getUserRolesByUserId(userId);
	}
	
	@Override
	public void bingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws JServiceException {
		if(isBing(serviceContext, userId, roleId)){
			throw new JServiceException("the user had already the role.");
		}
		
		UserRole userRole=new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRole.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, userRole);
	}
	
	@Override
	public void unbingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws JServiceException {
		
		UserRole userRole=getUserRoleOnUserIdAndRoleId(serviceContext, userId, roleId);
		if(userRole==null){
			throw new JServiceException("the user doesnot have the role.");
		}
		delete(serviceContext, userRole.getId());
	}
	
	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,
			String userId, String roleId) {
		return userRoleMapper.getUserRoleOnUserIdAndRoleId(userId, roleId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String userId,
			String roleId) {
		int count=userRoleMapper.countOnUserIdAndRoleId(userId, roleId);
		return count>0;
	}
	
}
