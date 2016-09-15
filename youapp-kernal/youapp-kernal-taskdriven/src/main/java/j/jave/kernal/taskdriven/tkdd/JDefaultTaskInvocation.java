package j.jave.kernal.taskdriven.tkdd;

import j.jave.kernal.taskdriven.tkdd.interceptor.JTaskAuthorizeInterceptor;
import j.jave.kernal.taskdriven.tkdd.interceptor.JTaskContextProcessorInterceptor;
import j.jave.kernal.taskdriven.tkdd.interceptor.JTaskExecutingInterceptor;
import j.jave.kernal.taskdriven.tkdd.interceptor.JTaskStatusInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author J
 */
public class JDefaultTaskInvocation implements JTaskInvocation{

	private List<JTaskInterceptor<JTask>> taskInterceptors=new ArrayList<JTaskInterceptor<JTask>>(8);
	
	/**
	 * the entity that maps to a concrete one. 
	 */
	private final JTask task;
	
	private JTaskContext taskContext;
	
	public  JDefaultTaskInvocation(JTask task,JTaskContext taskContext) {
		this.task=task;
		this.taskContext=taskContext;
		init();
	}
	
	private void init(){
		taskInterceptors.add(new JTaskContextProcessorInterceptor());
		taskInterceptors.add(new JTaskStatusInterceptor());
		taskInterceptors.add(new JTaskAuthorizeInterceptor());
		taskInterceptors.add(new JTaskExecutingInterceptor());
	}
	
	public void addTaskInterceptor(JTaskInterceptor<JTask> taskInterceptor){
		taskInterceptors.add(taskInterceptor);
	}
	
	private int currentInterceptorIndex = -1;
	
	@Override
	public Object proceed() {
		
		if (this.currentInterceptorIndex == this.taskInterceptors.size() - 1) {
			return task;
		}
		JTaskInterceptor<JTask> interceptor =
				this.taskInterceptors.get(++this.currentInterceptorIndex);
		return interceptor.interceptor(this);
	}
	
	
	public JTaskContext getTaskContext() {
		return taskContext;
	}
	
	public JTask getTask(){
		return task;
	}
}

