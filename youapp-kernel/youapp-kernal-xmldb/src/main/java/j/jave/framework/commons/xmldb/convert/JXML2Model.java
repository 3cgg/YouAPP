package j.jave.framework.commons.xmldb.convert;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.JSQLJavaTypeHelper;
import j.jave.framework.commons.model.support.detect.JColumnInfo;
import j.jave.framework.commons.model.support.detect.JModelDetect;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.xml.dom4j.util.JXMLUtils;
import j.jave.framework.commons.xmldb.JXMLData;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class JXML2Model {

	private static JXML2Model xml2Model=new JXML2Model();
	
	private JXML2Model(){}
	
	public static JXML2Model get(){
		return xml2Model;
	}
	
	JElementHelper elementHelper=JElementHelper.get();
	
	private JModelDetect modelDetect = JModelDetect.get();
	
	@SuppressWarnings("unchecked")
	JBaseModel convert(final Element modelElement,Class<? extends JBaseModel> clazz) throws Exception{
		if(modelElement==null) return null;
		
		final Map<String, String> columnValues=new HashMap<String, String>();
		
		columnValues.put(JElementHelper.COLUMN_ID, elementHelper.getModelId(modelElement));
		
		Iterator<Element> fieldElements=modelElement.elementIterator();
		while(fieldElements.hasNext()){
			Element fieldElement=fieldElements.next();
			String columnName=elementHelper.getFieldName(fieldElement);
			String columnValue=elementHelper.getFieldValue(fieldElement);
			columnValues.put(columnName, columnValue);
		}
		
		final JBaseModel baseModel=clazz.newInstance();
		
		List<JColumnInfo> columnInfos = modelDetect.getColumnInfos(clazz);
		JCollectionUtils.each(columnInfos, new JCollectionUtils.CollectionCallback<JColumnInfo>() {
			@Override
			public void process(JColumnInfo value)throws Exception {
				Object object=null;
				if(columnValues.containsKey(value.getName())){
					String string=columnValues.get(value.getName());
					object=JSQLJavaTypeHelper.get(value.getColumn()).convert(string);
				}
				else{
					object=value.getDefaultValue();
				}
				System.out.println(value.getField().getName()+"--------"+object);
				value.getField().set(baseModel, object);
				}
			});
		
		return baseModel;
	}

	@SuppressWarnings("unchecked")
	public JXMLData convert(Document document) throws Exception{
		if(document==null) return null;
		
		String type= elementHelper.getDocumentType(document);
		JAssert.state(JStringUtils.isNotNullOrEmpty(type), "type in the root is missing");
		
		String xmlName= elementHelper.getDocumentName(document);
		JAssert.state(JStringUtils.isNotNullOrEmpty(xmlName), "name in the root is missing");
		
		long version= elementHelper.getDocumentVersion(document);
		JAssert.state(version>=0, "version in the root is missing");
		
		JXMLData data=new JXMLData();
		data.setModelClass(type);
		data.setXmlVersion(version);
		data.setXmlName(xmlName);
		Iterator<Element> modelElements = document.getRootElement().elementIterator();
		while(modelElements.hasNext()){
			Class<? extends JBaseModel> clazz=JClassUtils.load(type, Thread.currentThread().getContextClassLoader());
			JBaseModel baseModel=convert(modelElements.next(), clazz);
			data.put(baseModel.getId(), baseModel);
		}
		return data;
		
	}
	
	
	public JXMLData convert(File file) throws Exception{
		return convert(JXMLUtils.loadDocument(new FileInputStream(file)));
	}
	
}
