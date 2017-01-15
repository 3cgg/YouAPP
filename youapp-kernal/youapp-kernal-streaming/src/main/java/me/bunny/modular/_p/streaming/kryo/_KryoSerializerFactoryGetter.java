package me.bunny.modular._p.streaming.kryo;

public abstract class _KryoSerializerFactoryGetter {
	
	private static KryoSerializerFactory factory=new KryoSerializerFactory();

	public static KryoSerializerFactory get(){
		return factory;
	}
	
}
