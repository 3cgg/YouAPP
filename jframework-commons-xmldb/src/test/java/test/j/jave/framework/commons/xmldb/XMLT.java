package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.xmldb.ele.JEleObject;
import j.jave.framework.commons.xmldb.utils.JXMLUtils;

import java.io.FileInputStream;
import java.util.List;

import org.dom4j.Element;

public class XMLT {

	public static void main(String[] args) throws Exception {
		
		JEleObject eleOperator=new JEleObject(JXMLUtils.loadDocument(new FileInputStream("D:\\java_\\JFramework1.1\\trunk\\jframework-commons-xmldb\\src\\test\\resources\\html.xml")));
		
		Element element= eleOperator.getEleById("content-wrapper");
		
		List<Element> elements1= eleOperator.getElesByName("description");
		
		List<Element> elements2= eleOperator.getElesByTagName("a");
		
		List<Element> elements3= eleOperator.getElesByClassName("line");
		
		
		
		
		System.out.println("e");
		
	}
}
