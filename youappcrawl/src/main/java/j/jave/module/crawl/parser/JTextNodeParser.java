package j.jave.module.crawl.parser;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class JTextNodeParser implements JNodeValueParser {

	@Override
	public String parse(Node node) {
		return node.getTextContent();
	}

	@Override
	public boolean supported(Node node) {
		return Text.class.isInstance(node);
	}

}
