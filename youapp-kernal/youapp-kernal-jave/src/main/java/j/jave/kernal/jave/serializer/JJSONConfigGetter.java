package j.jave.kernal.jave.serializer;

import j.jave.kernal.jave.json.JJSONConfig;

public abstract class JJSONConfigGetter {

	public JJSONConfig getJSONConfig(Class<?> clazz){
		return new JJSONConfig();
	}
}
