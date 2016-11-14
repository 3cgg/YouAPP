package j.jave.kernal.jave.s_deprecated;

public interface JSerializerFactory {

	 /**
     * Creates a new serializer
     * @param kryo The serializer instance requesting the new serializer.
     * @param type The type of the object that is to be serialized.
     * @return An implementation of a serializer that is able to serialize an object of type {@code type}.
     */
	JSerializer makeSerializer (JSO jso, Class<?> type);
}
