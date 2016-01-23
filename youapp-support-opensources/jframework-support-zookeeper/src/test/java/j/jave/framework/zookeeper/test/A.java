package j.jave.framework.zookeeper.test;

import j.jave.framework.zookeeper.support.JACL;
import j.jave.framework.zookeeper.support.JDigestAuth;
import j.jave.framework.zookeeper.support.JSimpleZooKeeperNodePath;
import j.jave.framework.zookeeper.support.JValue;
import j.jave.framework.zookeeper.support.JWatch;
import j.jave.framework.zookeeper.support.JZooKeeperClient;
import j.jave.framework.zookeeper.support.JZooKeeperNode;
import j.jave.framework.zookeeper.support.JZooKeeperNodeValue;

import org.apache.zookeeper.WatchedEvent;

public class A {

	JZooKeeperClient zooKeeperClient=new JZooKeeperClient("127.0.0.1:2181,127.0.0.1:2182");
	
	public static void main(String[] args) {
		A a=new A();
		a.run();
	}
	
	void run(){
		JZooKeeperNode zooKeeperNode=new JZooKeeperNode();
		JSimpleZooKeeperNodePath simpleZooKeeperNodePath=new JSimpleZooKeeperNodePath("/cc");
		zooKeeperNode.setZooNodePath(simpleZooKeeperNodePath);
		zooKeeperNode.setInstance(new JZooKeeperNodeValue() {
			@Override
			public byte[] getValue() {
				return "jiahzongjin".getBytes();
			}
		});
		JDigestAuth digestAuth=new JDigestAuth();
		digestAuth.setUsername("ZJ");
		digestAuth.setPassword("ZJ");
		JACL jacl=new JACL(digestAuth);
		
		zooKeeperClient.createNode(zooKeeperNode,jacl);
		
		zooKeeperClient.addAuth(digestAuth);
		
		digestAuth=new JDigestAuth();
		digestAuth.setUsername("ZJ");
		digestAuth.setPassword("ZaaaaaaJ");
		zooKeeperClient.addAuth(digestAuth);
		
		JValue value= zooKeeperClient.getNodeStat(zooKeeperNode, null);
		System.out.println(new String(value.getBytes()));
		JWatch watch=new JWatch() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("eeeeeeeeee-->"+event.getType()+"  state:"+event.getState());
			}
		};
		zooKeeperClient.getValue(zooKeeperNode, watch);
		
		zooKeeperClient.exist(zooKeeperNode, new JWatch() {
			
			@Override
			public void process(WatchedEvent event) {
				System.out.println("dddddddddd");
			}
		});
		
		zooKeeperClient.setNode(zooKeeperNode, "test-zhong".getBytes());
		
		zooKeeperClient.deleteNode(zooKeeperNode);
		
		System.out.println("end");
	}
}
