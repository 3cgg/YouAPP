package j.jave.framework.tkdd;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRepo {

	private static JRepo repo=new JRepo();
	
	public static JRepo get(){
		return repo;
	}
	
	private Map<Class<?>, JTaskMetadata> defineTaskMetadatas=new ConcurrentHashMap<Class<?>, JTaskMetadata>();

	private Map<Class<? extends JTask>, Map<Class<? extends JTask>, JTaskMetadata>>
	snapshotLinkedTaskMetadatas=new HashMap<Class<? extends JTask>, Map<Class<? extends JTask>,JTaskMetadata>>();
	
	public JTaskMetadata getDynamicSnapshotMetadata(JTask task,JTaskContext taskContext){
		
		if(taskContext.getCurrent()!=task.getClass()){
			throw new JTaskExecutionException("context is not configed correctly.");
		}
		
		// it will be initialized once in the bootstrap later.
		if(!defineTaskMetadatas.containsKey(task.getClass())){
			JTaskMetadata taskMetadata=task.getDefineTaskMetadata();
			taskMetadata.setOwner(null);
			defineTaskMetadatas.put(task.getClass(), taskMetadata);
		}

		if(taskContext.isRootTask()){
			Class<? extends JTask> root=task.getClass();
			// initialize snapshot root metadata
			if(!snapshotLinkedTaskMetadatas.containsKey(root)){
				Map<Class<? extends JTask>,JTaskMetadata> taskMap=new HashMap<Class<? extends JTask>, JTaskMetadata>();
				snapshotLinkedTaskMetadatas.put(root, taskMap);
				JTaskMetadata snapshotTaskMetadata=task.getSnapshotTaskMetadata();
				snapshotTaskMetadata.setOwner(null);
				taskMap.put(root, snapshotTaskMetadata);
				return snapshotTaskMetadata;
			}
			else{
				return snapshotLinkedTaskMetadatas.get(root).get(root);
			}
		}
		else{
			Map<Class<? extends JTask>,JTaskMetadata> taskMap=snapshotLinkedTaskMetadatas.get(taskContext.getRoot());
			JTaskMetadata taskMetadata=taskMap.get(task.getClass());
			// initialize the snapshot metadata
			if(taskMetadata==null){
				taskMetadata=task.getSnapshotTaskMetadata();
				taskMetadata.setOwner(snapshotLinkedTaskMetadatas.get(taskContext.getRoot()).get(taskContext.getRoot()));
				taskMap.put(task.getClass(), taskMetadata);
			}
			return taskMetadata;
		}
	}
	
	
	
	
	
}
