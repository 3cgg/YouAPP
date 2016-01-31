package j.jave.kernal.xml.node;

import java.util.List;


public interface JXPathGetter extends JNodeGetter {

	public List<?> getNodesByXPath(String xpath);
	
}
