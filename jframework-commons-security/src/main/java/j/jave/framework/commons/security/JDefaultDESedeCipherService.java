package j.jave.framework.commons.security;

import j.jave.framework.commons.utils.JStringUtils;

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
