package j.jave.kernal.jave;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.node.JW3CStandardGetter;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * the basic environment configuration 
 * @author J
 *
 */
public class JConfiguration extends HashMap<String, Object>{
	
	private static final JLogger LOGGER =JLoggerFactory.getLogger(JConfiguration.class);
	
	private final static JConfig defaultConfig=new JConfig();
	
	private static class JConfig extends HashMap<String, String>{
		
		private JConfig() {
			try{
				
				String defaultXML="youapp-default.xml";
				File defaultFile=new File(Thread.currentThread().getContextClassLoader().getResource(defaultXML).toURI());
				if(defaultFile.exists()){
					loadConfiguration(defaultFile);
				}
				File file=new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
				if(file.isDirectory()){
					File[] files=file.listFiles();
					Pattern pattern=Pattern.compile("^youapp-[-a-zA-Z_0-9]+[.]xml$");
					for(int i=0;i<files.length;i++){
						File file2=files[i];
						String fileName=file2.getName().replaceAll(" ", "");
						if(!defaultXML.equalsIgnoreCase(fileName)
								&&pattern.matcher(fileName).matches()){
							loadConfiguration(file2);
						}
					}
				}
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				throw new JInitializationException(e);
			}
		}
		   
		private void loadConfiguration(File file) throws Exception{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document =builder.parse(file); 
			JW3CStandardGetter getter=new JW3CStandardGetter(document.getDocumentElement());
			List<?> nodes= getter.getNodesByTagName("property");
			if(JCollectionUtils.hasInCollect(nodes)){
				for (Iterator<?> iterator = nodes.iterator(); iterator
						.hasNext();) {
					org.w3c.dom.Node node = (org.w3c.dom.Node) iterator.next();
					String name=getValueByKey("name", node);
					if(JStringUtils.isNotNullOrEmpty(name)){
						String value=getValueByKey("value", node);
						JConfig.this.put(name, value);
					}
				}
			}
		}
		
		private String getValueByKey(String key,org.w3c.dom.Node node ){
			JW3CStandardGetter getter=new JW3CStandardGetter(node);
			List<?> nodes= getter.getNodesByTagName(key);
			if(JCollectionUtils.hasInCollect(nodes)){
				org.w3c.dom.Node nd=(org.w3c.dom.Node) nodes.get(0);
				return nd.getTextContent();
			}
			return null;
		}
		
	}
	
	private final static JConfiguration configuration=new JConfiguration();
	public static JConfiguration get(){
		return configuration;
	}
	
	public Object get(String  key, Object defaultValue){
		
		if(containsKey(key)){
			return get(key);
		}
		
		if(defaultConfig.containsKey(key)){
			return defaultConfig.get(key);
		}
		Object value=getFromSystem(key);
		return value==null? defaultValue:value;
	}
	
	private Object getFromSystem(String key){
		Object object=System.getProperty(key);
		return object;
	}
	
	public String getString(String  key, String defaultValue){
		Object obj=get(key, defaultValue);
		return (String) obj;
	}
	
	public int getInt(String  key, int defaultValue){
		Object obj=get(key, defaultValue);
		return (Integer)obj;
	}
	
	public long getLong(String  key, long defaultValue){
		Object obj=get(key, defaultValue);
		return (Long)obj;
	}
	
	public double getDouble(String  key, double defaultValue){
		Object obj=get(key, defaultValue);
		return (Double)obj;
	}
	
}
