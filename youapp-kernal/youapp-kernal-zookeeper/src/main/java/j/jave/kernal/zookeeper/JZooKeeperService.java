package j.jave.kernal.zookeeper;

import java.util.List;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.JServiceOrder;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import me.bunny.kernel.jave.service.JService;

@JServiceOrder(value=1000,listenerClasses={JServiceHubInitializedListener.class})
public class JZooKeeperService
extends JServiceFactorySupport<JZooKeeperService>
implements JService , JServiceHubInitializedListener
{
	private JZooKeeperClient zooKeeperClient=null;
	
	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		String connectString=JConfiguration.get().getString(JZooKeeperProperties.SERVICE_ZOOKEEPER_SERVERS, 
				"127.0.0.1:2181");
		zooKeeperClient=new JZooKeeperClient(connectString);
		return true;
	}
	
	@Override
	protected JZooKeeperService doGetService() {
		return this;
	}
	
	
	public void createNode(JZooKeeperNode zooKeeperNode,JACL... acls){
		zooKeeperClient.createNode(zooKeeperNode, acls);
	}
	
	public void createNode(JZooKeeperNode zooKeeperNode){
		zooKeeperClient.createNode(zooKeeperNode);
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,JWatcher watch){
		return zooKeeperClient.getValue(zooKeeperNode, watch);
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode){
		return zooKeeperClient.getValue(zooKeeperNode);
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,boolean watch){
		return zooKeeperClient.getValue(zooKeeperNode, watch);
	}
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes,int version){
		zooKeeperClient.setNode(zooKeeperNode, bytes, version);
	}
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes){
		zooKeeperClient.setNode(zooKeeperNode, bytes);
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode,int version){
		zooKeeperClient.deleteNode(zooKeeperNode, version);
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode){
		zooKeeperClient.deleteNode(zooKeeperNode);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode){
		return zooKeeperClient.exist(zooKeeperNode);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode,JWatcher watch){
		return zooKeeperClient.exist(zooKeeperNode,watch);
	}
	
	public JStatWrapper getLatest(JZooKeeperNode zooKeeperNode){
		return zooKeeperClient.getLatest(zooKeeperNode);
	}
	
	public JValue getNodeStat(JZooKeeperNode zooKeeperNode,JWatcher watch){
		return zooKeeperClient.getNodeStat(zooKeeperNode, watch);
	}

	
	public void getChildren(JZooKeeperNode zooKeeperNode, JWatcher watcher,
			JChildren2Callback cb, Object ctx){
		zooKeeperClient.getChildren(zooKeeperNode, watcher, cb, ctx);
	}
	
	public List<String> getChildren(JZooKeeperNode zooKeeperNode, JWatcher watcher){
		return zooKeeperClient.getChildren(zooKeeperNode, watcher);
	}
	
	public List<String> getChildren(JZooKeeperNode zooKeeperNode){
		return getChildren(zooKeeperNode, null);
	}
	
	
	public void createDir(JZooKeeperNode zooKeeperNode,boolean recurse){
		if(!recurse){
			createDir(zooKeeperNode);
			return ;
		}
		String path=zooKeeperNode.getZooNodePath().getPath();
		String parentPath="/";
		for(int i=1;i<path.length();i++){
			char c=path.charAt(i);
			if(c=='/'){
				JZooKeeperNode parentZooKeeperNode=new JZooKeeperNode();
				parentZooKeeperNode.setPath(parentPath);
				parentZooKeeperNode.setValue(parentPath);
				parentZooKeeperNode.setCreateMode(JCreateMode.PERSISTENT);
				createDir(parentZooKeeperNode); 
			}
			parentPath=parentPath+c;
		}
		createDir(zooKeeperNode);
	}
	
	public void createDir(JZooKeeperNode zooKeeperNode){
		if(!exist(zooKeeperNode)){
			createNode(zooKeeperNode);
		}
	}
	
}
