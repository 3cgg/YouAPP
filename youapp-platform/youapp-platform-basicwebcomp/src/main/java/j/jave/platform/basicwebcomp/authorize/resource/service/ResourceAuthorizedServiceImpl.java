package j.jave.platform.basicwebcomp.authorize.resource.service;

import j.jave.platform.basicwebcomp.authorize.resource.model.ResourceAuthorized;
import j.jave.platform.basicwebcomp.authorize.resource.repo.ResourceAuthorizedRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceAuthorizedServiceImpl")
public class ResourceAuthorizedServiceImpl implements ResourceAuthorizedService {

	@Autowired
	private ResourceAuthorizedRepo resourceAuthorizedMapper;
	
	@Override
	public List<ResourceAuthorized> getResourceAuthorizedByUserId(String userId) {
		return resourceAuthorizedMapper.getResourceAuthorizedByUserId(userId);
	}

}
