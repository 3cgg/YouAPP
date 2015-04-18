package j.jave.framework.model.support.interceptor;

import j.jave.framework.model.support.JTable;

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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T proceed() {
		for(int i=0;i<modelInterceptors.size();i++){
			modelInterceptors.get(i).intercept(this);
		}
		return object;
	}
	
}
