package j.jave.module.crawl.parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class JElementWithMultiTextParser implements JNodeValueParser {

	@Override
	public String parse(Node node) {
		return node.getTextContent();	
	}

	@Override
	public boolean supported(Node node) {
		
		if(Element.class.isInstance(node)){
			Element element=(Element)node;
			int childNum=element.getChildNodes().getLength();
			if(childNum>1){
				return true;
			}
		}
		return false;
	}

}
