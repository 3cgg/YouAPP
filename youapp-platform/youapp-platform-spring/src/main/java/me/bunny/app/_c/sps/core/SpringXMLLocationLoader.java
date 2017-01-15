package me.bunny.app._c.sps.core;

import java.util.List;

public interface SpringXMLLocationLoader {

	public List<String> getSpringXmlLocation();
	
	public List<String> getSpringXmlLocation(ClassLoader classLoader);
}
