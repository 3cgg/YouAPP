package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceGroupMapper;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceGroupServiceImpl.transation")
public class ResourceGroupServiceImpl extends ServiceSupport<ResourceGroup> implements ResourceGroupService {

	@Autowired
	private ResourceGroupMapper resourceGroupMapper;
	
	@Override
	protected JMapper<ResourceGroup> getMapper() {
		return this.resourceGroupMapper;
	}
	
	
	
}
