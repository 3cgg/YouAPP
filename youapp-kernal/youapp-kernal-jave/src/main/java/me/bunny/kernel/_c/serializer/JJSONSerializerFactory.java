package me.bunny.kernel._c.serializer;

import me.bunny.kernel._c.json.JJSONConfig;

public class JJSONSerializerFactory extends JSerializerFactory {

	private JJSONConfigGetter configGetter=new JJSONConfigGetter() {
	};
	
	public void setConfigGetter(JJSONConfigGetter configGetter) {
		this.configGetter = configGetter;
	}
	
	@Override
	public JSerializer newSerializer(Class<?> clazz) {
		JJSONConfig config=configGetter.getJSONConfig(clazz);
		JJSONSerializer serializer=new JJSONSerializer(config,clazz);
		return serializer;
	}

}
