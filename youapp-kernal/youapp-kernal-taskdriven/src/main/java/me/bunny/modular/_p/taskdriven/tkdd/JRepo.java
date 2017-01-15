package me.bunny.modular._p.taskdriven.tkdd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRepo {

	private static JRepo repo=new JRepo();
	
	public static JRepo get(){
		return repo;
	}
	
	/**
	 * predefined task meta data. see {@link JTaskMetadataOnTask}
	 */
	private Map<Class<?>, JTaskMetadata> defineTaskMetadatas=new ConcurrentHashMap<Class<?>, JTaskMetadata>();
	
	/**
	 * some snapshot of task metadata cached in the running time. the snapshot is never affected by the custom metadata.
	 * Will be change by the framework to dynamically change the strategy of task running. 
	 */
	private Map<Class<?>, JTaskMetadata> dynamicSnapshotMetadatas=new ConcurrentHashMap<Class<?>, JTaskMetadata>();
	
	
//	private Map<Class<? extends JTask>, Map<Class<? extends JTask>, JTaskMetadata>>
//	snapshotLinkedTaskMetadatas=new HashMap<Class<? extends JTask>, Map<Class<? extends JTask>,JTaskMetadata>>();
	
	public JTaskMetadata getDynamicSnapshotMetadata(JTask task,JTaskContext taskContext){
		
		// it will be initialized once in the bootstrap later.
		if(!defineTaskMetadatas.containsKey(task.getClass())){
			JTaskMetadata taskMetadata=task.getDefineTaskMetadata();
			taskMetadata.setOwner(null);
			defineTaskMetadatas.put(task.getClass(), taskMetadata);
		}
		
		//it will be initialized once in the bootstrap later.
		if(!dynamicSnapshotMetadatas.containsKey(task.getClass())){
			JTaskMetadata taskMetadata=task.getDefineTaskMetadata();
			taskMetadata.setOwner(null);
			dynamicSnapshotMetadatas.put(task.getClass(), taskMetadata);
			return taskMetadata;
		}
		else{
			return dynamicSnapshotMetadatas.get(task.getClass());
		}

//		if(taskContext.isRootTask()){
//			Class<? extends JTask> root=task.getClass();
//			// initialize snapshot root meta data
//			if(!snapshotLinkedTaskMetadatas.containsKey(root)){
//				Map<Class<? extends JTask>,JTaskMetadata> taskMap=new HashMap<Class<? extends JTask>, JTaskMetadata>();
//				snapshotLinkedTaskMetadatas.put(root, taskMap);
//				JTaskMetadata snapshotTaskMetadata=task.getSnapshotTaskMetadata();
//				snapshotTaskMetadata.setOwner(null);
//				taskMap.put(root, snapshotTaskMetadata);
//				return snapshotTaskMetadata;
//			}
//			else{
//				return snapshotLinkedTaskMetadatas.get(root).get(root);
//			}
//		}
//		else{
//			Map<Class<? extends JTask>,JTaskMetadata> taskMap=snapshotLinkedTaskMetadatas.get(taskContext.getRoot());
//			JTaskMetadata taskMetadata=taskMap.get(task.getClass());
//			// initialize the snapshot meta data
//			if(taskMetadata==null){
//				taskMetadata=task.getSnapshotTaskMetadata();
//				taskMetadata.setOwner(snapshotLinkedTaskMetadatas.get(taskContext.getRoot()).get(taskContext.getRoot()));
//				taskMap.put(task.getClass(), taskMetadata);
//			}
//			return taskMetadata;
//		}
	}
	
	
	
	
	
}
