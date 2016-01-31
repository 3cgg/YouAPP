package j.jave.framework.components.authorize.resource.service;

import j.jave.framework.components.authorize.resource.mapper.ResourceAuthorizedMapper;
import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="resourceAuthorizedServiceImpl")
public class ResourceAuthorizedServiceImpl implements ResourceAuthorizedService {

	@Autowired
	private ResourceAuthorizedMapper resourceAuthorizedMapper;
	
	@Override
	public List<ResourceAuthorized> getResourceAuthorizedByUserId(String userId) {
		return resourceAuthorizedMapper.getResourceAuthorizedByUserId(userId);
	}

}
