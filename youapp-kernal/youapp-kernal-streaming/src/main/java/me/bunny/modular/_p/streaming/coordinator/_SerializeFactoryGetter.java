package me.bunny.modular._p.streaming.coordinator;

import me.bunny.kernel._c.serializer.JSerializerFactory;
import me.bunny.modular._p.streaming.kryo.KryoSerializerFactory;

public class _SerializeFactoryGetter {

	private static JSerializerFactory serializerFactory=new KryoSerializerFactory();
	
	public static JSerializerFactory get(){
		return serializerFactory;
	}
	
}
