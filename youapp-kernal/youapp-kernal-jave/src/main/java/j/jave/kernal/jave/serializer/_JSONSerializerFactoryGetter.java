package j.jave.kernal.jave.serializer;

public abstract class _JSONSerializerFactoryGetter {
	
	private static JJSONSerializerFactory factory=new JJSONSerializerFactory();

	public static JJSONSerializerFactory get(){
		return factory;
	}
	
}
