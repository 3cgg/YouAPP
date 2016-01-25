package j.jave.module.crawl.kernel;

import j.jave.framework.commons.xml.node.JClassNameGetter;
import j.jave.framework.commons.xml.node.JIDGetter;
import j.jave.framework.commons.xml.node.JMixedGetter;
import j.jave.framework.commons.xml.node.JNameGetter;
import j.jave.framework.commons.xml.node.JTagNameGetter;
import j.jave.framework.commons.xml.node.JXPathGetter;

import java.util.HashMap;

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
