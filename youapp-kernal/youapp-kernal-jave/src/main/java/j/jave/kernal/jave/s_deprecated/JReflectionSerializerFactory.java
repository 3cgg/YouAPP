package j.jave.kernal.jave.s_deprecated;

import static j.jave.kernal.jave.s_deprecated.U.className;

public class JReflectionSerializerFactory implements JSerializerFactory {

	private final Class<? extends JSerializer> serializerClass;

	public JReflectionSerializerFactory (Class<? extends JSerializer> serializerClass) {
		this.serializerClass = serializerClass;
	}

	@Override
	public JSerializer makeSerializer (JSO jso, Class<?> type) {
		return makeSerializer(jso, serializerClass, type);
	}

	/** Creates a new instance of the specified serializer for serializing the specified class. Serializers must have a zero
	 * argument constructor or one that takes (Kryo), (Class), or (Kryo, Class).
	*/
	public static JSerializer makeSerializer (JSO jso, Class<? extends JSerializer> serializerClass, Class<?> type) {
		try {
			try {
				return serializerClass.getConstructor(JSO.class, Class.class).newInstance(jso, type);
			} catch (NoSuchMethodException ex1) {
				try {
					return serializerClass.getConstructor(JSO.class).newInstance(jso);
				} catch (NoSuchMethodException ex2) {
					try {
						return serializerClass.getConstructor(Class.class).newInstance(type);
					} catch (NoSuchMethodException ex3) {
						return serializerClass.newInstance();
					}
				}
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to create serializer \"" + serializerClass.getName() + "\" for class: " + className(type), ex);
		}

	}
}