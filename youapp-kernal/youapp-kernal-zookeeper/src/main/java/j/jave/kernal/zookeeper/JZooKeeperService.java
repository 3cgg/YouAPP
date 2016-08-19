package j.jave.kernal.zookeeper;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import j.jave.kernal.jave.service.JService;

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
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,JWatch watch){
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
	
	public boolean exist(JZooKeeperNode zooKeeperNode,JWatch watch){
		return zooKeeperClient.exist(zooKeeperNode,watch);
	}
	
	public JStatWrapper getLatest(JZooKeeperNode zooKeeperNode){
		return zooKeeperClient.getLatest(zooKeeperNode);
	}
	
	public JValue getNodeStat(JZooKeeperNode zooKeeperNode,JWatch watch){
		return zooKeeperClient.getNodeStat(zooKeeperNode, watch);
	}

}
