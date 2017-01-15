package me.bunny.kernel._c.serializer;

import me.bunny.kernel._c.json.JJSONConfig;

public abstract class JJSONConfigGetter {

	public JJSONConfig getJSONConfig(Class<?> clazz){
		return new JJSONConfig();
	}
}
