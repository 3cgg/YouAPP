package me.bunny.app._c.jpa.springjpa.query;

import java.util.Collection;

import me.bunny.kernel._c.reflect.JPropertyNotFoundException;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class JEntityValidateService
extends JServiceFactorySupport<JEntityValidateService>
implements JService
{
	
	private JEntityUtilService entityUtilService
	=JServiceHubDelegate.get().getService(this, JEntityUtilService.class);
	
	public boolean propertyExists(String property,Class<?> entityClass){
		JEntityModelMeta entityModelMeta=entityUtilService.getEntityModelMeta(entityClass);
		Collection<JEntityColumnMeta> entityColumnMetas=entityModelMeta.columnMetas();
		boolean exists=false;
		for(JEntityColumnMeta columnMeta:entityColumnMetas){
			if(columnMeta.getProperty().equals(property)){
				exists=true;
				break;
			}
		}
		
		if(!exists){
			throw new JPropertyNotFoundException("property : "+property +" not found, "+entityClass.getClass());
		}
		return exists;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected JEntityValidateService doGetService() {
		return this;
	}
	
	
	
	
	
	
}
