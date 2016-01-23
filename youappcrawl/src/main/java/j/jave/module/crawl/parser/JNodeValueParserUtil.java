package j.jave.module.crawl.parser;

import j.jave.module.crawl.def.JWebConstants;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class JNodeValueParserUtil {
	
	public static String parser(Node node,String... parsers){
		
		JNodeValueParser nodeValueParser=null;
		
		if(Text.class.isInstance(node)){
			// AS text
			nodeValueParser=new JTextNodeParser();
			return nodeValueParser.parse(node);
		}
		else {
			nodeValueParser=new JElementWithSingleTextParser();
			if(nodeValueParser.supported(node)){
				return nodeValueParser.parse(node);
			}
		}
		
		//others , ask for special parser
		
		if(parsers.length>0){
			for (int i = 0; i < parsers.length; i++) {
				try{
					String className=parsers[i];
					if(JWebConstants.EMPTY.equals(className)){
						continue;
					}
					nodeValueParser=(JNodeValueParser) Class.forName(className).newInstance();
					if(nodeValueParser.supported(node)){
						return nodeValueParser.parse(node);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		else{
			//default get all text in the node
			nodeValueParser=new JElementWithMultiTextParser();
			if(nodeValueParser.supported(node)){
				return nodeValueParser.parse(node);
			}
		}
		
		return null;
		
	}

}
