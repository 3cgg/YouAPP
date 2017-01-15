package j.jave.kernal.streaming.coordinator.services.workflowmetarepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.coordinator._SerializeFactoryGetter;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import me.bunny.kernel.jave.serializer.JSerializerFactory;
import me.bunny.kernel.jave.serializer.SerializerUtils;

public class ZKWorkflowMetaRepo extends SimpleWorkflowMetaRepo {

	private final ZookeeperExecutor executor;
	
	private final String path;
	
	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	/**
	 * KEY :  WORKFLOW NAME
	 */
	private Map<String, WorkflowMeta> workflowMetas=Maps.newConcurrentMap();
	
	
	public ZKWorkflowMetaRepo(ZookeeperExecutor executor,String path,ChangedCallBack changedCallBack) {
		super(changedCallBack);
		this.executor=executor;
		this.path = path;
		if(!executor.exists(path)){
			executor.createPath(path);
		}
		_init();
		notifyAdded(new ArrayList<>(workflowMetas.values()));
	}

	private void _init(){
		List<String> paths=executor.getChildren(path);
		for(String _ph:paths){
			String _path=path+"/"+_ph;
			byte[] bytes=executor.getPath(_path);
			if(bytes!=null&&bytes.length>0){
				WorkflowMeta workflowMeta=
						SerializerUtils.deserialize(serializerFactory, bytes, WorkflowMeta.class);
				workflowMetas.put(workflowMeta.getName(), workflowMeta);
			}
		}
	}
	
	@Override
	protected String _addWorkflowMeta(WorkflowMeta workflowMeta) {
		final String workflowAddPath=NodeLeader.workflowAddPath();
		String tempPath=workflowAddPath+"/"+workflowMeta.getName();
		if(executor.exists(tempPath)){
			return null;
		}
		workflowMeta.setUnique(tempPath);
		executor.createPath(tempPath, 
				SerializerUtils.serialize(serializerFactory, workflowMeta));
		workflowMetas.put(workflowMeta.getName(), workflowMeta);
		return tempPath;
	}

	@Override
	protected WorkflowMeta _removeWorkflowMeta(String workflowName) {
		final String workflowAddPath=NodeLeader.workflowAddPath();
		String tempPath=workflowAddPath+"/"+workflowName;
		executor.deletePath(tempPath);
		return workflowMetas.remove(workflowName);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
