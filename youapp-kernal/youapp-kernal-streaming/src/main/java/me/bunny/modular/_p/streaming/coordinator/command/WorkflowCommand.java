package me.bunny.modular._p.streaming.coordinator.command;

import java.lang.reflect.ParameterizedType;

import me.bunny.kernel._c.model.JModel;
import me.bunny.modular._p.streaming.coordinator.CommandResource;
import me.bunny.modular._p.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;

public abstract class WorkflowCommand<T extends WorkflowCommandModel> {
	
	public interface WorkflowCommandModel extends JModel{}
	
	@SuppressWarnings("unchecked")
	public Class<T> getGenericClass(){
		ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}
	
	
	public final void execute(T commandModel,CommandResource commandResource){
		try{
			doExecute(commandModel,commandResource);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void doExecute(T commandModel,CommandResource commandResource) throws Exception;
	
}
