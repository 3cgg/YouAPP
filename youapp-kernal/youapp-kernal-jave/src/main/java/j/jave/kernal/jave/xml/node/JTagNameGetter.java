package j.jave.kernal.jave.xml.node;

import java.util.List;

public interface JTagNameGetter extends JNodeGetter {

	public List<?> getNodesByTagName(String tagName);

}
