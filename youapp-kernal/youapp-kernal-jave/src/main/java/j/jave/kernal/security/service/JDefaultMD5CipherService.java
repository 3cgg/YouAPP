package j.jave.kernal.security.service;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.security.JMD5Cipher;
import j.jave.kernal.jave.security.exception.JSecurityException;

public class JDefaultMD5CipherService extends JServiceFactorySupport<JMD5CipherService> implements JMD5CipherService  {
	
//	public JDefaultMD5CipherService() {
//		super(JMD5CipherService.class);
//	}

	@Override
	public String encrypt(String plain) throws JSecurityException {
		try {
			return JMD5Cipher.encrypt(plain);
		} catch (Exception e) {
			throw new JSecurityException(e);
		}
	}

	@Override
	public JMD5CipherService getService() {
		return this;
	}
	
	public static interface JMD5CipherServiceSupport{
		
		public JMD5CipherService getMd5CipherService();
		
	}
	
}
