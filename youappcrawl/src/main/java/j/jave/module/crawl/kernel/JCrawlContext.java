package j.jave.module.crawl.kernel;

import java.util.HashMap;

import me.bunny.kernel._c.xml.node.JClassNameGetter;
import me.bunny.kernel._c.xml.node.JIDGetter;
import me.bunny.kernel._c.xml.node.JMixedGetter;
import me.bunny.kernel._c.xml.node.JNameGetter;
import me.bunny.kernel._c.xml.node.JTagNameGetter;
import me.bunny.kernel._c.xml.node.JXPathGetter;

public class JCrawlContext extends HashMap{

	/*
	private static final ThreadLocal<JCrawlContext> LOCAL=new ThreadLocal<JCrawlContext>();
	
	public static void set(JCrawlContext context){
		LOCAL.set(context);
	}
	
	public static JCrawlContext get(){
		return LOCAL.get();
	}
	*/
	public JXPathGetter getXPathGetter(){
		return (JXPathGetter) get(JPropertiesKeys.NODE_XPATH_GETTER);
	}
	
	public JNameGetter getNameGetter(){
		return (JNameGetter) get(JPropertiesKeys.NODE_NAME_GETTER);
	}
	
	public JClassNameGetter getClassNameGetter(){
		return (JClassNameGetter) get(JPropertiesKeys.NODE_CLASSNAME_GETTER);
	}
	
	public JTagNameGetter getTagNameGetter(){
		return (JTagNameGetter) get(JPropertiesKeys.NODE_TAGNAME_GETTER);
	}
	
	public JIDGetter getIDGetter(){
		return (JIDGetter) get(JPropertiesKeys.NODE_ID_GETTER);
	}
	
	public JMixedGetter getMixedGetter(){
		return (JMixedGetter) get(JPropertiesKeys.NODE_MIXED_GETTER);
	}
	
	public double getDouble(String key,double defaultValue){
		Object object=get(key);
		if(object==null){
			return defaultValue;
		}
		else{
			return Double.parseDouble((String) object);
		}
	}
	
}
