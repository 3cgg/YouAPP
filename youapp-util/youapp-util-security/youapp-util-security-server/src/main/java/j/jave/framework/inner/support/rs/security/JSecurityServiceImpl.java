package j.jave.framework.inner.support.rs.security;

import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.security.exception.JSecurityException;
import j.jave.framework.commons.security.service.JDESedeCipherService;
import j.jave.framework.commons.security.service.JDESedeCipherServiceFactory.JDESedeCipherServiceSupport;
import j.jave.framework.commons.security.service.JDefaultMD5CipherService.JMD5CipherServiceSupport;
import j.jave.framework.commons.security.service.JMD5CipherService;

import org.springframework.stereotype.Service;

@Service(value="j.jave.framework.inner.support.rs.security.JSecurityServiceImpl")
public class JSecurityServiceImpl implements JSecurityService, JDESedeCipherServiceSupport ,JMD5CipherServiceSupport{
	
	private JDESedeCipherService desedeCipherService;
	
	private JMD5CipherService md5CipherService;
	
	public JSecurityServiceImpl(){
		System.out.println("on...");
	}
	
	@Override
	public JDESedeCipherService getDESedeCipherService(){
		if(desedeCipherService==null){
			desedeCipherService= JServiceHubDelegate.get().getService(this, JDESedeCipherService.class);
		}
		return desedeCipherService;
	}
	
	public JMD5CipherService getMd5CipherService() {
		
		if(md5CipherService==null){
			md5CipherService=JServiceHubDelegate.get().getService(this, JMD5CipherService.class);
		}
		return md5CipherService;
	}
	
	@Override
	public String encryptOnDESede(String plain) throws JSecurityException {
		return getDESedeCipherService().encrypt(plain);
	}

	@Override
	public String decryptOnDESede(String encrypted) throws JSecurityException {
		return getDESedeCipherService().decrypt(encrypted);
	}

	@Override
	public String encryptOnMD5(String plain) throws JSecurityException {
		try {
			return getMd5CipherService().encrypt(plain);
		} catch (Exception e) {
			throw new JSecurityException(e);
		}
	}

}
