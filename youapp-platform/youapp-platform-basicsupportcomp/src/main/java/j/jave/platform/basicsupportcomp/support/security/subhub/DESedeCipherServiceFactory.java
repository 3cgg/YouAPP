package j.jave.platform.basicsupportcomp.support.security.subhub;

import j.jave.kernal.security.service.JDESedeCipherService;
import j.jave.kernal.security.service.JDefaultDESedeCipherService;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="j.jave.platform.basicsupportcomp.support.security.subhub.DESedeCipherServiceFactory")
public class DESedeCipherServiceFactory extends SpringServiceFactorySupport<DESedeCipherService> {

	@Autowired(required=false)
	private DESedeCipherServiceProvider extDeSedeCipherService;
	
	private DESedeCipherService provideDesedeCipherService;
	
	private DESedeCipherService delegateDesedeCipherService=new DESedeCipherService() {
		
		private JDESedeCipherService innerDCipherService=new JDefaultDESedeCipherService();
		
		@Override
		public String encrypt(String plain) {
			return innerDCipherService.encrypt(plain);
		}
		
		@Override
		public String decrypt(String encrypted) {
			return innerDCipherService.decrypt(encrypted);
		}
	};
	
	private Object sync=new Object();
	
	@Override
	public DESedeCipherService getService() {
		
		if(provideDesedeCipherService==null){
			synchronized (sync) {
				if(provideDesedeCipherService==null){
					if(extDeSedeCipherService!=null){
						provideDesedeCipherService=extDeSedeCipherService;
					}
					else{
						provideDesedeCipherService=delegateDesedeCipherService;
					}
				}
			}
		}
		
		return provideDesedeCipherService;
	}
	
}
