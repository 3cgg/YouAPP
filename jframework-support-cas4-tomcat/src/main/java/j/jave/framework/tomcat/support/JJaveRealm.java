package j.jave.framework.tomcat.support;

import java.security.Principal;

import org.apache.catalina.Wrapper;
import org.apache.catalina.realm.RealmBase;

public class JJaveRealm extends RealmBase {

	@Override
	protected String getName() {
		return "j.jave.framework.tomcat.support.JJaveRealm";
	}

	@Override
	protected String getPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Principal getPrincipal(String username) {
		return new JJavePrincipal(username);
	}
	
	@Override
	public boolean hasRole(Wrapper wrapper, Principal principal, String role) {
		System.out.println(JJaveRealm.class.getName()+" process role : "+role);
		return true;
	}
	
}
