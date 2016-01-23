package j.jave.framework.components.support.security.subhub;

import j.jave.framework.commons.security.JDefaultMD5CipherService;
import j.jave.framework.commons.security.JMD5CipherService;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;


@Service(value="j.jave.framework.components.support.security.subhub.MD5CipherServiceFactory")
public class MD5CipherServiceFactory extends SpringServiceFactorySupport<JMD5CipherService> {

	public MD5CipherServiceFactory() {
		super(JMD5CipherService.class);
	}

	private JMD5CipherService mdsCipherService;
	
	private Object sync=new Object();
	
	@Override
	public JMD5CipherService getService() {
		
		if(mdsCipherService==null){
			synchronized (sync) {
				if(mdsCipherService==null){
					mdsCipherService=new JDefaultMD5CipherService();
				}
			}
		}
		return mdsCipherService;
	}
	
}
