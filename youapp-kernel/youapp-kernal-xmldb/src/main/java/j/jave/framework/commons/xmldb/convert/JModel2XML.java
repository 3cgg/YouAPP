package j.jave.framework.commons.xmldb.convert;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.detect.JColumnInfo;
import j.jave.framework.commons.model.support.detect.JModelDetect;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.xml.dom4j.util.JXMLUtils;
import j.jave.framework.commons.xmldb.JXMLData;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class JModel2XML {

private static JModel2XML model2xml=new JModel2XML();
	
	private JModel2XML(){}
	
	public static JModel2XML get(){
		return model2xml;
	}
	
	JElementHelper elementHelper = JElementHelper.get();

	private JModelDetect modelDetect = JModelDetect.get();

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
	
	
	public void convert(JXMLData xmlData,File file) throws Exception{
		Document document=DocumentHelper.createDocument();
		Element rootElement=elementHelper.createRoot(xmlData);
		document.add(rootElement);
		Iterator<JBaseModel> iterator=  xmlData.values().iterator();
		while(iterator.hasNext()){
			JBaseModel baseModel=iterator.next();
			Element modelElement=modelToXML(baseModel);
			rootElement.add(modelElement);
		}
		FileOutputStream fileOutputStream=null;
		try{
			fileOutputStream=new FileOutputStream(file);
			JXMLUtils.write(document, fileOutputStream);
		}catch(Exception e){
			throw e;
		}finally{
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		
	}
	
	
	
	
	
	
	
	
}
