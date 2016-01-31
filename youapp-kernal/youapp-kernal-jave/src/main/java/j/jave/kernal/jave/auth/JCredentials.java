package j.jave.kernal.jave.auth;

import java.security.Principal;

public interface JCredentials {

	Principal getUserPrincipal();

    String getPassword();
	
}
