package j.jave.framework.components.resource.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.resource.mapper.ResourceExtendMapper;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceExtendServiceImpl.transation")
public class ResourceExtendServiceImpl extends ServiceSupport<ResourceExtend> implements ResourceExtendService {

	@Autowired
	private ResourceExtendMapper resourceExtendMapper;
	
	@Autowired
	private ResourceService resourceService;
	
	@Override
	protected JMapper<ResourceExtend> getMapper() {
		return this.resourceExtendMapper;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
