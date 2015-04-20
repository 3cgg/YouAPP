package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceRoleMapper;
import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceRoleServiceImpl")
public class ResourceRoleServiceImpl extends ServiceSupport<ResourceRole> implements ResourceRoleService {

	@Autowired
	private ResourceRoleMapper resourceRoleMapper;
	
	@Override
	protected JMapper<ResourceRole> getMapper() {
		return this.resourceRoleMapper;
	}
	
	
	
}
