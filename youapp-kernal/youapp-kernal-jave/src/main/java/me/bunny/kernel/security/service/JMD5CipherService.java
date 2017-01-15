package me.bunny.kernel.security.service;

import me.bunny.kernel.jave.security.exception.JSecurityException;
import me.bunny.kernel.jave.service.JService;

public interface JMD5CipherService extends JService {

	public String encrypt(String plain) throws JSecurityException ;
	
}
