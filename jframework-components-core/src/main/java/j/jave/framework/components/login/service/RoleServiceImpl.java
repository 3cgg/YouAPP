package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.RoleMapper;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="roleServiceImpl")
public class RoleServiceImpl extends ServiceSupport<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	protected JMapper<Role> getMapper() {
		return this.roleMapper;
	}
	
	
	
}
