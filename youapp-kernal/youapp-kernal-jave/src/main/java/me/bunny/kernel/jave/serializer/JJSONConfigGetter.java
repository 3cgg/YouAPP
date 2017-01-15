package me.bunny.kernel.jave.serializer;

import me.bunny.kernel.jave.json.JJSONConfig;

public abstract class JJSONConfigGetter {

	public JJSONConfig getJSONConfig(Class<?> clazz){
		return new JJSONConfig();
	}
}
