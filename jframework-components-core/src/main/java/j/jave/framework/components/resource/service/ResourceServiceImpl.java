package j.jave.framework.components.resource.service;

import java.util.List;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceMapper;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceServiceImpl.transation")
public class ResourceServiceImpl extends ServiceSupport<Resource> implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	protected JMapper<Resource> getMapper() {
		return this.resourceMapper;
	}
	
	@Override
	public List<Resource> getResources() {
		return resourceMapper.getResources();
	}
	
}
