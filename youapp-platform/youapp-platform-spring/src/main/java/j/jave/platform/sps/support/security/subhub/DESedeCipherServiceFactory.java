package j.jave.platform.sps.support.security.subhub;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel.security.service.JDESedeCipherService;
import me.bunny.kernel.security.service.JDefaultDESedeCipherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="DESedeCipherServiceFactory")
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
