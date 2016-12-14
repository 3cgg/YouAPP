package j.jave.kernal.streaming.coordinator.services.taskrepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.zookeeper.CreateMode;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.Task;
import j.jave.kernal.streaming.coordinator._SerializeFactoryGetter;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class ZKTaskRepo implements TaskRepo {
	
	/**
	 * KEY :  WORKFLOW NAME
	 */
	private Map<String, Queue<Task>> tasks=Maps.newConcurrentMap();
	
	/**
	 * KEY :  ID
	 */
	private Map<String, Task> temp=Maps.newConcurrentMap();
	
	private final ZookeeperExecutor executor;
	
	private final String path;
	
	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	public ZKTaskRepo(ZookeeperExecutor executor,String path) {
		this.executor=executor;
		this.path = path;
		_init();
	}
	
	private void _init(){
		if(executor.exists(path)){
			List<String> paths=executor.getChildren(path);
			for (String ph : paths) {
				String taskPath=path+"/"+ph;
				byte[] bytes=executor.getPath(taskPath);
				if(bytes!=null&&bytes.length>0){
					Task task=SerializerUtils.deserialize(serializerFactory, bytes, Task.class);
					task.setZkNode(taskPath);
					_addTask(task);
				}
			}
		}
	}
	
	private String _addTask(Task task){
		Queue<Task> ts= tasks.get(task.getWorkflowName());
		if(ts==null){
			ts=new LinkedBlockingQueue<>();
			tasks.put(task.getWorkflowName(), ts);
		}
		ts.offer(task);
		temp.put(task.getId(), task);
		return task.getId();
	}

	@Override
	public synchronized String addTask(Task task) {
		if(JStringUtils.isNullOrEmpty(task.getId())){
			task.setId(JUniqueUtils.sequence());
		}
		if(JStringUtils.isNullOrEmpty(task.getZkNode())){
			String zkPath=executor.createPath(path+"/tk-"
					, SerializerUtils.serialize(serializerFactory, task)
					,CreateMode.PERSISTENT_SEQUENTIAL);
			task.setZkNode(zkPath);
		}
		return _addTask(task);
	}

	@Override
	public synchronized Task getTaskByWorfklowName(String workflowName) {
		Queue<Task> ts= tasks.get(workflowName);
		if(ts==null) return null;
		Task task=ts.poll();
		if(task!=null){
			temp.remove(task.getId());
			executor.deletePath(task.getZkNode());
		}
		return task;
	}

	@Override
	public Task peek(String taskId) {
		return temp.get(taskId);
	}
	
	public Collection<Task> getAllTasks(){
		return temp.values();
	}
	
	public Task getTask(String unique){
		return temp.get(unique);
	}
}
