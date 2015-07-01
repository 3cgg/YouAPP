package j.jave.framework.tkdd;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public class JDefaultTaskInvocation<T extends JTask> implements JTaskInvocation<T>{

	private List<JTaskInterceptor<T>> taskInterceptors=new ArrayList<JTaskInterceptor<T>>(8);
	
	/**
	 * the entity that maps to a concrete one. 
	 * {@link JTable} 
	 */
	private final T object;
	
	private JTaskContext context;
	
	public  JDefaultTaskInvocation(T object,JTaskContext context) {
		this.object=object;
		this.context=context;
		init();
	}
	
	private void init(){
		taskInterceptors.add(new JTaskContextProcessorInterceptor<T>(object,context));
		taskInterceptors.add(new JTaskStatusInterceptor<T>(object,context));
	}
	
	public void addTaskInterceptor(JTaskInterceptor<T> taskInterceptor){
		taskInterceptors.add(taskInterceptor);
	}
	
	private int currentInterceptorIndex = -1;
	
	@Override
	public T proceed() {
		
		if (this.currentInterceptorIndex == this.taskInterceptors.size() - 1) {
			return object;
		}
		JTaskInterceptor<T> interceptor =
				this.taskInterceptors.get(++this.currentInterceptorIndex);
		return interceptor.interceptor(this);
	}
	
}

