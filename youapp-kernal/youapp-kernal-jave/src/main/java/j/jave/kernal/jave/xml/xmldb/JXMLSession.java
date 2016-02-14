package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.model.support.interceptor.JDefaultModelInvocation;
import j.jave.kernal.jave.proxy.JAtomicResourceSession;
import j.jave.kernal.jave.proxy.JAtomicResourceSessionHolder;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the class should not be used individually , but always together with JAtomicResourceSessionHolder
 * @author J
 *@see JAtomicResourceSessionHolder
 */
public class JXMLSession implements JAtomicResourceSession{
	
	/**
	 * KEY is XML name without extension.
	 * INNER MAP KEY : PRIMARY
	 */
	private Map<String, JXMLData> sessionDatas=new ConcurrentHashMap<String, JXMLData>(1000);
	
	private static JXMLDataSource xmlDataSource=JSinglePropertiesXMLDataSource.get();
	
	@Override
	public boolean cached() {
		return true;
	}

	@Override
	public void commit() throws Exception {
		
		// do validation before committing to xml data source.
		JCollectionUtils.each(sessionDatas, new JCollectionUtils.EntryCallback<String, JXMLData>() {
			@Override
			public void process(String key, JXMLData value) throws Exception {
				
				JAssert.state(JStringUtils.isNotNullOrEmpty(value.getModelClass()), " type is empty."); 
				
				JAssert.state(key.equals(value.getXmlName()), "key is not the same as xml file name, key : "+key+" xml name : "+value.getXmlName());
				
				for (Iterator<Entry<String,JBaseModel>> iterator = value.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String,JBaseModel> entry =  iterator.next();
					JBaseModel baseModel=entry.getValue();
					JDefaultModelInvocation<JBaseModel> defaultModelInvocation=new JDefaultModelInvocation<JBaseModel>(baseModel);
					defaultModelInvocation.proceed();
				}
			}
		});
		
		xmlDataSource.merger(sessionDatas);
		sessionDatas.clear();
	}

	@Override
	public void rollback() throws Exception {
		sessionDatas.clear();
	}
	
	public void update(JBaseModel baseModel){
		insert(baseModel);
	}
	
	public void insert(JBaseModel baseModel){
		String xmlName=baseModel.getClass().getAnnotation(JTable.class).name();
		JXMLData xmlData=sessionDatas.get(xmlName);
		if(xmlData==null){
			xmlData=new JXMLData();
			xmlData.setModelClass(baseModel.getClass().getName());
			xmlData.setXmlName(xmlName);
			sessionDatas.put(xmlName, xmlData);
		}
		xmlData.put(baseModel.getId(), baseModel);
	}
	
	public <T> T get(JBaseModel baseModel){
		return get(baseModel.getId(), baseModel.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String id, Class<? extends JBaseModel> modelClass){
		String xmlName=modelClass.getAnnotation(JTable.class).name();
		JXMLData xmlData= sessionDatas.get(xmlName);
		if(xmlData!=null&&xmlData.contains(id)){
			return (T) xmlData.get(id);
		}
		else{
			return (T) xmlDataSource.getModel(id, modelClass);
		}
	}
	
	public List<Map<String, Object>> select(String jql){
		return xmlDataSource.select(jql, null);
	}
	
	public List<Map<String, Object>> select(String jql,Map<String, Object> params){
		return xmlDataSource.select(jql, params==null?new HashMap<String, Object>():params);
	}
	
	public <M extends JBaseModel> List<M> select(String jql,Map<String, Object> params,Class<M> clazz){
		return xmlDataSource.select(jql, params==null?new HashMap<String, Object>():params, clazz);
	}
	
	public <M extends JBaseModel> List<M> select(String jql,Class<M> clazz){
		return select(jql, null, clazz);
	}

}
