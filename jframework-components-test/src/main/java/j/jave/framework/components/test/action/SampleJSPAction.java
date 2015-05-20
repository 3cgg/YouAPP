/**
 * 
 */
package j.jave.framework.components.test.action;

import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.json.JJSON;

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
	
}
