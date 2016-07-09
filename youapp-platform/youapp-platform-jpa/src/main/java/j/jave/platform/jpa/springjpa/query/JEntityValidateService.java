package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JPropertyNotFoundException;
import j.jave.kernal.jave.service.JService;

import java.util.Collection;

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
