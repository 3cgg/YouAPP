package test.j.jave.kernal.streaming.coordinator.b;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class C {
	
	private static ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
	
	private static ExecutorService zooKeeperExecutorService=Executors.newFixedThreadPool(9);
	
	public static void main(String[] args) {
		executor.watchChildrenPath("/ch", new ZooNodeChildrenCallback() {
			
			@Override
			public void call(List<ZooNode> nodes) {
				System.out.println(" ok ...");
				System.out.println("in : "+nodes.size());
			}
		}, zooKeeperExecutorService,PathChildrenCacheEvent.Type.CHILD_UPDATED);
	}
	
}
