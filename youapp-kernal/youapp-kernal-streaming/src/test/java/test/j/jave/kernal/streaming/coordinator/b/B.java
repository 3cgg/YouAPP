package test.j.jave.kernal.streaming.coordinator.b;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.streaming.coordinator.InstanceNodeVal;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.NodeStatus;
import j.jave.kernal.streaming.coordinator._SerializeFactoryGetter;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

public class B {

	private static ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
	
	private static JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private static void complete(final String path,Throwable t){
		final InstanceNodeVal instanceNodeVal=new InstanceNodeVal();
		NodeStatus nodeStatus=null;
		if(t==null){
			nodeStatus=NodeStatus.COMPLETE;
		}else{
			nodeStatus=NodeStatus.COMPLETE_ERROR;
			nodeStatus.setThrowable(t);
		}
		instanceNodeVal.setStatus(nodeStatus);
		
		executor.setPath(path, 
				SerializerUtils.serialize(serializerFactory, instanceNodeVal));
	}
	
	public static void main(String[] args) {
//		String path="/a/b/c";
		String path=NodeLeader.basePath()+"/instance/default/88/root/denoise-1";
//		if(!executor.exists(path)){
//			executor.createPath(path);
//		}
//		complete(path, new RuntimeException("test"));
		final InstanceNodeVal instanceNodeVal=
				SerializerUtils.deserialize(serializerFactory, executor.getPath(path),
						InstanceNodeVal.class);
		System.out.println(instanceNodeVal);
	}
	
	
}
