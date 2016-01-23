package j.jave.framework.commons.xmldb.ele;

import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.xmldb.utils.JAttributeKVFilter;
import j.jave.framework.commons.xmldb.utils.JElementHandler;
import j.jave.framework.commons.xmldb.utils.JXMLUtils;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

public class JEleObject implements JEleGet{
	
	private Document document;
	
	private Element element;
	
	public JEleObject(Document document) {
		JAssert.notNull("document");
		this.document=document;
		
		// extract root element
		element=document.getRootElement();
		
	}
	
	public JEleObject(Element element) {
		JAssert.notNull("element");
		this.element=element;
		
		// detect document
		if(element.isRootElement()){
			document=element.getDocument();
		}
		
	}
	
	public JEleObject(Document includeDocument,Element element) {
		
		if(element!=null&&includeDocument!=null){
			JAssert.state(element.getDocument()!=includeDocument, "element nust be relationship with document.");
		}
		
		if(element==null){
			this.document=includeDocument;
			// extract root element
			element=document.getRootElement();
		}
		
		if(includeDocument==null){
			this.element=element;
			// detect document
			if(element.isRootElement()){
				document=element.getDocument();
			}
		}
	}
	
	@Override
	public Element getEleByUppercaseID(String ID) {
		return document.elementByID(ID); 
	}
	
	@Override
	public Element getEleById(final String id) {
		
		//document.elementByID(id);  UPPERCASE ID must exists as attribute in any element.
		
		final List<Element> elements=new ArrayList<Element>();
		
		JXMLUtils.each(element, new JElementHandler() {
			@Override
			public boolean handleElement(Element element) {
				Attribute attribute = element.attribute("id");
				if(attribute!=null&&id.equals(attribute.getValue())){
					elements.add(element);
					return false;
				}
				return true;
			}
		});
		return elements.isEmpty()?null:elements.get(0);
	}

	@Override
	public List<Element> getElesByName(final String name) {
		final List<Element> elements=new ArrayList<Element>();
		
		JXMLUtils.each(element, new JElementHandler() {
			@Override
			public boolean handleElement(Element element) {
				Attribute attribute = element.attribute("name");
				if(attribute!=null&&name.equals(attribute.getValue())){
					elements.add(element);
				}
				return true;
			}
		});
		
		return elements;
	}

	@Override
	public List<Element> getElesByTagName(final String tagName) {
		final List<Element> elements=new ArrayList<Element>();
		
		JXMLUtils.each(element, new JElementHandler() {
			@Override
			public boolean handleElement(Element element) {
				if(tagName.equals(element.getName())){
					elements.add(element);
				}
				return true;
			}
		});
		
		return elements;
	}

	@Override
	public List<Element> getElesByClassName(String... cssClassNames) {

		final List<Element> elements=new ArrayList<Element>();
		
		final JAttributeKVFilter attributeKVFilter=new JAttributeKVFilter("class", " +", cssClassNames);
		
		JXMLUtils.each(element, 
				new JElementHandler() {
					@Override
					public boolean handleElement(Element element) {
						elements.add(element);
						return true;
					}
				},
				attributeKVFilter
				);
		
		return elements;
	}

}
