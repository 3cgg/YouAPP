package me.bunny.kernel._c.auth;

import java.security.Principal;

public interface JCredentials {

	Principal getUserPrincipal();

    String getPassword();
	
}
