package j.jave.platform.jpa.springjpa.query;

import java.util.Collection;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.reflect.JPropertyNotFoundException;
import me.bunny.kernel.jave.service.JService;

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
