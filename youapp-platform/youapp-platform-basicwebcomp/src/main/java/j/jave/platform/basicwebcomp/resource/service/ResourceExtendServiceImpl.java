package j.jave.platform.basicwebcomp.resource.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.resource.model.Resource;
import j.jave.platform.basicwebcomp.resource.model.ResourceExtend;
import j.jave.platform.basicwebcomp.resource.repo.ResourceExtendRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceExtendServiceImpl.transation")
public class ResourceExtendServiceImpl extends ServiceSupport<ResourceExtend> implements ResourceExtendService {

	@Autowired
	private ResourceExtendRepo<?> resourceExtendMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public JIPersist<?, ResourceExtend> getRepo() {
		return resourceExtendMapper;
	}
	
	@Override
	public void saveResourceExtend(ServiceContext context,
			ResourceExtend resourceExtend) throws JServiceException {
		// store resource first. 
		if(JStringUtils.isNullOrEmpty(resourceExtend.getResourceId())){
			Resource resource=resourceExtend.getResource();
			resourceService.saveResource(context, resource);
			resourceExtend.setResourceId(resource.getId());
		}
		
		if(JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
			resourceExtend.setCached("N");
		}
		
		saveOnly(context, resourceExtend);
	}

	@Override
	public void enableCache(ServiceContext context,
			ResourceExtend resourceExtend) throws JServiceException {
		
		// create resource extend
		if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
			saveResourceExtend(context, resourceExtend);
		}
		
		if("N".equals(resourceExtend.getCached())||JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
			resourceExtendMapper.updateCached(resourceExtend.getId(), "Y");
		}
		
	}
	
	@Override
	public void disableCache(ServiceContext context,
			ResourceExtend resourceExtend) throws JServiceException {
		// create resource extend
		if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
			saveResourceExtend(context, resourceExtend);
		}
		
		if("Y".equals(resourceExtend.getCached())||JStringUtils.isNullOrEmpty(resourceExtend.getCached())){
			resourceExtendMapper.updateCached(resourceExtend.getId(), "N");
		}
		
	}
	
	@Override
	public ResourceExtend enhanceResource(ServiceContext context,
			ResourceExtend resourceExtend) throws JServiceException {

		if(JStringUtils.isNullOrEmpty(resourceExtend.getResourceId())){
			Resource resource=resourceService.getResourceByURL(context, resourceExtend.getUrl());
			// if create new resource
			if(resource==null){
				resource=new Resource();
				resource.setUrl(resourceExtend.getUrl());
				resourceService.saveResource(context, resource);
				resourceExtend.setResourceId(resource.getId());
			}
			else{
				resourceExtend.setResourceId(resource.getId());
			}
		}
		
		//if create new resource extend
		if(JStringUtils.isNullOrEmpty(resourceExtend.getId())){
			
			ResourceExtend dbResourceExtend=resourceExtendMapper.getResourceExtendOnResourceId(resourceExtend.getResourceId());
			// create new resource extend , set all default value.
			if(dbResourceExtend==null){
				saveResourceExtend(context, resourceExtend);
				return resourceExtend;
			}
			else{
				resourceExtend.setId(dbResourceExtend.getId());
			}
		}
		
		// update different states. 
		
		if(JStringUtils.isNotNullOrEmpty(resourceExtend.getCached())){
			resourceExtendMapper.updateCached(resourceExtend.getId(), resourceExtend.getCached());
		}
		return resourceExtend;
	}
	
	
	@Override
	public ResourceExtend getResourceExtendOnResourceId(ServiceContext context,
			String resourceId) {
		return resourceExtendMapper.getResourceExtendOnResourceId(resourceId);
	}

	@Override
	public List<ResourceExtend> getAllResourceExtends(ServiceContext context) {
		return resourceExtendMapper.getAllResourceExtends();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
