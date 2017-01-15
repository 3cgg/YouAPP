package me.bunny.app._c.jpa.springjpa.query;

import java.util.HashMap;
import java.util.Map;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
	protected JEntityUtilService doGetService() {
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
