package me.bunny.app._c.jpa.springjpa.query;

import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

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
	public JSPIFoundService doGetService() {
		return this;
	}
	
}
