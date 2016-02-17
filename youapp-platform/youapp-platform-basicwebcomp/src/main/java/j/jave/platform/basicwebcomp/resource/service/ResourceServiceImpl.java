package j.jave.platform.basicwebcomp.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.resource.model.Resource;
import j.jave.platform.basicwebcomp.resource.repo.ResourceRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceServiceImpl.transation")
public class ResourceServiceImpl extends ServiceSupport<Resource> implements ResourceService {

	@Autowired
	private ResourceRepo<?> resourceMapper;
	
	@Override
	public JIPersist<?, Resource> getRepo() {
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
