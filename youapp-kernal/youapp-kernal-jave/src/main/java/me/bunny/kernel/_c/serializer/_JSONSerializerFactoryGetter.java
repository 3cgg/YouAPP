package me.bunny.kernel._c.serializer;

public abstract class _JSONSerializerFactoryGetter {
	
	private static JJSONSerializerFactory factory=new JJSONSerializerFactory();

	public static JJSONSerializerFactory get(){
		return factory;
	}
	
}
