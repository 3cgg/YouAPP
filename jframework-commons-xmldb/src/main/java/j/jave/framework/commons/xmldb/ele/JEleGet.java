package j.jave.framework.commons.xmldb.ele;

import java.util.List;

import org.dom4j.Element;

public interface JEleGet {
	
	public  Element getEleById(String id);
	
	public  Element getEleByUppercaseID(String ID);
	
	public  List<Element> getElesByName(String name);
	
	public  List<Element> getElesByTagName(String tagName);
	
	public  List<Element> getElesByClassName(String... cssClassNames );
	
}
