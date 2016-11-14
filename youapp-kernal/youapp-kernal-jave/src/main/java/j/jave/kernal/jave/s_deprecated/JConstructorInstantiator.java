package j.jave.kernal.jave.s_deprecated;

import java.lang.reflect.Constructor;

public class JConstructorInstantiator <T> implements JObjectInstantiator<T> {

	   protected Constructor<T> constructor;

	   public JConstructorInstantiator(Class<T> type) {
	      try {
	         constructor = type.getDeclaredConstructor((Class[]) null);
	      }
	      catch(Exception e) {
	         throw new RuntimeException(e);
	      }
	   }

	   public T newInstance() {
	      try {
	         return constructor.newInstance((Object[]) null);
	      }
	      catch(Exception e) {
	          throw new RuntimeException(e);
	      }
	   }

	}
