package j.jave.platform.basicwebcomp.access.subhub;

import java.util.List;

import j.jave.platform.basicwebcomp.core.service.SessionUserImpl;

public interface AuthenticationManagerService {

	SessionUserImpl getUserByNameAndPassword(String name,String password);
	
	SessionUserImpl getUserByName(String name);
	
	public List<AuthorizedResource> getAllAuthorizedResources();
	
}
