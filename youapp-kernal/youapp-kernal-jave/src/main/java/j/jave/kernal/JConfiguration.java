package j.jave.kernal;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JClassPathUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JJARUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.node.JW3CStandardGetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
		
		private void processJarFile(File file) throws Exception{
			final String jarFilePath=file.getAbsolutePath();
			Set<JarEntry> jarEntries= JJARUtils.getJarEntries(jarFilePath, "^youapp-[-a-zA-Z_0-9]+[.]xml$");
			JCollectionUtils.each(jarEntries, new JCollectionUtils.CollectionCallback<JarEntry>() {
				public void process(JarEntry jarEntry) throws Exception {
					JarFile jarFile=null;
					try{
						jarFile= new JarFile(jarFilePath);
						LOGGER.info("scanning configuration from : "+ jarFilePath+"!/"+jarEntry.getName());
						loadConfiguration(jarFile.getInputStream(jarEntry));
					}finally{
						if(jarFile!=null){
							jarFile.close();
						}
					}
				};
			});
		}
		
		private void processFileFile(File file) throws Exception{
			Pattern pattern=Pattern.compile("^youapp-[-a-zA-Z_0-9]+[.]xml$");
			String fileName=file.getName().replaceAll(" ", "");
			if(pattern.matcher(fileName).matches()){
				LOGGER.info("scanning configuration from : "+ file.getAbsolutePath());
				loadConfiguration(file);
			}
			
		}
		
		
		/**
		 * 
		 * @param files  ALL files as the classpath
		 * @throws Exception
		 */
		private void processFiles(List<File> files) throws Exception{
			JCollectionUtils.each(files, new JCollectionUtils.CollectionCallback<File>() {
				@Override
				public void process(File file) throws Exception {
					if(file.getName().endsWith(".jar")){
						processJarFile(file);
					}
					else if(file.isFile()){
						processFileFile(file);
					}
					else if(file.isDirectory()){
						File[] files=file.listFiles();
						for(int i=0;i<files.length;i++){
							File innerFile=files[i];
							if(innerFile.getName().endsWith(".jar")){
								processJarFile(innerFile);
							}
							else if(innerFile.isFile()){
								processFileFile(innerFile);
							}
						}
					}
				}
			});
		}
		
		private JConfig() {
			try{
				final String defaultXML="youapp-default.xml";
				InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultXML);
				if(inputStream!=null){
					loadConfiguration(inputStream);
				}
				
				//classpath
				LOGGER.info("scanning from classpath.");
				List<File> files= JClassPathUtils.getRuntimeClassPathFiles();
				processFiles(files);
				
//				// for web
//				URL libUrl=Thread.currentThread().getContextClassLoader().getResource("../lib");
//				LOGGER.info("expected to find [WEB-INF/lib] : "+ (libUrl==null?"NULL":libUrl.toString()));
//				if(libUrl!=null){
//					File file=new File(libUrl.toURI());
//					if(file.isDirectory()){
//						processFiles(Arrays.asList(file));
//					}
//				}
//				
//				URI uri=Thread.currentThread().getContextClassLoader().getResource("").toURI();
//				LOGGER.info("expected to find [WEB-INF/classes] : "+ (uri==null?"NULL":uri.toString()));
//				File file=new File(uri);
//				if(file.isDirectory()){
//					processFiles(Arrays.asList(file));
//				}
				
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				throw new JInitializationException(e);
			}
		}
		
		private void loadConfiguration(InputStream inputStream) throws Exception{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document =builder.parse(inputStream);
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
		
		private void loadConfiguration(File file) throws Exception{
			loadConfiguration(new FileInputStream(file));
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
	
	public Boolean getBoolean(String  key, boolean defaultValue){
		Object obj=get(key, defaultValue);
		if(!Boolean.class.isInstance(obj)){
			if("on".equals(String.valueOf(obj).trim())){
				return true;
			}
			if("off".equals(String.valueOf(obj).trim())){
				return false;
			}
			return Boolean.valueOf(String.valueOf(obj));
		}
		return (Boolean)obj;
	}
	
	public int getInt(String  key, int defaultValue){
		Object obj=get(key, defaultValue);
		if(!Integer.class.isInstance(obj)){
			return Integer.valueOf(String.valueOf(obj));
		}
		return (Integer)obj;
	}
	
	public long getLong(String  key, long defaultValue){
		Object obj=get(key, defaultValue);
		if(!Long.class.isInstance(obj)){
			return Long.valueOf(String.valueOf(obj));
		}
		return (Long)obj;
	}
	
	public double getDouble(String  key, double defaultValue){
		Object obj=get(key, defaultValue);
		if(!Double.class.isInstance(obj)){
			return Double.valueOf(String.valueOf(obj));
		}
		return (Double)obj;
	}
	
	public Set<String> allKeys(String regex){
		Set<String> keys=new HashSet<String>();
		Pattern pattern=Pattern.compile(regex);
		
		for(String key:defaultConfig.keySet()){
			if(pattern.matcher(key).matches()){
				keys.add(key);
			}
		}
		
		for(String key:this.keySet()){
			if(pattern.matcher(key).matches()){
				keys.add(key);
			}
		}
		return keys;
	}
	
}
