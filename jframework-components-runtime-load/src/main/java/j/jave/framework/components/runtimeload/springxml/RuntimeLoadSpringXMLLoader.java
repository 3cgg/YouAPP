package j.jave.framework.components.runtimeload.springxml;

import j.jave.framework.components.core.SpringXMLLocationLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RuntimeLoadSpringXMLLoader implements SpringXMLLocationLoader{

	public List<String> getSpringXmlLocation(){
		return getSpringXmlLocation(this.getClass().getClassLoader());
	}
	
	@Override
	public List<String> getSpringXmlLocation(ClassLoader classLoader) {
		String baseDir="j/jave/framework/components/runtimeload/springxml/";
		List<String> resources=new ArrayList<String>();
		URL url= classLoader.getResource(baseDir+"spring-context.xml");
		if(url!=null){
			resources.add(url.toString());
		}
		return resources;
	}
	
	
	public static void main(String[] args) {
		RuntimeLoadSpringXMLLoader springXMLLoader=new RuntimeLoadSpringXMLLoader();
		List<String> resources=springXMLLoader.getSpringXmlLocation();
		System.out.println(resources.size());
		
	}
	
}
