/**
 * 
 */
package j.jave.framework.components.test.action;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.components.core.context.SpringContextSupport;
import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.components.multi.version.JComponentVersionSpringApplicationSupport;
import j.jave.framework.components.multi.version.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;
import j.jave.framework.components.multi.version.web.JComponentVersionWeb;

import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;

/**
 * @author J
 */
@Controller(value="sample.sampleaction")
public class SampleJSPAction extends JSPActionSupport {

	
	public String toSample(){
		return "/WEB-INF/jsp/sample/sample.jsp";
	}
	
	
	public String testMultiRequest(){
		
		
		Map<String, Object> parameters= httpContext.getParameters();
		if(parameters!=null){
			String print="";
			for (Iterator<Entry<String, Object> > iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object>  entry =  iterator.next();
				String param=entry.getKey()+"="+entry.getValue();
				print=print+param+"&";
				if(print.length()/1000>1){
					LOGGER.debug(print);
					print="";
				}
			}
		}
		return JJSON.get().format(parameters);
	}
	
	public String deployComponent() throws Exception{
		String jarFilePath=getParameter("file");
		URI uri=new URI(jarFilePath);
		ComponentVersionApplication application=  JComponentVersionSpringApplicationSupport.loadComponent(
				SpringContextSupport.getApplicationContext(), new URL[]{uri.toURL()});
		return JJSON.get().format(application.getApp());
	}
	
	public String removeComponent() throws Exception{
		String component=getParameter(JComponentVersionWeb.componnet);
		JComponentVersionSpringApplicationSupport.removeComponent(component);
		SpringContextSupport.getApplicationContext();
		return JJSON.get().format("OK");
		
	}
	
	
	
	
	
	
	
	
}
