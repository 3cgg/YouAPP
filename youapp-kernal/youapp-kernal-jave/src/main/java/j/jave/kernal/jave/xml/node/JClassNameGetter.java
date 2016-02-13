package j.jave.kernal.jave.xml.node;

import java.util.List;

public interface JClassNameGetter extends JNodeGetter {

	public List<?> getNodesByClassName(String... className);
	
}
