package j.jave.kernal.jave.serializer;

public abstract class JSerializerFactory {

	abstract public JSerializer newSerializer(Class<?> clazz);
	
}
