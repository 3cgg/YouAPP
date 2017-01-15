package me.bunny.app._c.jpa.springjpa.query;

import me.bunny.app._c.jpa.springjpa.spi.JGenericQueryService;
import me.bunny.app._c.jpa.springjpa.spi.JHibernateQueryService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class JSPIQueryServiceUtil {

	protected static JSPIFoundService spiFoundService=JServiceHubDelegate.get()
			.getService(new Object(), JSPIFoundService.class);
	
	protected static JSPIQueryService spiQueryService;
	static {
		JJPASPI jpaSpi=spiFoundService.getSpi();
		switch (jpaSpi) {
		case HIBERNATE:
			spiQueryService=new JHibernateQueryService();
			break;
		case EMPTY:
			spiQueryService=new JGenericQueryService();
			break;
		default:
			spiQueryService=new JGenericQueryService();
		}
	}
	
	public static JSPIQueryService getSPIQueryService(){
		return spiQueryService;
	}
	
}
