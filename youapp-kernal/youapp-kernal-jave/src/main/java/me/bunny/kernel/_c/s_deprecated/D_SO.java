package me.bunny.kernel._c.s_deprecated;

import static me.bunny.kernel._c.s_deprecated.D_U.className;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.EnumSet;

import com.fasterxml.jackson.databind.ser.SerializerFactory;

import me.bunny.kernel._c.json.JJSONConfig;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;


public abstract class D_SO {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(D_SO.class);
	
	private D_SerializerFactory defaultSerializer=new D_JSONSerializerFactory();
	
	private D_ClassResolver classResolver;
	
	private boolean registrationRequired;
	
	private boolean warnUnregisteredClasses;

	private JJSONConfig jsonConfig;
	
	public D_SO(D_ClassResolver classResolver) {
		this.classResolver=classResolver;
	}

	/** Writes an object using the registered serializer. */
	public void writeObject (OutputStream output, Object object) {
		if (output == null) throw new IllegalArgumentException("output cannot be null.");
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		writeObject(output, object, getRegistration(object.getClass()).getSerializer());
	}

	/** Writes an object using the specified serializer. The registered serializer is ignored. */
	public void writeObject (OutputStream output, Object object, D_Serializer serializer) {
		if (output == null) throw new IllegalArgumentException("output cannot be null.");
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		serializer.write(this, output, object);
	}
	
	
	/** Reads an object using the registered serializer. */
	public <T> T readObject (InputStream input, Class<T> type) {
		if (input == null) throw new IllegalArgumentException("input cannot be null.");
		if (type == null) throw new IllegalArgumentException("type cannot be null.");
		return readObject(input, type, getRegistration(type).getSerializer());
	}

	/** Reads an object using the specified serializer. The registered serializer is ignored. */
	public <T> T readObject (InputStream input, Class<T> type, D_Serializer serializer) {
		if (input == null) throw new IllegalArgumentException("input cannot be null.");
		if (type == null) throw new IllegalArgumentException("type cannot be null.");
		if (serializer == null) throw new IllegalArgumentException("serializer cannot be null.");
		return (T) serializer.read(this, input, type);
	}
	
	
	/** If the class is not registered and {@link Kryo#setRegistrationRequired(boolean)} is false, it is automatically registered
	 * using the {@link Kryo#addDefaultSerializer(Class, Class) default serializer}.
	 * @throws IllegalArgumentException if the class is not registered and {@link Kryo#setRegistrationRequired(boolean)} is true.
	 * @see ClassResolver#getRegistration(Class) */
	public D_Registration getRegistration (Class type) {
		if (type == null) throw new IllegalArgumentException("type cannot be null.");

		D_Registration registration = classResolver.getRegistration(type);
		if (registration == null) {
			if (Proxy.isProxyClass(type)) {
				// If a Proxy class, treat it like an InvocationHandler because the concrete class for a proxy is generated.
				registration = getRegistration(InvocationHandler.class);
			} else if (!type.isEnum() && Enum.class.isAssignableFrom(type)) {
				// This handles an enum value that is an inner class. Eg: enum A {b{}};
				registration = getRegistration(type.getEnclosingClass());
			} else if (EnumSet.class.isAssignableFrom(type)) {
				registration = classResolver.getRegistration(EnumSet.class);
			} else if (isClosure(type)) {
				registration = classResolver.getRegistration(D_ClosureSerializer.Closure.class);
			}
			if (registration == null) {
				if (registrationRequired) {
					throw new IllegalArgumentException(unregisteredClassMessage(type));
				}
				if(warnUnregisteredClasses) {
					LOGGER.warn(unregisteredClassMessage(type));
				}
				registration = classResolver.registerImplicit(type);
			}
		}
		return registration;
	}
	
	/** Returns true if the specified type is a closure.
	 * <p>
	 * This can be overridden to support alternative implementations of clousres. Current version supports Oracle's Java8 only */
	protected boolean isClosure(Class type) {
		if (type == null) throw new IllegalArgumentException("type cannot be null.");
		return type.getName().indexOf('/') >= 0;
	}
	
	protected String unregisteredClassMessage (Class type) {
		return "Class is not registered: " + className(type)
			+ "\nNote: To register this class use: kryo.register(" + className(type) + ".class);";
	}
	
	// --- Default serializers ---
	/**
	 * Sets the serializer factory to use when no
	 * {@link #addDefaultSerializer(Class, Class) default serializers} match an
	 * object's type. Default is {@link ReflectionSerializerFactory} with
	 * {@link FieldSerializer}.
	 * 
	 * @see #newDefaultSerializer(Class)
	 */
	public void setDefaultSerializer(D_SerializerFactory serializer) {
		if (serializer == null)
			throw new IllegalArgumentException("serializer cannot be null.");
		defaultSerializer = serializer;
	}

	/**
	 * Sets the serializer to use when no
	 * {@link #addDefaultSerializer(Class, Class) default serializers} match an
	 * object's type. Default is {@link FieldSerializer}.
	 * 
	 * @see #newDefaultSerializer(Class)
	 */
	public void setDefaultSerializer(Class<? extends D_Serializer> serializer) {
		if (serializer == null)
			throw new IllegalArgumentException("serializer cannot be null.");
		defaultSerializer = new D_ReflectionSerializerFactory(serializer);
	}
	
	/** Called by {@link #getDefaultSerializer(Class)} when no default serializers matched the type. Subclasses can override this
	 * method to customize behavior. The default implementation calls {@link SerializerFactory#makeSerializer(Kryo, Class)} using
	 * the {@link #setDefaultSerializer(Class) default serializer}. */
	protected D_Serializer newDefaultSerializer (Class type) {
		return defaultSerializer.makeSerializer(this, type);
	}
	
	public void setJsonConfig(JJSONConfig jsonConfig) {
		this.jsonConfig = jsonConfig;
	}
	
	JJSONConfig getJsonConfig() {
		return jsonConfig;
	}
	
	/** Creates a new instance of a class using {@link Registration#getInstantiator()}. If the registration's instantiator is null,
	 * a new one is set using {@link #newInstantiator(Class)}. */
	public <T> T newInstance (Class<T> type) {
		D_Registration registration = getRegistration(type);
		D_ObjectInstantiator instantiator = registration.getInstantiator();
		if (instantiator == null) {
//			instantiator = newInstantiator(type);
			registration.setInstantiator(instantiator);
		}
		return (T)instantiator.newInstance();
	}
}
