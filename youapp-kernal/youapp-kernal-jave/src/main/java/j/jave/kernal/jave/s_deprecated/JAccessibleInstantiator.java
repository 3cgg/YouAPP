package j.jave.kernal.jave.s_deprecated;

public class JAccessibleInstantiator<T> extends JConstructorInstantiator<T> {

	public JAccessibleInstantiator(Class<T> type) {
	      super(type);
	      if(constructor != null) {
	         constructor.setAccessible(true);
	      }
	   }
	
}
