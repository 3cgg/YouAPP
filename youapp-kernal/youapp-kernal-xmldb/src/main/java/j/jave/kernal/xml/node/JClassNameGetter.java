package j.jave.kernal.xml.node;

import java.util.List;

public interface JClassNameGetter extends JNodeGetter {

	public List<?> getNodesByClassName(String... className);
	
}
