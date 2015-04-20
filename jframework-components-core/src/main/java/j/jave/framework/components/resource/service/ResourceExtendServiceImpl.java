package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceExtendMapper;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceExtendServiceImpl")
public class ResourceExtendServiceImpl extends ServiceSupport<ResourceExtend> implements ResourceExtendService {

	@Autowired
	private ResourceExtendMapper resourceExtendMapper;
	
	@Override
	protected JMapper<ResourceExtend> getMapper() {
		return this.resourceExtendMapper;
	}
	
	
	
}
