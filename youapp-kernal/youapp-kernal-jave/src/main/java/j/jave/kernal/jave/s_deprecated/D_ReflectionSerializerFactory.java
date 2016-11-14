package j.jave.kernal.jave.s_deprecated;

import static j.jave.kernal.jave.s_deprecated.D_U.className;

public class D_ReflectionSerializerFactory implements D_SerializerFactory {

	private final Class<? extends D_Serializer> serializerClass;

	public D_ReflectionSerializerFactory (Class<? extends D_Serializer> serializerClass) {
		this.serializerClass = serializerClass;
	}

	@Override
	public D_Serializer makeSerializer (D_SO jso, Class<?> type) {
		return makeSerializer(jso, serializerClass, type);
	}

	/** Creates a new instance of the specified serializer for serializing the specified class. Serializers must have a zero
	 * argument constructor or one that takes (Kryo), (Class), or (Kryo, Class).
	*/
	public static D_Serializer makeSerializer (D_SO jso, Class<? extends D_Serializer> serializerClass, Class<?> type) {
		try {
			try {
				return serializerClass.getConstructor(D_SO.class, Class.class).newInstance(jso, type);
			} catch (NoSuchMethodException ex1) {
				try {
					return serializerClass.getConstructor(D_SO.class).newInstance(jso);
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