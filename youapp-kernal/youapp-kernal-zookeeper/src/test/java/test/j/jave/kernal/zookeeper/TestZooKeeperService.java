package test.j.jave.kernal.zookeeper;

import j.jave.kernal.zookeeper.JACL;
import j.jave.kernal.zookeeper.JDigestAuth;
import j.jave.kernal.zookeeper.JSimpleZooKeeperNodePath;
import j.jave.kernal.zookeeper.JValue;
import j.jave.kernal.zookeeper.JWatcher;
import j.jave.kernal.zookeeper.JZooKeeperNode;
import j.jave.kernal.zookeeper.JZooKeeperNodeValue;
import j.jave.kernal.zookeeper.JZooKeeperService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.apache.zookeeper.WatchedEvent;
import org.junit.Test;

public class TestZooKeeperService extends TestEventSupport {

	JZooKeeperService zooKeeperService=
			JServiceHubDelegate.get().getService(this, JZooKeeperService.class);
	
	@Test
	public void testZoo(){
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
		
		zooKeeperService.createNode(zooKeeperNode,jacl);
		
//		zooKeeperService.addAuth(digestAuth);
//		
//		digestAuth=new JDigestAuth();
//		digestAuth.setUsername("ZJ");
//		digestAuth.setPassword("ZaaaaaaJ");
//		zooKeeperService.addAuth(digestAuth);
		
		JValue value= zooKeeperService.getNodeStat(zooKeeperNode, null);
		System.out.println(new String(value.getBytes()));
		JWatcher watch=new JWatcher() {
			@Override
			public void doProcess(WatchedEvent event) {
				System.out.println("eeeeeeeeee-->"+event.getType()+"  state:"+event.getState());
			}
		};
		zooKeeperService.getValue(zooKeeperNode, watch);
		
		zooKeeperService.exist(zooKeeperNode, new JWatcher() {
			
			@Override
			public void doProcess(WatchedEvent event) {
				System.out.println("dddddddddd");
			}
		});
		
		zooKeeperService.setNode(zooKeeperNode, "test-zhong".getBytes());
		
		zooKeeperService.deleteNode(zooKeeperNode);
		
		System.out.println("end");
	}
}
