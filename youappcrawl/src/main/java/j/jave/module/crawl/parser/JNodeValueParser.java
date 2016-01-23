package j.jave.module.crawl.parser;

import org.w3c.dom.Node;

public interface JNodeValueParser {

	String parse(Node node);
	
	boolean supported(Node node);
	
}
