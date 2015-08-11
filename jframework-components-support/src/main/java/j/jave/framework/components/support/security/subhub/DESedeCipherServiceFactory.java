package j.jave.framework.components.support.security.subhub;

import j.jave.framework.commons.security.JDESedeCipherConfig;
import j.jave.framework.commons.security.JDESedeCipherService;
import j.jave.framework.commons.security.JDefaultDESedeCipherService;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

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
