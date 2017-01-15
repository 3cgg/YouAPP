package me.bunny.kernel.security.service;

import me.bunny.kernel._c.security.exception.JSecurityException;
import me.bunny.kernel._c.service.JService;

public interface JMD5CipherService extends JService {

	public String encrypt(String plain) throws JSecurityException ;
	
}
