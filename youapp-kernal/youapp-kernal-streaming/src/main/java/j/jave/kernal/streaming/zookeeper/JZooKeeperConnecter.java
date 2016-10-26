package j.jave.kernal.streaming.zookeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import j.jave.kernal.jave.utils.JStringUtils;

@SuppressWarnings("serial")
public class JZooKeeperConnecter implements Serializable {

	private JZooKeeperConfig zooKeeperConfig;
	
	public JZooKeeperConnecter(JZooKeeperConfig zooKeeperConfig) {
		this.zooKeeperConfig=zooKeeperConfig;
	}
	
	public ZookeeperExecutor connect(){
		return new ZookeeperExecutor() {
			@Override
			protected JZooKeeperConfig zooKeeperConfigProvide() {
				return zooKeeperConfig;
			}
		};
	}
	
	public interface NodeCallback extends Serializable {
		
		public void call(JNode node);
		
	}
	
	public interface NodeChildrenCallback extends Serializable {
		
		public void call(List<JNode> nodes);
		
	}
	
	public abstract class ZookeeperExecutor implements Serializable{
		
		private CuratorFramework curatorFramework;
		
		protected abstract JZooKeeperConfig zooKeeperConfigProvide();
		
		public ZookeeperExecutor() {
			JZooKeeperConfig zooKeeperConfig=zooKeeperConfigProvide();
	        CuratorFramework client = CuratorFrameworkFactory.builder()
	                .connectString(zooKeeperConfig.getConnectString())
	                .retryPolicy(zooKeeperConfig.getRetryPolicy())
	                .namespace(zooKeeperConfig.getNamespace())
	                .build();
	        client.start();
	        curatorFramework=client;
		}
		
		public String createPath(String path){
			return createPath(path,new byte[]{},CreateMode.PERSISTENT);
		}
		
		public String createEphSequencePath(String path){
			return createPath(path,new byte[]{},CreateMode.EPHEMERAL_SEQUENTIAL);
		}
		
		public String createPath(String path,byte[] data){
			try{
				return curatorFramework.create()
				.creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
				.forPath(path,data);
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public String createPath(String path,byte[] data,CreateMode createMode){
			try{
				return curatorFramework.create()
				.creatingParentContainersIfNeeded()
				.withMode(createMode)
				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
				.forPath(path,data);
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public void setPath(String path,String data){
			try{
				curatorFramework.setData()
				.forPath(path,JStringUtils.utf8(data));
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public void deletePath(String path){
			try{
				curatorFramework.delete()
				.forPath(path);
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public byte[] getPath(String path){
			try{
				return curatorFramework.getData()
				.forPath(path);
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public NodeCache watchPath(final String path,final NodeCallback nodeCallback,ExecutorService executor){

			try{
				final NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
				nodeCache.start(true);
				nodeCache.getListenable().addListener(new NodeCacheListener() {
					
					@Override
					public void nodeChanged() throws Exception {
						try{
							JNode node=new JNode();
							node.setPath(path);
							ChildData childData= nodeCache.getCurrentData();
							if(childData==null) return ;
							node.setData(childData.getData());
							nodeCallback.call(node);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, executor);
				return nodeCache;
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		public NodeCache watchPath(final String path,final NodeCallback nodeCallback){
			ExecutorService pool = Executors.newFixedThreadPool(1);
			return watchPath(path, nodeCallback, pool);
		}
		
		
		public PathChildrenCache watchChildrenPath(final String path,final NodeChildrenCallback nodeChildrenCallback,PathChildrenCacheEvent.Type... types){
			ExecutorService pool = Executors.newFixedThreadPool(1);
			return watchChildrenPath(path, nodeChildrenCallback, pool, types);
		}
		
		
		public PathChildrenCache watchChildrenPath(final String path,final NodeChildrenCallback nodeChildrenCallback,ExecutorService executor,PathChildrenCacheEvent.Type... types){
			try{
				final Type[] _types;
				if(types.length==0){
					_types=new PathChildrenCacheEvent.Type[]{PathChildrenCacheEvent.Type.CHILD_ADDED,
							PathChildrenCacheEvent.Type.CHILD_REMOVED};
				}
				else{
					_types=types;
				}
				final PathChildrenCache childrenCache=
						new PathChildrenCache(curatorFramework, path,false,false, executor);
						
				childrenCache.start(StartMode.NORMAL);
				childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
					
					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
						
						try{
							boolean done=false;
							for(Type type:_types){
								if(type==event.getType()){
									done=true;
									break;
								}
							}
							if(!done) return;
							
							List<ChildData> childDatas= childrenCache.getCurrentData();
							List<JNode> nodes=new ArrayList<>();
							for(ChildData childData:childDatas){
								JNode node=new JNode();
								node.setPath(childData.getPath());
								node.setData(childData.getData());
								nodes.add(node);
							}
							nodeChildrenCallback.call(nodes);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, executor);
				return childrenCache;
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		
		public boolean exists(final String path){
			try{
				return curatorFramework.checkExists()
				.forPath(path)!=null;
			}catch (Exception e) {
				throw new JCustomZooKeeperException(e);
			}
		}
		
		CuratorFramework backend(){
			return curatorFramework;
		}
		
	}
	
	
	
}
