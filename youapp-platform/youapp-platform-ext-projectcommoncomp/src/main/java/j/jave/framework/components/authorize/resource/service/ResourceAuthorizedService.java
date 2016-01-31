package j.jave.framework.components.authorize.resource.service;

import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;

import java.util.List;

public interface ResourceAuthorizedService {

	public List<ResourceAuthorized> getResourceAuthorizedByUserId(String userId);
	
}
