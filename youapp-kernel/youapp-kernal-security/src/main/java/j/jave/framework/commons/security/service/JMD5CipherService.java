package j.jave.framework.commons.security.service;

import j.jave.framework.commons.security.exception.JSecurityException;
import j.jave.framework.commons.service.JService;

public interface JMD5CipherService extends JService {

	public String encrypt(String plain) throws JSecurityException ;
	
}
