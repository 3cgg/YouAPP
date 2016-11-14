package j.jave.kernal.jave.s_deprecated;

import java.lang.reflect.Constructor;

public class D_ConstructorInstantiator <T> implements D_ObjectInstantiator<T> {

	   protected Constructor<T> constructor;

	   public D_ConstructorInstantiator(Class<T> type) {
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
