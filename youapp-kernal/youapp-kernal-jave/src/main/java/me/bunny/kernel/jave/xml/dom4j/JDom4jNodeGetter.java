package me.bunny.kernel.jave.xml.dom4j;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import me.bunny.kernel.jave.utils.JAssert;
import me.bunny.kernel.jave.xml.dom4j.util.JAttributeKVFilter;
import me.bunny.kernel.jave.xml.dom4j.util.JElementHandler;
import me.bunny.kernel.jave.xml.dom4j.util.JXMLUtils;
import me.bunny.kernel.jave.xml.node.JClassNameGetter;
import me.bunny.kernel.jave.xml.node.JIDGetter;
import me.bunny.kernel.jave.xml.node.JMixedGetter;
import me.bunny.kernel.jave.xml.node.JNameGetter;
import me.bunny.kernel.jave.xml.node.JTagNameGetter;
import me.bunny.kernel.jave.xml.node.JXPathGetter;

public class JDom4jNodeGetter  implements JIDGetter, JNameGetter, JTagNameGetter,
JXPathGetter, JClassNameGetter, JMixedGetter{
	
	private Document document;
	
	private Element element;
	
	public JDom4jNodeGetter(Document document) {
		JAssert.notNull("document");
		this.document=document;
		
		// extract root element
		element=document.getRootElement();
	}
	
	public JDom4jNodeGetter(Element element) {
		JAssert.notNull("element");
		this.element=element;
		
		// detect document
		if(element.isRootElement()){
			document=element.getDocument();
		}
		
	}
	
	public JDom4jNodeGetter(Document includeDocument,Element element) {
		
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
	
	public Element getEleByUppercaseID(String ID) {
		return document.elementByID(ID); 
	}
	
	private Element getEleById(final String id) {
		
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

	private List<Element> getElesByName(final String name) {
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

	private List<Element> getElesByTagName(final String tagName) {
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

	private List<Element> getElesByClassName(String... cssClassNames) {

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

	@Override
	public List<?> getNodes(String... keyValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getNodesByMixed(String mixed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getNodesByClassName(String... className) {
		return getElesByClassName(className);
	}

	@Override
	public List<?> getNodesByXPath(String xpath) {
		
		
		
		return null;
	}

	@Override
	public List<?> getNodesByTagName(String tagName) {
		return getElesByTagName(tagName);
	}

	@Override
	public List<?> getNodesByName(String name) {
		return getElesByName(name);
	}

	@Override
	public List<?> getNodesById(String id) {
		List<Element> elements=new ArrayList<Element>();
		Element element=getEleById(id);
		if(element!=null){
			elements.add(element);
		}
		return elements;
	}

}
