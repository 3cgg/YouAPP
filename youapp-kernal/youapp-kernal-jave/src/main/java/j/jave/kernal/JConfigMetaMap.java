package j.jave.kernal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactoryProvider;
import j.jave.kernal.jave.utils.JClassPathUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JJARUtils;
import j.jave.kernal.jave.utils.JLangUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.node.JW3CStandardGetter;

class JConfigMetaMap extends HashMap<String, JConfigMeta>{
	
	private static final JLogger LOGGER =JLoggerFactoryProvider.console()
			.getLogger(JConfigMetaMap.class);
	
	private final String defaultXML="youapp-default.xml";
	
	JConfigMetaMap() {
		try{
			InputStream inputStream=Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(defaultXML);
			if(inputStream!=null){
				loadConfiguration(inputStream);
			}
			//classpath
			LOGGER.info(" to find more config...");
			List<File> files= JClassPathUtils.getRuntimeClassPathFiles();
			for (File file : files) {
				String info="classpath file : "+file.getAbsolutePath();
				System.out.println(info);
				LOGGER.info(info);
			}
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
	
	
	private void processJarFile(File file) throws Exception{
		final String jarFilePath=file.getAbsolutePath();
		Set<JarEntry> jarEntries= JJARUtils.getJarEntries(jarFilePath, "^youapp-[-a-zA-Z_0-9]+[.]xml$");
		JCollectionUtils.each(jarEntries, new JCollectionUtils.CollectionCallback<JarEntry>() {
			public void process(JarEntry jarEntry) throws Exception {
				JarFile jarFile=null;
				try{
					if(jarEntry.getName().endsWith(defaultXML)){
						LOGGER.info("invalid path name : "+ jarFilePath+"!/"+jarEntry.getName());
						return;
					}
					jarFile= new JarFile(jarFilePath);
					LOGGER.info("scanning configuration from : "+ jarFilePath+"!/"+jarEntry.getName());
					System.out.println("read conf : "+ jarFilePath+"!/"+jarEntry.getName());
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
			if(fileName.endsWith(defaultXML)){
				LOGGER.info("invalid path name : "+file.getAbsolutePath());
				return;
			}
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
					JConfigMeta configMeta=new JConfigMeta();
					String value=getValueByKey("value", node);
					String desc=getValueByKey("description", node);
					String override=getValueByKey("override", node);
					configMeta.setName(name);
					configMeta.setValue(value);
					configMeta.setDescription(desc);
					if(JStringUtils.isNotNullOrEmpty(override)){
						boolean isOverride=JLangUtils.booleanValue(override);
						configMeta.setOverride(isOverride);
					}
					put(name, configMeta);
					System.out.println(configMeta.getName()+"="+configMeta.getValue()+";"+configMeta.isOverride()); 
				}
			}
		}
	}
	
	private void loadConfiguration(File file) throws Exception{
		System.out.println("read conf: "+file.getAbsolutePath());
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