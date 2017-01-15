package j.jave.platform.sps.multiv.jnterface;

import j.jave.platform.sps.multiv.ComponentMetaNames;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport.ComponentProperties;
import me.bunny.kernel.jave.exception.JInitializationException;
import me.bunny.kernel.jave.io.JInputStreamWrapperSource;
import me.bunny.kernel.jave.utils.JPropertiesUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Any unique version.
 * @author J
 *
 */
public class JKey {

	private String version;
	
	private String component;
	
	private String app;
	
	public final String unique(){
		return ComponentVersionSpringApplicationSupport.unique(app, component, version);
	}
	
	public static final String unique(String app,String component,String version){
		return ComponentVersionSpringApplicationSupport.unique(app, component, version);
	}
	
	public static JKey parse(String unique){
		JKey key=new JKey();
		String[] parts=unique.split(":");
		key.app=parts[0];
		key.component=parts[1];
		key.version=parts[2];
		return key;
	}
	
	public JKey(){
	}
	// get all info from component-version.properties.
	@SuppressWarnings("unused")
	@Deprecated
	private void findComponentInfo() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ComponentProperties.PROPERTY_LOCATION);
		try {
			JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(inputStream);
			Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
			app = JPropertiesUtils.getKey(ComponentMetaNames.APP_NAME,
					properties);
			component = JPropertiesUtils.getKey(
					ComponentMetaNames.COMPONENT_NAME, properties);
			version = JPropertiesUtils.getKey(
					ComponentMetaNames.COMPONENT_VERSION, properties);
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
	}
	
	public String getVersion() {
		return version;
	}

	public String getComponent() {
		return component;
	}

	public String getApp() {
		return app;
	}
	
	
}
