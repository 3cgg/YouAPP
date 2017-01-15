package me.bunny.kernel.taskdriven.tkdd;

import me.bunny.kernel._c.support.validate.JSelfValidator;
import me.bunny.kernel.taskdriven.tkdd.flow.JFlowContext;

import java.util.HashMap;

import javax.security.auth.Subject;

public class JTaskContext  extends HashMap<Object, Object> implements JSelfValidator{
	
	/**
	 * the linked task start element.
	 */
//	private Class<? extends JTask> root;
	
	/**
	 * set when the task is executing under the flow progress . 
	 */
	private JFlowContext flowContext;
	
	public JFlowContext getFlowContext() {
		return flowContext;
	}
	
//	public JTaskContext(Class<? extends JTask> root) {
//		this.root=root;
//	}
	
	public JTaskContext(JFlowContext flowContext){
		//initialize flow context
		this.flowContext=flowContext;
	}
	
	public JTaskContext(){
		//initialize flow context
		this.flowContext=JFlowContext.get();
	}

//	public Class<? extends JTask> getRoot() {
//		return root;
//	}
//	
//	public void setRoot(Class<? extends JTask> root) {
//		this.root = root;
//	}

	@Override
	public boolean validate() {
//		if(getRoot()==null){
//			throw new JTaskExecutionException("the root task of context is null or empty. please set.");
//		}
		return false;
	}
	
	
	@Override
	public Object get(Object key) {
		Object object=  null;
		if(containsKey(key)){
			object=  super.get(key);
		}
		else{
			//get from flow context
			object=flowContext.get(key);
		}
		return object;
	}
	
	
	/**
	 * get value from the map, including type convertion, predefined default value returned if no existing.
	 * @param key
	 * @param defaultValue
	 * @param clazz
	 * @return
	 */
	public <T> T get(Object key, Object defaultValue , Class<T> clazz) {
		Object object=  get(key, defaultValue);
		return object==null?clazz.cast(defaultValue):clazz.cast(object);
	}
	
	/**
	 * get value from the map, predefined default value returned if no existing.
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object get(Object key, Object defaultValue) {
		Object object=  get(key);
		return object==null?defaultValue:object;
	}
	
	public Subject getSubject(Class<? extends JTask> taskClazz){
		JTaskCustomConfig customConfig= (JTaskCustomConfig) get(taskClazz);
		Subject subject=null;
		if(customConfig!=null){
			subject= customConfig.getSubject();
		}
		if(subject==null){
			// get global
			subject=getSubject();
		}
		return subject;
	}
	
	public Subject getSubject(){
		return (Subject) get(JTKDDConstants.GLOBAL_SUBJECT);
	}
	
	
	
}
