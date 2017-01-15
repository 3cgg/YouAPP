package me.bunny.kernel.jave.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public abstract class SerializerUtils {

	public static byte[] serialize(JSerializerFactory serializerFactory, Object object) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		serializerFactory.newSerializer(object.getClass()).write(outputStream, object);
		return outputStream.toByteArray();
	}

	public static <T> T deserialize(JSerializerFactory serializerFactory, byte[] bytes, Class<T> clazz) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		return serializerFactory.newSerializer(clazz).read(inputStream, clazz);
	}
	
	/**
	 * 
	 * @param serializerFactory
	 * @param bytes
	 * @param clazz target class
	 * @param innerClass need if the target clazz is collection
	 * @return
	 */
	public static <T> T deserialize(JSerializerFactory serializerFactory, byte[] bytes, Class<T> clazz,Class<?> innerClass) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		return serializerFactory.newSerializer(innerClass).read(inputStream, clazz);
	}
	
}
