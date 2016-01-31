package j.jave.kernal.xmldb;

import j.jave.kernal.jave.exception.JConcurrentException;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JURIUtils;
import j.jave.kernal.xml.dom4j.xmldb.JDom4jDefaultModelXMLService;
import j.jave.kernal.xmldb.exception.JXMLException;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class JXMLDataSource {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * URI ,  file:/ , http:// 
	 */
	protected String uri;
	
	/**
	 * KEY is xml name
	 * INNER MAP KEY : PRIMARY
	 */
	private static Map<String, JXMLData> datas=new ConcurrentHashMap<String, JXMLData>(1000);
	
	private static Map<String, String> classNameXmlNames=new HashMap<String, String>();
	
	private static JModelXMLService modelXMLService=new JDom4jDefaultModelXMLService();
	
	/**
	 * scan all {@link #eventQueue} to pop a highest event to execute.
	 */
	private ScheduledExecutorService xmlDataPersistenceExecutor =Executors.newScheduledThreadPool(1,new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "XML Data Persistence");
		}
	});
	
	private void setup(){
		xmlDataPersistenceExecutor.schedule(xmlDataPersistenceTask, 10, TimeUnit.SECONDS);
	}
	
	Runnable xmlDataPersistenceTask=new Runnable() {
		
		@Override
		public void run() {
			try{
				write2Xml();
			}catch(Exception e){
				LOGGER.info("write data to xml, error : "+e.getMessage(),e);
			}
		}
	};
	
	/**
	 * only always running in the only background one thread. 
	 * @throws Exception 
	 */
	private void write2Xml() throws Exception{
		try{
			// attempt to get lock
			while(true){
				if (lock.tryLock() ||lock.tryLock(2, TimeUnit.SECONDS)) {
					 break;
				}
			}
			JCollectionUtils.each(datas, new JCollectionUtils.Callback<String, JXMLData>() {
				@Override
				public void process(String key, JXMLData value)
						throws Exception {
					
					if(value.isXmlDataChanged()){
						modelXMLService.write(value, new File(new URI(JURIUtils.append(uri, value.getXmlName()+".xml"))));
						value.setXmlDataChanged(false);
					}
					
				}
			});
			
		}catch(Exception e){
			throw e;
		}
		finally{
			lock.unlock();
		}
	}
	
	
	public JXMLDataSource (){
	}
	
	protected final synchronized void init(){
		
		try {
			File file=new File(new URI(uri));
			
			if(!file.isDirectory()){
				throw new IllegalArgumentException("only accept directory, error uri : "+uri);
			}
			
			if(!file.exists()){
				file.mkdir();
			}
			
			File[]  files=file.listFiles();
			if(JCollectionUtils.hasInArray(files)){
				for (int i = 0; i < files.length; i++) {
					JFile xmlFile=new JFile(files[i]);
					JXMLData xmlData=null;
					try{
						xmlData= modelXMLService.read(xmlFile.getFile());
					}catch(Exception e){
						LOGGER.info("error occurs in the file : "+xmlFile.getFilename());
						throw e;
					}
					String name=xmlData.getXmlName();
					JAssert.state(name.equals(xmlFile.getFileNameNoExtension()), 
							"the attribute name must be the same as the file name, error : "
									+name+","+xmlFile.getFileNameNoExtension());
					datas.put(name, xmlData);
					classNameXmlNames.put(xmlData.getModelClass(), xmlData.getXmlName());
					JXMLSelect.putClass(xmlData.getModelClass());
				}
			}
			
			setup();
			
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
	}
	
	
	public JBaseModel getModel(JBaseModel model){
		return getModel(model.getId(), model.getClass());
	}
	
	public JBaseModel getModel(String id, Class<?> modelClass){
		String className=modelClass.getName();
		String xmlName=classNameXmlNames.get(className);
		JAssert.state(JStringUtils.isNotNullOrEmpty(xmlName), "xml file not found, error : ");
		JBaseModel baseModel= (JBaseModel) datas.get(xmlName).get(id);
		try {
			return baseModel==null?null:(JBaseModel) baseModel.clone();
		} catch (CloneNotSupportedException e) {
			throw new JXMLException(e);
		}
	}
	
	/**
	 * get all  
	 * @param modelClass
	 * @return
	 */
	public List<JBaseModel> gets(Class<? extends JBaseModel> modelClass){
		String className=modelClass.getName();
		String xmlName=classNameXmlNames.get(className);
		JAssert.state(JStringUtils.isNotNullOrEmpty(xmlName), "xml file not found, error : ");
		JXMLData xmlData=datas.get(xmlName);
		return new ArrayList<JBaseModel>(xmlData.values());
	}
	
	private static ReentrantLock lock=new ReentrantLock(true);
	
	
	/**
	 * lock added in the method.
	 * @param modified
	 * @throws Exception
	 */
	void merger(Map<String, JXMLData>  modified) throws Exception{
		
		try{
			lock.lock();
			
			// do validation under the locking state
			validate(modified);
			
			JCollectionUtils.each(modified, new JCollectionUtils.Callback<String, JXMLData>() {
				@Override
				public void process(String key, JXMLData value)
						throws Exception {
					
					JXMLData cloneXmlData= value.clone();
					
					JXMLData dbvalues=datas.get(key);
					
					if(dbvalues==null){
						cloneXmlData.setXmlVersion(1);
						cloneXmlData.setXmlDataChanged(true);
						datas.put(key, cloneXmlData);
						return ;
					}
					else{
						dbvalues.setXmlDataChanged(true);
						dbvalues.setXmlVersion(1+dbvalues.getXmlVersion());
					}
					
					for (Iterator<Entry<String,JBaseModel>> iterator = cloneXmlData.entrySet().iterator(); iterator
							.hasNext();) {
						Entry<String,JBaseModel> entry =  iterator.next();
						String id=entry.getKey();
						JBaseModel baseModel=entry.getValue();
						// may do more but this
						JBaseModel dbModel=dbvalues.get(id);
						if(dbModel!=null){
							// replace xml value , including increasing version. 
							int curVersion=baseModel.getVersion();
							baseModel.setVersion(++curVersion);
							dbvalues.put(id, baseModel);
						}
						else{
							dbvalues.put(id, baseModel);
						}
					}
					
				}
			});
			
			
		}catch(Exception e){
			LOGGER.error("some unexpected exception occurs while mergering data", e);
			throw e;
		}
		finally{
			lock.unlock();
		}
	}
	
	
	private void validate(Map<String, JXMLData>  modified) throws Exception{
			JCollectionUtils.each(modified, new JCollectionUtils.Callback<String, JXMLData>() {
				@Override
				public void process(String key, JXMLData value)
						throws Exception {
					
					JXMLData dbvalues=datas.get(key);
					if(dbvalues==null){
						return ;
					}
					
					for (Iterator<Entry<String,JBaseModel>> iterator = value.entrySet().iterator(); iterator
							.hasNext();) {
						Entry<String,JBaseModel> entry =  iterator.next();
						String id=entry.getKey();
						JBaseModel baseModel=entry.getValue();
						// may do more but this
						JBaseModel dbModel=dbvalues.get(id);
						if(dbModel!=null){
							// validate version change. 
							int oldVersion=dbModel.getVersion();
							int curVersion=baseModel.getVersion();
							if(oldVersion!=curVersion){
								throw new JConcurrentException("version changed : "+baseModel.getClass().getName()
										+"old version : "+oldVersion+" current version : "+curVersion);
							}
						}
					}
				}
			});
	}
	
	public List<Map<String, Object>> select(String jql,Map<String, Object> params){
		
		JXMLSelect select=(JXMLSelect) new JXMLSelect().parse(jql, params);
		return select.select();
	}
	
	public <M extends JBaseModel> List<M> select(String jql,Map<String, Object> params,Class<M> clazz){
		return new JXMLSelect().parse(jql, params).select(clazz);
	}
	
	
}
