package me.bunny.modular._p.streaming.coordinator.services.taskrepo;

import me.bunny.kernel._c.service.JService;
import me.bunny.modular._p.streaming.coordinator.Task;

public interface TaskRepo extends JService{

	String addTask(Task task);
	
	/**
	 * retrieve but not remove the task
	 * @param id
	 * @return
	 */
	Task peek(String taskId);
	
	/**
	 * retrieve and remove the task if existing in the repo
	 * @param workflowName
	 * @return
	 */
	Task getTaskByWorfklowName(String workflowName);
	
}
