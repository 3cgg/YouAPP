package me.bunny.kernel.jave.serializer;

public abstract class JSerializerFactory {

	abstract public JSerializer newSerializer(Class<?> clazz);
	
}
