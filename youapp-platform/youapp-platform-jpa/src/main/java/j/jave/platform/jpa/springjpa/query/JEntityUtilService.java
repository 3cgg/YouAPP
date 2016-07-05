package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.service.JService;

import java.util.HashMap;
import java.util.Map;

public class JEntityUtilService
extends JServiceFactorySupport<JEntityUtilService>
implements JService
{
	
	private static Map<Class<?>, JEntityModelMeta> entityMetas=new HashMap<Class<?>, JEntityModelMeta>();

	private Object sync=new Object();
	
	private JEntityValidateService entityValidateService=null;
	
	private JEntityModelMeta genEntityModelMeta(Class<?> entityClass){
		JEntityMetaFinder entityMetaFinder=new JEntityMetaFinder(entityClass);
		JEntityModelMeta entityModelMeta= entityMetaFinder.find();
		return entityModelMeta;
	}
	
	
	public JEntityModelMeta getEntityModelMeta(Class<?> entityClass){
		
		if(entityMetas.containsKey(entityClass)){
			return entityMetas.get(entityClass);
		}
		
		synchronized (sync) {
			if(entityMetas.containsKey(entityClass)){
				return entityMetas.get(entityClass);
			}
			JEntityModelMeta meta= genEntityModelMeta(entityClass);
			entityMetas.put(entityClass, meta);
			return meta;
		}
	}
	
	
	public JEntityValidateService getEntityValidateService() {
		if(entityValidateService==null){
			entityValidateService=JServiceHubDelegate.get().getService(this, JEntityValidateService.class);
		}
		return entityValidateService;
	}
	
	
	@Override
	public JEntityUtilService getService() {
		return this;
	}
	
	private static JEntityUtilService INSTANCE=null;
	
	public static JEntityUtilService get(){
		if(INSTANCE==null){
			INSTANCE=JServiceHubDelegate
					.get().getService(new Object(), JEntityUtilService.class);
		}
		return INSTANCE;
	}
	
	
	

}
