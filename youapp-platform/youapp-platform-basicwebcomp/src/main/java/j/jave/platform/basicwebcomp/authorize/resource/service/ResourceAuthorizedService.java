package j.jave.platform.basicwebcomp.authorize.resource.service;

import j.jave.platform.basicwebcomp.authorize.resource.model.ResourceAuthorized;

import java.util.List;

public interface ResourceAuthorizedService {

	public List<ResourceAuthorized> getResourceAuthorizedByUserId(String userId);
	
}
