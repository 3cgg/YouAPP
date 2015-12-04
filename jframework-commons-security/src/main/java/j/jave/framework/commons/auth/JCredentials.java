package j.jave.framework.commons.auth;

import java.security.Principal;

public interface JCredentials {

	Principal getUserPrincipal();

    String getPassword();
	
}
