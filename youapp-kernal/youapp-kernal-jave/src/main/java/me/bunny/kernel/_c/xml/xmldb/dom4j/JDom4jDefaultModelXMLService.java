package me.bunny.kernel._c.xml.xmldb.dom4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.model.support.JSQLJavaTypeHelper;
import me.bunny.kernel._c.model.support.detect.JColumnInfo;
import me.bunny.kernel._c.model.support.detect.JModelDetect;
import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.xml.dom4j.util.JXMLUtils;
import me.bunny.kernel._c.xml.xmldb.JModelXMLService;
import me.bunny.kernel._c.xml.xmldb.JXMLData;

public class JDom4jDefaultModelXMLService implements JModelXMLService {

	JElementHelper elementHelper = JElementHelper.get();

	private JModelDetect modelDetect = JModelDetect.get();

	@SuppressWarnings("unchecked")
	public Element modelToXML(final JBaseModel model) throws Exception {
		final Element modelElement = elementHelper.newModelElement(null);
		elementHelper.addModelElementId(modelElement, model.getId());

		List<JColumnInfo> columnInfos = modelDetect.getColumnInfos(model);

		JCollectionUtils.each(columnInfos, new JCollectionUtils.CollectionCallback<JColumnInfo>() {
			@Override
			public void process( JColumnInfo value)throws Exception {
				if(!"ID".equalsIgnoreCase(value.getName())){
					Element fieldElement=elementHelper.newFieldElement(modelElement);
					elementHelper.addFieldName(fieldElement, value.getName());
					elementHelper.addFieldValue(fieldElement, JStringUtils.toString(value.getField().get(model)));
				}
				}
			});
		
		return modelElement;
	}
	
	@Override
	public void write(JXMLData xmlData, OutputStream outputStream)
			throws Exception {
		Document document=DocumentHelper.createDocument();
		Element rootElement=elementHelper.createRoot(xmlData);
		document.add(rootElement);
		Iterator<JBaseModel> iterator=  xmlData.values().iterator();
		while(iterator.hasNext()){
			JBaseModel baseModel=iterator.next();
			Element modelElement=modelToXML(baseModel);
			rootElement.add(modelElement);
		}
		try{
			JXMLUtils.write(document, outputStream);
		}catch(Exception e){
			throw e;
		}finally{
		
		}
	}

	@Override
	public void write(JXMLData xmlData, File file) throws Exception {
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		try{
			write(xmlData, fileOutputStream);
		}catch(Exception e){
			throw e;
		}finally{
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private JBaseModel readFromElement(final Element modelElement,Class<? extends JBaseModel> clazz) throws Exception{
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
	private JXMLData readFromDocument(Document document) throws Exception{
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
			JBaseModel baseModel=readFromElement(modelElements.next(), clazz);
			data.put(baseModel.getId(), baseModel);
		}
		return data;
		
	}
	
	@Override
	public JXMLData read(InputStream inputStream) throws Exception {
		return readFromDocument(JXMLUtils.loadDocument(inputStream));
	}

	@Override
	public JXMLData read(File file) throws Exception {
		
		FileInputStream fileInputStream=new FileInputStream(file);
		try{
			return readFromDocument(JXMLUtils.loadDocument(fileInputStream));
		}catch(Exception e){
			throw e;
		}finally{
			if(fileInputStream!=null){
				fileInputStream.close();
			}
		}
	}

}
