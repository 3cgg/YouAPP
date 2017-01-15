package me.bunny.kernel.jave.s_deprecated;

public interface D_SerializerFactory {

	 /**
     * Creates a new serializer
     * @param kryo The serializer instance requesting the new serializer.
     * @param type The type of the object that is to be serialized.
     * @return An implementation of a serializer that is able to serialize an object of type {@code type}.
     */
	D_Serializer makeSerializer (D_SO jso, Class<?> type);
}
