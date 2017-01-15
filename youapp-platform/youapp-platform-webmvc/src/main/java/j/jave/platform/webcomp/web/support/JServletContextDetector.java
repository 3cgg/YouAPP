/**
 * 
 */
package j.jave.platform.webcomp.web.support;

import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;
import me.bunny.app._c.sps.support.ehcache.subhub.EhcacheDelegateService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * the class has the ability of collect all Servlets , Filters in the WEB.XML via scanning the {@link ServletContext}. 
 * Some custom JFilterFilter or JServletFilter can replace default ones {@link #initFilterFilter()} , {@link #initServletFilter()}
 * <p> it's thread safety. 
 * @author J
 * @see JServletContext 
 */
public class JServletContextDetector implements JServletProvider , JServletStaticMemoryCacheIO ,JEhcacheServiceAware {

	private final HttpServletRequest request;
	
	private JFilterFilter filterFilter;
	
	private JServletFilter servletFilter;
	
	private EhcacheDelegateService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheDelegateService.class);;
	
	@Override
	public EhcacheDelegateService getEhcacheService() {
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheDelegateService) ehcacheService;
	}
	

	public JServletContextDetector(HttpServletRequest request) {
		this.request=request;
		initFilterFilter();
		initServletFilter();
	}

	private void initServletFilter() {
		this.servletFilter=new JServletFilter() {
			
			@Override
			public boolean filter(ServletRegistration servletRegistration,
					ServletContext servletContext) {
				return false;
			}
			
			@Override
			public boolean filter(Class<?> clazz) {
				return !JServlet.class.isAssignableFrom(clazz);
			}
		};
	}

	private void initFilterFilter() {
		this.filterFilter=new JFilterFilter() {
			@Override
			public boolean filter(FilterRegistration filterRegistration,
					ServletContext servletContext) {
				return false;
			}
			
			@Override
			public boolean filter(Class<?> clazz) {
				return !JFilter.class.isAssignableFrom(clazz) ;
			}
		};
	}
	
	public JServletContextDetector(HttpServletRequest request,JFilterFilter filterFilter,JServletFilter servletFilter) {
		this.request=request;
		this.filterFilter=filterFilter;
		this.servletFilter=servletFilter;
	}

	volatile JServletContext servletContext=null;
	
	private Object sync=new Object();
	
	@Override
	public JServletContext getServletContext() {
		
		if(servletContext==null){
			synchronized (sync) {
				if(servletContext==null){
					Object obj=getEhcacheService().get(JServletStaticMemoryCacheIO_KEY);
					if(obj==null){
						obj=setServletContext();
					}
					servletContext=(JServletContext) obj;
				}
			}
		}
		return servletContext;
	}

	@Override
	public JServletContext setServletContext() {
		
		ServletContext servletContext= request.getServletContext();
		JServletContext appServletContext=new JServletContext(filterFilter, servletFilter);
		
		Map<String, ? extends FilterRegistration> filterMap= servletContext.getFilterRegistrations();
		if(filterMap!=null&&!filterMap.isEmpty()){
			for (Iterator<? extends FilterRegistration> iterator = filterMap.values().iterator(); iterator.hasNext();) {
				FilterRegistration registration =  iterator.next();
				appServletContext.putFilterRegistration(registration, servletContext);
			}
		}
		
		Map<String, ? extends ServletRegistration> servletMap= servletContext.getServletRegistrations();
		if(servletMap!=null&&!servletMap.isEmpty()){
			for (Iterator<? extends ServletRegistration> iterator = servletMap.values().iterator(); iterator.hasNext();) {
				ServletRegistration registration =  iterator.next();
				appServletContext.putServletRegistration(registration, servletContext);
			}
		}
		getEhcacheService().put(JServletStaticMemoryCacheIO_KEY, appServletContext);
		return appServletContext;
	}
	
	
	
	
}
