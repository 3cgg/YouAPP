package j.jave.kernal.taskdriven.tkdd.flow;

import j.jave.kernal.taskdriven.tkdd.JTask;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataSpecInit;
import me.bunny.kernel.jave.support.validate.JSelfValidator;

import java.util.HashMap;

/**
 * global flow context
 * @author J
 *
 */
public class JFlowContext extends HashMap<Object, Object> implements JSelfValidator {

	public static final ThreadLocal<JFlowContext> flowContexts=new ThreadLocal<JFlowContext>();
	
	private int currentTaskIndex=-1;
		
	private Class<? extends JTask> current;
	
	public void setCurrentTaskIndex(int currentTaskIndex) {
		this.currentTaskIndex = currentTaskIndex;
	}

	public int getCurrentTaskIndex() {
		return currentTaskIndex;
	}
	public Class<? extends JTask> getCurrent() {
		return current;
	}
	
	/**
	 * set current running task.
	 * @param current
	 */
	public void setCurrent(Class<? extends JTask> current) {
		this.current = current;
	}
	
	public static JFlowContext get(){
		return flowContexts.get();
	}
	
	static void set(JFlowContext flowContext){
		flowContexts.set(flowContext);
	}
	
	@Override
	public boolean validate() {
		return false;
	}
	
	@Override
	public Object clone() {
		JFlowContext flowContext= (JFlowContext) super.clone();
		return flowContext;
	}

	public void setCustomTaskMetadataSpec(Class<? extends JTask> taskClass,JTaskMetadataSpecInit taskMetadataSpecInit ){
		put(taskClass, taskMetadataSpecInit);
	}
	
	public JTaskMetadataSpecInit getCustomTaskMetadataSpec(Class<? extends JTask> taskClass){
		return (JTaskMetadataSpecInit) get(taskClass);
	}
}
