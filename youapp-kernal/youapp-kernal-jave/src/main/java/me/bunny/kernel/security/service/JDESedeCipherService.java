package me.bunny.kernel.security.service;

import me.bunny.kernel._c.service.JService;

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
