package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.jave.security.exception.JSecurityException;
import j.jave.platform.basicsupportcomp.support.security.subhub.MD5CipherService;

public class RemoteMd5SecurityService implements MD5CipherService {
	
	@Override
	public String encrypt(String plain) {
		try{
			return RemoteSecurityService.get().encryptOnMD5(plain);
		}catch(Exception e){
			throw new JSecurityException(e);
		}
	}

	
}
