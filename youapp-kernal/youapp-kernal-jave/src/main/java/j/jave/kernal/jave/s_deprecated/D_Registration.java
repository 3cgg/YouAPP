package j.jave.kernal.jave.s_deprecated;


public class D_Registration {

	private final Class type;
	
	private D_Serializer serializer;
	
	private D_ObjectInstantiator instantiator;

	public D_Registration (Class type, D_Serializer serializer) {
		if (type == null) throw new IllegalArgumentException("type cannot be null.");
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		this.type = type;
		this.serializer = serializer;
	}

	public Class getType () {
		return type;
	}

	public D_Serializer getSerializer () {
		return serializer;
	}

	public void setSerializer (D_Serializer serializer) {
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		this.serializer = serializer;
	}

	/** @return May be null if not yet set. */
	public D_ObjectInstantiator getInstantiator () {
		return instantiator;
	}

	/** Sets the instantiator that will create a new instance of the type in {@link Kryo#newInstance(Class)}. */
	public void setInstantiator (D_ObjectInstantiator instantiator) {
		if (instantiator == null) throw new IllegalArgumentException("instantiator cannot be null.");
		this.instantiator = instantiator;
	}

}
