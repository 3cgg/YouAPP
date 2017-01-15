package me.bunny.kernel.security.service;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.security.JMD5Cipher;
import me.bunny.kernel.jave.security.exception.JSecurityException;

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
	public JMD5CipherService doGetService() {
		return this;
	}
	
	public static interface JMD5CipherServiceSupport{
		
		public JMD5CipherService getMd5CipherService();
		
	}
	
}
