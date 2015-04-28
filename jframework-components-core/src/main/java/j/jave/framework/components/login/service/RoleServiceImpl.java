package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.RoleMapper;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="roleServiceImpl.transation")
public class RoleServiceImpl extends ServiceSupport<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	protected JMapper<Role> getMapper() {
		return this.roleMapper;
	}

	@Override
	public Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode) {
		return roleMapper.getRoleByRoleCode(roleCode);
	}
	
	@Override
	public Role getAdminRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getDefaultRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, DEFAULT_CODE);
	}
}
