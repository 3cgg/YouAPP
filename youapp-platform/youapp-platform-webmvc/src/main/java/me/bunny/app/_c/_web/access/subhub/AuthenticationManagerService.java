package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c._web.core.service.SessionUserImpl;
import me.bunny.kernel._c.service.JService;

import java.util.List;

public interface AuthenticationManagerService extends JService {

	SessionUserImpl getUserByNameAndPassword(String name,String password);
	
	SessionUserImpl getUserByName(String name);
	
	public List<AuthorizedResource> getAllAuthorizedResources();
	
}
