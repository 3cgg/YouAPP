package j.jave.framework.commons.xml.dom4j.xmldb;

import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.xmldb.JXMLData;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class JElementHelper {

	public static JElementHelper elementHelper=new JElementHelper();
	private JElementHelper(){}
	public static JElementHelper get(){
		return elementHelper;
	}
	
	public static final String COLUMN_ID="ID";
	public String getModelId(Element element){
		return element.attributeValue("ID");
	}
	
	public String getDocumentType(Document document){
		Element rootElement=document.getRootElement();
		if(rootElement==null) return null;
		String type=rootElement.attributeValue("TYPE");
		return JStringUtils.isNullOrEmpty(type)? null:type.trim();
	}
	
	public long getDocumentVersion(Document document){
		Element rootElement=document.getRootElement();
		if(rootElement==null) return -1;
		String version=rootElement.attributeValue("V");
		return JStringUtils.isNullOrEmpty(version)? -1:Long.parseLong(version);
	}
	
	public String getDocumentName(Document document){
		Element rootElement=document.getRootElement();
		if(rootElement==null) return null;
		String type=rootElement.attributeValue("NAME");
		return JStringUtils.isNullOrEmpty(type)? null:type.trim();
	}
	
	public int getElementVersion(Element element){
		return Integer.parseInt(element.attributeValue("VERSION"));
	}
	
	public Element newModelElement(Element parent){
		if(parent==null){
			return DocumentHelper.createElement("model");
		}
		return parent.addElement("model");
	}
	
	public void addModelElementId(Element modelElement,String id){
		modelElement.addAttribute("ID", id);
	}
	
	public Element newFieldElement(Element parent){
		if(parent==null){
			return DocumentHelper.createElement("field");
		}
		return parent.addElement("field");
	}
	
	public void addFieldName(Element element,String name){
		element.addAttribute("name", name);
	}
	
	public String getFieldName(Element element){
		return element.attributeValue("name");
	}
	
	
	public void addFieldValue(Element element,String value){
		element.addAttribute("value", value);
	}
	
	public String getFieldValue(Element element){
		return element.attributeValue("value");
	}
	
	public Element createRoot(JXMLData xmlData){
		Element modelsElement=DocumentHelper.createElement("models");
		modelsElement.addAttribute("TYPE", xmlData.getModelClass());
		modelsElement.addAttribute("NAME",xmlData.getXmlName());
		modelsElement.addAttribute("V", String.valueOf(xmlData.getXmlVersion()));
		return modelsElement;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
