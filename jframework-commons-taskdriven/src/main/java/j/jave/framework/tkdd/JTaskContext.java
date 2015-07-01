package j.jave.framework.tkdd;

import j.jave.framework.commons.support.validate.JSelfValidator;

import java.util.HashMap;

public class JTaskContext  extends HashMap<Object, Object> implements JSelfValidator{
	
	//"0" is the root task.
	private int currentTaskIndex=-1;
	
	private Class<? extends JTask> current;
	
	/**
	 * the linked task start element.
	 */
	private Class<? extends JTask> root;
	
	public JTaskContext(Class<? extends JTask> root) {
		this.root=root;
	}
	
	public JTaskContext(){}

	public Class<? extends JTask> getRoot() {
		return root;
	}
	
	public void setCurrentTaskIndex(int currentTaskIndex) {
		this.currentTaskIndex = currentTaskIndex;
	}
	/**
	 * "0" is the root task.
	 * @return
	 */
	public int getCurrentTaskIndex() {
		return currentTaskIndex;
	}
	public Class<? extends JTask> getCurrent() {
		return current;
	}
	
	boolean isRootTask(){
		return current==root&&currentTaskIndex==0;
	}
	
	/**
	 * set current running task.
	 * @param current
	 */
	public void setCurrent(Class<? extends JTask> current) {
		this.current = current;
	}

	@Override
	public boolean validate() {
		if(getRoot()==null){
			throw new JTaskExecutionException("the root task of context is null or empty. please set.");
		}
		return false;
	}
	
	
	public void setCustomTaskMetadataSpec(Class<? extends JTask> taskClass,Class<? extends JTaskMetadataSpecInit> taskMetadataSpecInitClass ){
		put(taskClass, taskMetadataSpecInitClass);
	}
	
	
	@SuppressWarnings("unchecked")
	public Class<? extends JTaskMetadataSpecInit> getCustomTaskMetadataSpec(Class<? extends JTask> taskClass){
		return (Class<? extends JTaskMetadataSpecInit>) get(taskClass);
	}
	
	
	
	
}
