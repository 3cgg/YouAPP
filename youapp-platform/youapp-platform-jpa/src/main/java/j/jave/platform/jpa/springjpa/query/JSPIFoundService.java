package j.jave.platform.jpa.springjpa.query;

import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;

public class JSPIFoundService extends JServiceFactorySupport<JSPIFoundService>
implements JService
{
	
	public JJPASPI getSpi(){
		PersistenceProviderResolver persistenceProviderResolver=PersistenceProviderResolverHolder.getPersistenceProviderResolver();
		List<PersistenceProvider> persistenceProviders=persistenceProviderResolver.getPersistenceProviders();
		if(JCollectionUtils.hasInCollect(persistenceProviders)){
			for(PersistenceProvider persistenceProvider:persistenceProviders){
				if(persistenceProvider instanceof HibernatePersistenceProvider){
					return JJPASPI.HIBERNATE;
				}
			}
		}
		return JJPASPI.EMPTY;
	} 

	
	@Override
	public JSPIFoundService getService() {
		return this;
	}
	
}
