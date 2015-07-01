package j.jave.framework.tomcat.support;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Realm;
import org.apache.catalina.authenticator.AuthenticatorBase;
import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;

public class JJaveAuthenticator extends AuthenticatorBase {

	private static final String AUTH_METHOD="JJave";
	
	private String source;
	
	@Override
	public boolean authenticate(Request request, HttpServletResponse response,LoginConfig config)
			throws IOException {
		System.out.println(JJaveAuthenticator.class.getName()+" source : "+source);
		
		Realm realm=context.getRealm() ;
		
		if(!(realm instanceof JJaveRealm)){
			throw new RuntimeException(" realm not support : expected "+JJaveRealm.class.getName());
		}
		
		JJaveRealm javeRealm=(JJaveRealm) realm;
		
		register(request, response, javeRealm.getPrincipal("admin"), getAuthMethod(), null, null);
		
		return true;
	}

	@Override
	protected String getAuthMethod() {
		return AUTH_METHOD;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
