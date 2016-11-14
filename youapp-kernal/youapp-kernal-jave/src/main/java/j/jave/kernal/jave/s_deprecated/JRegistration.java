package j.jave.kernal.jave.s_deprecated;


public class JRegistration {

	private final Class type;
	
	private JSerializer serializer;
	
	private JObjectInstantiator instantiator;

	public JRegistration (Class type, JSerializer serializer) {
		if (type == null) throw new IllegalArgumentException("type cannot be null.");
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		this.type = type;
		this.serializer = serializer;
	}

	public Class getType () {
		return type;
	}

	public JSerializer getSerializer () {
		return serializer;
	}

	public void setSerializer (JSerializer serializer) {
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		this.serializer = serializer;
	}

	/** @return May be null if not yet set. */
	public JObjectInstantiator getInstantiator () {
		return instantiator;
	}

	/** Sets the instantiator that will create a new instance of the type in {@link Kryo#newInstance(Class)}. */
	public void setInstantiator (JObjectInstantiator instantiator) {
		if (instantiator == null) throw new IllegalArgumentException("instantiator cannot be null.");
		this.instantiator = instantiator;
	}

}
