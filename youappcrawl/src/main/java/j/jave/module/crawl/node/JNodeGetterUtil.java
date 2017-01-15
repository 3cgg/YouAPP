package j.jave.module.crawl.node;

import j.jave.module.crawl.kernel.JCrawlContext;
import me.bunny.kernel._c.xml.node.JNodeGetter;

public class JNodeGetterUtil {
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getNodeGetter(String protocol,JCrawlContext crawlContext){
		
		if(JNodeGetter.XPATH_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getXPathGetter();
		}
		else if(JNodeGetter.TAG_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getTagNameGetter();
		}
		else if(JNodeGetter.NAME_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getNameGetter();
		}
		else if(JNodeGetter.CLASS_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getClassNameGetter();
		}
		else if(JNodeGetter.ID_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getClassNameGetter();
		}
		else if(JNodeGetter.MIXED_PROTOCOL.equals(protocol)){
			return (T) crawlContext.getMixedGetter();
		}
		return null;
	}
	
	
	
	
}
