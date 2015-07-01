package j.jave.framework.tomcat.support;

import java.security.Principal;

public class JJavePrincipal implements Principal {

	@Override
	public String getName() {
		return this.name;
	}

	private String name;
	
	public JJavePrincipal(String name) {
		this.name=name;
	}
	
	
}
