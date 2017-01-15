package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
import me.bunny.kernel.jave.serializer.JSerializerFactory;

public class _SerializeFactoryGetter {

	private static JSerializerFactory serializerFactory=new KryoSerializerFactory();
	
	public static JSerializerFactory get(){
		return serializerFactory;
	}
	
}
