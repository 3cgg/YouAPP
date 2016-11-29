package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.serializer.JJSONSerializerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;

public class _SerializeFactoryGetter {

	private static JSerializerFactory serializerFactory=new JJSONSerializerFactory();
	
	public static JSerializerFactory get(){
		return serializerFactory;
	}
	
}
