package j.jave.module.crawl.parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class JElementWithSingleTextParser implements JNodeValueParser {

	@Override
	public String parse(Node node) {
		return node.getFirstChild().getTextContent();
	}

	@Override
	public boolean supported(Node node) {
		
		if(Element.class.isInstance(node)){
			Element element=(Element)node;
			int childNum=element.getChildNodes().getLength();
			if(childNum>1){
				return false;
			}
			Node singleNode=element.getFirstChild();
			// must be text
			return Text.class.isInstance(singleNode);
			
		}
		return false;
	}

}
