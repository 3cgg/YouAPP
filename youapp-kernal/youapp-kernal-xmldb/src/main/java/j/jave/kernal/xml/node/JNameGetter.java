package j.jave.kernal.xml.node;

import java.util.List;

public interface JNameGetter extends JNodeGetter {
	
	public List<?> getNodesByName(String name);

}
