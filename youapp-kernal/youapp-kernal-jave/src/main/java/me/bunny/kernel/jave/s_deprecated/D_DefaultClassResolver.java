package me.bunny.kernel.jave.s_deprecated;

import static me.bunny.kernel.jave.s_deprecated.D_U.getWrapperClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class D_DefaultClassResolver implements D_ClassResolver {

	private D_SO jso;
	
	protected final Map<Class, D_Registration> classToRegistration = new HashMap<>();
	
	@Override
	public void setJSO(D_SO jso) {
		this.jso=jso;
	}

	@Override
	public D_Registration register(D_Registration registration) {
		if (registration == null) throw new IllegalArgumentException("registration cannot be null.");
		classToRegistration.put(registration.getType(), registration);
		if (registration.getType().isPrimitive()) 
			classToRegistration.put(getWrapperClass(registration.getType()), registration);
		return registration;
	}

	@Override
	public D_Registration registerImplicit(Class type) {
		return register(new D_Registration(type, jso.newDefaultSerializer(type)));
	}

	@Override
	public D_Registration getRegistration(Class type) {
		Class wrapperClass=type;
		if (type.isPrimitive()) {
			wrapperClass=getWrapperClass(type);
		}
		return classToRegistration.get(wrapperClass);
	}

	@Override
	public D_Registration writeClass(OutputStream output, Class type) {
		return null;
	}

	@Override
	public D_Registration readClass(InputStream input) {
		return null;
	}

	@Override
	public void reset() {
		
	}

}
