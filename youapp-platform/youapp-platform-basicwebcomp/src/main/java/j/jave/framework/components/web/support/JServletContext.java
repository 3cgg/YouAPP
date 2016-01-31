/**
 * 
 */
package j.jave.framework.components.web.support;

import j.jave.framework.commons.exception.JInitializationException;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.web.youappmvc.jsp.JJSPServiceServlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the class hold all information defined in web.xml,
 * all information in the class will be same whatever the call execute.
 * @author J
 */
public class JServletContext {
	
	private final Logger LOGGER=LoggerFactory.getLogger(getClass());

	List<Servlet> servlets=new ArrayList<JServletContext.Servlet>();
	
	List<Filter> filters=new ArrayList<JServletContext.Filter>();
	
	List<String> servletUrlMappings=new ArrayList<String>();
	
	List<String> filterUrlMappings=new ArrayList<String>();
	
	List<String> servletUrlMappingsWithoutStarAppend=new ArrayList<String>();
	
	List<String> filterUrlMappingsWithoutStarAppend=new ArrayList<String>();
	
	private JFilterFilter filterFilter;
	
	private JServletFilter servletFilter;
	
	JServletContext(JFilterFilter filterFilter,JServletFilter servletFilter) {
		this.filterFilter=filterFilter;
		this.servletFilter=servletFilter;
	}
	
	
	class Filter{
		String name;
		List<String> urlMappings;
		List<String> servletNameMappings;
		Class<?> _class;
		String className;
	}

	class Servlet{
		String name;
		List<String> urlMappings;
		Class<?> _class;
		String className;
	}
	
	/**
	 * put the filter , together with do some filter on the filter.
	 * @param filterRegistration
	 * @param servletContext
	 */
	void putFilterRegistration(FilterRegistration filterRegistration,ServletContext servletContext){
		try{
		
			String filterClassNameDeclared=filterRegistration.getClassName();
			ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
			Class<?> clazz=JClassUtils.load(filterClassNameDeclared, classLoader);
			boolean filter=false;
			//first
			filter=filterFilter.filter(clazz);
			// then second
			if(!filter){
				filter=filterFilter.filter(filterRegistration, servletContext);
			}
			
			if(!filter){
				Filter filterInfo=new Filter();
				filterInfo._class=clazz;
				filterInfo.name=filterRegistration.getName();
				filterInfo.className=filterClassNameDeclared;
				filterInfo.urlMappings=new ArrayList<String>(filterRegistration.getUrlPatternMappings());
				filterInfo.servletNameMappings=new ArrayList<String>(filterRegistration.getServletNameMappings());
				filters.add(filterInfo);
				filterUrlMappings.addAll(filterInfo.urlMappings);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
	}
	
	/**
	 *  put the servlet , together with do some filter on the servlet.
	 * @param servletRegistration
	 * @param servletContext
	 */
	void putServletRegistration(ServletRegistration servletRegistration,ServletContext servletContext){
		try{
		
			String servletClassNameDeclared=servletRegistration.getClassName();
			ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
			Class<?> clazz=JClassUtils.load(servletClassNameDeclared, classLoader);
			boolean filter=false;
			//first
			filter=servletFilter.filter(clazz);
			// then second
			if(!filter){
				filter=servletFilter.filter(servletRegistration, servletContext);
			}
			
			if(!filter){
				Servlet servletInfo=new Servlet();
				servletInfo._class=clazz;
				servletInfo.name=servletRegistration.getName();
				servletInfo.className=servletClassNameDeclared;
				servletInfo.urlMappings=new ArrayList<String>(servletRegistration.getMappings());
				servlets.add(servletInfo);
				servletUrlMappings.addAll(servletInfo.urlMappings);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
	}
	
	/**
	 * if the servlet path is intercepted any one of all servlets.
	 * @param servletPath
	 * @return
	 */
	public boolean containServletPath(String servletPath){
		return contains(this.servletUrlMappings, servletPath);
	}
	
	/**
	 *  if the filter path is intercepted any one of all filters.
	 * @param filterPath
	 * @return
	 */
	public boolean containFilterPath(String filterPath){
		return contains(this.filterUrlMappings, filterPath);
	}
	
	private boolean contains(List<String> cols,String path){
		boolean isMath=false;
		if(cols!=null){
			for (Iterator<String> iterator = cols.iterator(); iterator
					.hasNext();) {
				String string =  iterator.next().trim();
				if(string.indexOf("*")!=-1){
					Pattern pattern=Pattern.compile(string.replace("*", ".*"));
					isMath=pattern.matcher(path).matches();
					if(isMath){
						break;
					}
				}
			}
		}
		return isMath;
	}
	
	
	private volatile List<String> jspServletUrls=null;
	
	private Object jspUrlSync=new Object();
	
	/**
	 * default to find {@link JJSPServiceServlet} , if multiple URL mappings found, return first.
	 * @return
	 */
	public String getJSPServletUrlMapping(){
		if(jspServletUrls==null){
			synchronized (jspUrlSync) {
				if(jspServletUrls==null){
					jspServletUrls=new ArrayList<String>();
					for(int i=0;i<servlets.size();i++){
						Servlet servlet=servlets.get(i);
						if(servlet._class==JJSPServiceServlet.class){
							jspServletUrls.addAll(servlet.urlMappings);
						}
					}
				}
			}
		}
		return jspServletUrls.get(0);
	}
	
	/**
	 * default to find {@link JJSPServiceServlet} , if multiple URL mappings found, return first.
	 * convert "/web/service/dispatch/*" -> "/web/service/dispatch" 
	 * @return
	 */
	public String getJSPServletUrlMappingResolvingStar(){
		String url=getJSPServletUrlMapping();
		String res="";
		if(JStringUtils.isNotNullOrEmpty(url)){
			for(int i=url.length();i>0;){
				i--;
				switch (url.charAt(i)) {
					case '*':
						continue;
					case '/':
						continue;
					default:
							break;
				}
				res= url.substring(0,(i+1));
				break;
			}
		}
		return res;
	}
	
}
