package me.bunny.kernel._c.serializer;

public abstract class JSerializerFactory {

	abstract public JSerializer newSerializer(Class<?> clazz);
	
}
