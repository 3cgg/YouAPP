package j.jave.platform.basicsupportcomp.support.security.subhub;

import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.security.service.JDESedeCipherConfig;
import j.jave.kernal.security.service.JDESedeCipherService;
import j.jave.kernal.security.service.JDefaultDESedeCipherService;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value="j.jave.framework.components.support.security.subhub.DESedeCipherServiceFactory")
public class DESedeCipherServiceFactory extends SpringServiceFactorySupport<JDESedeCipherService> {

	public DESedeCipherServiceFactory() {
		super(JDESedeCipherService.class);
	}

	@Autowired(required=false)
	private JDESedeCipherConfig desedeCipherConfig;
	
	private JDESedeCipherService desedeCipherService;
	
	private Object sync=new Object();
	
	@Override
	public JDESedeCipherService getService() {
		
		if(desedeCipherService==null){
			synchronized (sync) {
				if(desedeCipherService==null){
					if(desedeCipherConfig!=null&&JStringUtils.isNotNullOrEmpty(desedeCipherConfig.getKey())){
						desedeCipherService=new JDefaultDESedeCipherService(desedeCipherConfig);
					}
					else{
						desedeCipherService=new JDefaultDESedeCipherService();
					}
				}
			}
		}
		
		return desedeCipherService;
	}
	
}
