package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.core.service.SessionUserImpl;

import java.util.List;

public interface AuthenticationManagerService extends JService {

	SessionUserImpl getUserByNameAndPassword(String name,String password);
	
	SessionUserImpl getUserByName(String name);
	
	public List<AuthorizedResource> getAllAuthorizedResources();
	
}
