package j.jave.kernal.xmldb;

import j.jave.kernal.jave.model.JBaseModel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class JXMLData extends ConcurrentHashMap<String,JBaseModel> implements Cloneable {
	
	private long xmlVersion ;
	
	private String xmlName;
	
	private String modelClass;

	// additional properties for enhancing performance
	private boolean xmlDataChanged;
	
	public boolean isXmlDataChanged() {
		return xmlDataChanged;
	}

	public void setXmlDataChanged(boolean xmlDataChanged) {
		this.xmlDataChanged = xmlDataChanged;
	}

	public long getXmlVersion() {
		return xmlVersion;
	}

	public void setXmlVersion(long xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public String getModelClass() {
		return modelClass;
	}

	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}
	
	
	@Override
	public JXMLData clone() throws CloneNotSupportedException {
		JXMLData clone=new JXMLData();
		Iterator<Entry<String, JBaseModel>> iterator= this.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, JBaseModel> entry=iterator.next();
			clone.put(entry.getKey(), entry.getValue().clone());
		}
		return clone;
	}
	
}
