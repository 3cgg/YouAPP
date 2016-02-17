package j.jave.platform.basicwebcomp.authorize.resource.repo;

import j.jave.platform.basicwebcomp.authorize.resource.model.ResourceAuthorized;

import java.util.List;


public interface ResourceAuthorizedRepo{

	public List<ResourceAuthorized> getResourceAuthorizedByUserId(String userId);
	
}
