package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.RoleGroupMapper;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="roleGroupServiceImpl.transation")
public class RoleGroupServiceImpl extends ServiceSupport<RoleGroup> implements RoleGroupService {

	@Autowired
	private RoleGroupMapper  roleGroupMapper;
	
	@Override
	protected JMapper<RoleGroup> getMapper() {
		return this.roleGroupMapper;
	}
	
	
	
}
