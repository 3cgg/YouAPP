package j.jave.framework.zookeeper.support;

import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class JDigestAuth extends JBaseAuth{

	public JDigestAuth(){
		this.schema="digest";
	}

	@Override
	public String authorizingId() {
		try {
			return super.consistof();
		} catch (Exception e) {
			throw new JZooKepperException(e);
		}
	}
	
	public String authorizingIdSHA1(){
		try {
			return DigestAuthenticationProvider.generateDigest(consistof());
		} catch (NoSuchAlgorithmException e) {
			throw new JZooKepperException(e);
		} 
	}
	
}
