package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.jave.security.exception.JSecurityException;
import j.jave.platform.basicsupportcomp.support.security.subhub.DESedeCipherService;

public class RemoteDesedeSecurityService implements DESedeCipherService {
	
	@Override
	public String encrypt(String plain) {
		try{
			return RemoteSecurityService.get().encryptOnDESede(plain);
		}catch(Exception e){
			throw new JSecurityException(e);
		}
	}

	@Override
	public String decrypt(String encrypted) {
		try{
			return RemoteSecurityService.get().decryptOnDESede(encrypted);
		}catch(Exception e){
			throw new JSecurityException(e);
		}
	}

	
}
