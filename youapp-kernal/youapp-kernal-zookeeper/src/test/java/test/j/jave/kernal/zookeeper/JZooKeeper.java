package test.j.jave.kernal.zookeeper;

import j.jave.kernal.jave.utils.JStringUtils;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class JZooKeeper {

	public static void main(String[] args) throws Exception {

		// new Thread("a-thread") {
		// @Override
		// public void run() {
		// try {
		// new JZooKeeper().run("127.0.0.1:2181","/a-ee");
		// } catch (IOException | KeeperException | InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }.start();

		// new Thread("b-thread"){
		// @Override
		// public void run() {
		// try {
		// new JZooKeeper().run("127.0.0.1:2182","/b-dd");
		// } catch (IOException | KeeperException | InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }.start();

//		new JZooKeeper().run1("127.0.0.1:2182","/oooo");
		
		Thread.sleep(1000000);
	}

	class ZooKeeperWrapper {
		ZooKeeper zooKeeper;
	}

	
	public void run1(String hostPort, String rroot) throws IOException,
			KeeperException, InterruptedException {
		final String root = rroot;
		final ZooKeeperWrapper zooKeeperWrapper = new ZooKeeperWrapper();
		// 创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper(hostPort, 3000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				Stat stat = new Stat();
				try {
					if (JStringUtils.isNotNullOrEmpty(event.getPath())) {
						byte[] bytes = zooKeeperWrapper.zooKeeper.getData(
								event.getPath(), true, stat);
						System.out.println("new value : " + new String(bytes));
					}
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("已经触发了" + event.getType() + "事件！");
			}
		});
		zooKeeperWrapper.zooKeeper = zk;

		// 创建一个目录节点
		zk.create(root, "rootNode".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zk.create(root + "/testChildPathOne", "testChildDataOne".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		Stat stat = new Stat();

		byte[] bytes = zk.getData(root + "/testChildPathOne", true, stat);
		System.out.println(new String(bytes));
		
		new Thread("000000000000"){
			public void run() {
				Stat stat = new Stat();
				byte[] bytes;
				try {
					bytes = zooKeeperWrapper.zooKeeper.getData(
							root + "/testChildPathOne", true, stat);
					System.out.println("new value : " + new String(bytes));
					
					Thread.sleep(10000);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();

		
		new Thread("1111111111"){
			public void run() {
				Stat stat = new Stat();
				byte[] bytes;
				try {
					bytes = zooKeeperWrapper.zooKeeper.getData(
							root + "/testChildPathOne", true, stat);
					System.out.println("new value : " + new String(bytes));
					
					Thread.sleep(10000);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();

		
		
		// 删除子目录节点
		zk.delete(root + "/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete(root, -1);

		// 关闭连接
		zk.close();

	}

	public void run(String hostPort, String rroot) throws IOException,
			KeeperException, InterruptedException {

		final String root = rroot;

		final ZooKeeperWrapper zooKeeperWrapper = new ZooKeeperWrapper();

		// 创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper(hostPort, 3000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				Stat stat = new Stat();
				try {
					if (JStringUtils.isNotNullOrEmpty(event.getPath())) {
						byte[] bytes = zooKeeperWrapper.zooKeeper.getData(
								event.getPath(), true, stat);
						System.out.println("new value : " + new String(bytes));
					}
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("已经触发了" + event.getType() + "事件！");
			}
		});
		zooKeeperWrapper.zooKeeper = zk;

		// 创建一个目录节点
		zk.create(root, "rootNode".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zk.create(root + "/testChildPathOne", "testChildDataOne".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		Stat stat = new Stat();

		byte[] bytes = zk.getData(root + "/testChildPathOne", true, stat);
		System.out.println(new String(bytes));

		// 删除子目录节点
		zk.delete(root + "/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete(root, -1);

		// 关闭连接
		zk.close();

	}

}
