package j.jave.kernal.jave.model.support.interceptor;

import j.jave.kernal.jave.model.support.JTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public class JDefaultModelInvocation<T> implements JModelInvocation<T>{

	private List<JModelIntercepter<T>> modelInterceptors=new ArrayList<JModelIntercepter<T>>(8);
	
	/**
	 * the entity that maps to a concrete table, with annotaion 
	 * {@link JTable} 
	 */
	private final T object;
	
	public JDefaultModelInvocation(T object) {
		this.object=object;
		init();
	}
	
	private synchronized void init(){
		modelInterceptors.add(new JModelValidatorIntercepter<T>(object));
	}
	
	private int currentInterceptorIndex = -1;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T proceed() {
		
		if (this.currentInterceptorIndex == this.modelInterceptors.size() - 1) {
			return object;
		}
		JModelIntercepter<T> interceptor =
				this.modelInterceptors.get(++this.currentInterceptorIndex);
		return interceptor.intercept(this);
	}
	
}
