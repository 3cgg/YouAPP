package me.bunny.kernel.security.service;

import me.bunny.kernel._c.security.JDESedeCipher;
import me.bunny.kernel._c.utils.JStringUtils;

public class JDefaultDESedeCipherService implements JDESedeCipherService {

	private JDESedeCipher desedeCipher; 
	
	public JDefaultDESedeCipherService(){
		desedeCipher=JDESedeCipher.get();
	}
	
	public JDefaultDESedeCipherService(JDESedeCipherConfig desedeCipherConfig){
		
		if(JStringUtils.isNotNullOrEmpty(desedeCipherConfig.getKey())){
			desedeCipher=JDESedeCipher.get(desedeCipherConfig.getKey());
		}
		else{
			desedeCipher=JDESedeCipher.get();
		}
	}
	
	@Override
	public String encrypt(String plain) {
		return desedeCipher.encrypt(plain);
	}

	@Override
	public String decrypt(String encrypted) {
		return desedeCipher.decrypt(encrypted);
	}
	
}
