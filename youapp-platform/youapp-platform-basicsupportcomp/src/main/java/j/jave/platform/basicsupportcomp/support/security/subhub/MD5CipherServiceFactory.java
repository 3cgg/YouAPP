package j.jave.platform.basicsupportcomp.support.security.subhub;

import j.jave.kernal.security.service.JDefaultMD5CipherService;
import j.jave.kernal.security.service.JMD5CipherService;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

@Deprecated
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
