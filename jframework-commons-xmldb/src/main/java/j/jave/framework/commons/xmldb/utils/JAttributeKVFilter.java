package j.jave.framework.commons.xmldb.utils;

import j.jave.framework.commons.utils.JArrays;
import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.utils.JStringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class JAttributeKVFilter implements JElementFilter {

	private class Attr{
		private final String attrName;
		
		private final String splitStr;
		
		private final String[] attrValues;
		
		Attr(String attrName,String splitStr,String... attrValues) {
			this.attrName=attrName;
			this.splitStr=splitStr;
			this.attrValues=attrValues;
		}
		
	}
	
	
	public void addAttr(String attrName,String attrValue){
		Attr attr=new Attr(attrName, null,attrValue);
		attrs.add(attr);
	}
	
	public void addAttr(String attrName,String splitStr,String...attrValues ){
		JAssert.isTrue(JStringUtils.isNotNullOrEmpty(splitStr), "split string must not be null(empty).");
		Attr attr=new Attr(attrName, null,attrValues);
		attrs.add(attr);
	}
	
	private List<Attr> attrs=new ArrayList<JAttributeKVFilter.Attr>();
	
	public  JAttributeKVFilter(String attrName,String splitStr,String...attrValues ) {
		Attr attr=new Attr(attrName, splitStr,attrValues);
		attrs.add(attr);
	}
	
	@Override
	public boolean filterElement(Element element) {
		boolean finalMatches=true;
		for (Iterator<Attr> iterator = attrs.iterator(); iterator.hasNext();) {
			Attr attr =  iterator.next();
			Attribute attribute=element.attribute(attr.attrName);
			if(attribute==null) {
				finalMatches=false;
				break;
			}
			String valueText=attribute.getValue();
			String splitStr=attr.splitStr;
			if(splitStr!=null&&!"".equals(splitStr)){
				String[] valueTexts= valueText.trim().split(splitStr);
				if(!JArrays.includeIn(valueTexts, attr.attrValues)){
					finalMatches=false;
					break;
				}
			}
			else{
				if(attr.attrValues.length==0
						|| (!valueText.equals(attr.attrValues[0])) ){
					finalMatches=false;
					break;
				}
			}
		}
		return finalMatches;
	}

}
