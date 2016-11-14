package j.jave.kernal.jave.s_deprecated;

import static j.jave.kernal.jave.s_deprecated.U.getWrapperClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class JDefaultClassResolver implements JClassResolver {

	private JSO jso;
	
	protected final Map<Class, JRegistration> classToRegistration = new HashMap<>();
	
	@Override
	public void setJSO(JSO jso) {
		this.jso=jso;
	}

	@Override
	public JRegistration register(JRegistration registration) {
		if (registration == null) throw new IllegalArgumentException("registration cannot be null.");
		classToRegistration.put(registration.getType(), registration);
		if (registration.getType().isPrimitive()) 
			classToRegistration.put(getWrapperClass(registration.getType()), registration);
		return registration;
	}

	@Override
	public JRegistration registerImplicit(Class type) {
		return register(new JRegistration(type, jso.newDefaultSerializer(type)));
	}

	@Override
	public JRegistration getRegistration(Class type) {
		Class wrapperClass=type;
		if (type.isPrimitive()) {
			wrapperClass=getWrapperClass(type);
		}
		return classToRegistration.get(wrapperClass);
	}

	@Override
	public JRegistration writeClass(OutputStream output, Class type) {
		return null;
	}

	@Override
	public JRegistration readClass(InputStream input) {
		return null;
	}

	@Override
	public void reset() {
		
	}

}
