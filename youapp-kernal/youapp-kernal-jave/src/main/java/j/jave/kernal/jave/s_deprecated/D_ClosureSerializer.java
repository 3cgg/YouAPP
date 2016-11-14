package j.jave.kernal.jave.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class D_ClosureSerializer extends D_Serializer {

	/** Marker class to bind ClosureSerializer to. See also {@link Kryo#isClosure(Class)} and {@link Kryo#getRegistration(Class)} */
	public static class Closure {
	}

	private static Method readResolve;
	private static Class serializedLambda = java.lang.invoke.SerializedLambda.class;
	static {
		try {
			readResolve = serializedLambda.getDeclaredMethod("readResolve");
			readResolve.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException("Could not obtain SerializedLambda or its methods via reflection", e);
		}
	}
	
	
	
	@Override
	public void write(D_SO jso, OutputStream output, Object object) {
		try {
			Class type = object.getClass();
			Method writeReplace = type.getDeclaredMethod("writeReplace");
			writeReplace.setAccessible(true);
			Object replacement = writeReplace.invoke(object);
			if (serializedLambda.isInstance(replacement)) {
				// Serialize the representation of this lambda
				jso.writeObject(output, replacement);
			} else
				throw new RuntimeException("Could not serialize lambda");
		} catch (Exception e) {
			throw new RuntimeException("Could not serialize lambda", e);
		}
	}

	@Override
	public Object read(D_SO jso, InputStream input, Class type) {
		try {
			Object object = jso.readObject(input, serializedLambda);
			return readResolve.invoke(object);
		} catch (Exception e) {
			throw new RuntimeException("Could not serialize lambda", e);
		}
	}

	public Object copy (D_SO jso, Object original) {
		try {
			Class type = original.getClass();
			Method writeReplace = type.getDeclaredMethod("writeReplace");
			writeReplace.setAccessible(true);
			Object replacement = writeReplace.invoke(original);
			if (serializedLambda.isInstance(replacement)) {
				return readResolve.invoke(replacement);
			} else
				throw new RuntimeException("Could not serialize lambda");
		} catch (Exception e) {
			throw new RuntimeException("Could not serialize lambda", e);
		}
	}
	
}
