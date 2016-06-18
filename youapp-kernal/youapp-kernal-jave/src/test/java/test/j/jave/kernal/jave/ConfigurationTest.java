package test.j.jave.kernal.jave;

import j.jave.kernal.JConfigMeta;
import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.json.JJSON;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.junit.Test;

public class ConfigurationTest extends TestCase {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		JConfiguration configuration= JConfiguration.get();
		System.out.println(configuration.getString("youapp.atomic.resource.session.provider", "no-exists"));;
		System.out.println(configuration.getString("youapp.xmldb.url", "no-exists"));;
		System.out.println("end");
	}
	
	@Test
	public void testConfigMeta(){
		JConfiguration configuration=JConfiguration.get();
		
		Map<String, JConfigMeta> metas=configuration.getAllConfigMetas();
		
		for (Iterator<Entry<String, JConfigMeta>> iterator = metas.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, JConfigMeta> type = iterator.next();
			
			JConfigMeta configMeta= configuration.getConfigMetas(type.getKey());
			System.out.println(JJSON.get().formatObject(configMeta));;
		}
		
		String configMetaString=configuration.getAllConfigMetaJSON();
		
		System.out.println(configMetaString);
		
	}
	
	
	
	
	
	
	
	

}
