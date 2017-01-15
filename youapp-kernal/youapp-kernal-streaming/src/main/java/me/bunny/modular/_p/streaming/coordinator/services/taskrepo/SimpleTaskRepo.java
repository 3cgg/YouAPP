package me.bunny.modular._p.streaming.coordinator.services.taskrepo;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Maps;

import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.modular._p.streaming.coordinator.Task;

public class SimpleTaskRepo implements TaskRepo {
	
	/**
	 * KEY :  WORKFLOW NAME
	 */
	private Map<String, Queue<Task>> tasks=Maps.newConcurrentMap();
	
	/**
	 * KEY :  ID
	 */
	private Map<String, Task> temp=Maps.newConcurrentMap();
	
	public SimpleTaskRepo() {
		
	}

	@Override
	public synchronized String addTask(Task task) {
		if(JStringUtils.isNullOrEmpty(task.getId())){
			task.setId(JUniqueUtils.SEQUECE);
		}
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
	public synchronized Task getTaskByWorfklowName(String workflowName) {
		Queue<Task> ts= tasks.get(workflowName);
		Task task=ts.poll();
		temp.remove(task.getId());
		return task;
	}

	@Override
	public Task peek(String taskId) {
		return temp.get(taskId);
	}

}
