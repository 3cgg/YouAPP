package j.jave.framework.components.core;

import java.util.List;

public interface SpringXMLLocationLoader {

	public List<String> getSpringXmlLocation();
	
	public List<String> getSpringXmlLocation(ClassLoader classLoader);
}
