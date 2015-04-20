package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.UserRoleMapper;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userRoleServiceImpl")
public class UserRoleServiceImpl extends ServiceSupport<UserRole> implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	protected JMapper<UserRole> getMapper() {
		return this.userRoleMapper;
	}
	
	
	
}
