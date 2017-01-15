package me.bunny.kernel.security.service;

public interface JSecurityService {

	String encryptOnDESede(String plain) throws Exception;

	String decryptOnDESede(String encrypted) throws Exception;

	String encryptOnMD5(String plain) throws Exception;

}