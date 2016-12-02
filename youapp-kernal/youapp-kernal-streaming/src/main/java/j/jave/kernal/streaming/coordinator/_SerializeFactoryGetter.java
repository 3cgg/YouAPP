package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;

public class _SerializeFactoryGetter {

	private static JSerializerFactory serializerFactory=new KryoSerializerFactory();
	
	public static JSerializerFactory get(){
		return serializerFactory;
	}
	
}
