package j.jave.kernal.security.service;

import j.jave.kernal.jave.security.exception.JSecurityException;
import j.jave.kernal.jave.service.JService;

public interface JMD5CipherService extends JService {

	public String encrypt(String plain) throws JSecurityException ;
	
}
