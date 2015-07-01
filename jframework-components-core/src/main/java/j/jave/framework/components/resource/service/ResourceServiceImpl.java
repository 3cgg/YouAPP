package j.jave.framework.components.resource.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceMapper;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

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
	
	@Override
	public Resource getResourceByURL(ServiceContext context, String url) {
		return resourceMapper.getResourceByURL(url);
	}
	
	@Override
	public void saveResource(ServiceContext context, Resource resource) throws JServiceException{
		Resource dbResource=getResourceByURL(context, resource.getUrl());
		if(dbResource!=null){
			throw new JServiceException("duplicate url : "+resource.getUrl());
		}
		saveOnly(context, resource);
	}
	
	
}
