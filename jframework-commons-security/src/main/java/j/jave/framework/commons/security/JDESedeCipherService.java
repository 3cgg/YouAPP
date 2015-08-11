package j.jave.framework.commons.security;

import j.jave.framework.commons.service.JService;

public interface JDESedeCipherService extends JService {

	/**
	 * encrypt the value 
	 * @param plain
	 * @return
	 */
	public String encrypt(String plain);
	
	/**
	 * decrypt the encrypted value.
	 */
	public String decrypt(String encrypted);
	
}
