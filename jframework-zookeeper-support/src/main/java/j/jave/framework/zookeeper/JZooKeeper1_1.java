package j.jave.framework.zookeeper;

import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class JZooKeeper1_1 {

	public static void main(String[] args) throws Exception {

		new JZooKeeper1_1().run();
		
		Thread.sleep(1000000);
	}

	class ZooKeeperWrapper {
		ZooKeeper zooKeeper;
	}

	public void run() throws IOException,
			KeeperException, InterruptedException {
		final String root = "/eeeee";
		ZooKeeper zk = get();
		zk.create(root, "rootNode".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		zk.create(root + "/testChildPathOne", "testChildDataOne".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		Stat stat = new Stat();

		byte[] bytes = zk.getData(root + "/testChildPathOne", new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				JZoonKeeperLoggerSupport.loggerWatchedEvent(event," the first zookeeper wather");
			}
		}, stat);
		System.out.println(new String(bytes));

		ZooKeeper zk1 = get();
		bytes = zk1.getData(root + "/testChildPathOne", new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				JZoonKeeperLoggerSupport.loggerWatchedEvent(event," the second zookeeper wather");
			}
		}, stat);
		System.out.println(new String(bytes));
		
		zk.delete(root + "/testChildPathOne", -1);
		
		zk.delete(root, -1);
		
		zk.close();

		zk1.close();
		
	}

	private ZooKeeper get() throws IOException {
		String hostPort="127.0.0.1:2181,127.0.0.1:2182";
		final ZooKeeperWrapper zooKeeperWrapper = new ZooKeeperWrapper();

		ZooKeeper zk = new ZooKeeper(hostPort, 3000, new Watcher() {
			public void process(WatchedEvent event) {
				Stat stat = new Stat();
				try {
					if (JStringUtils.isNotNullOrEmpty(event.getPath())) {
						byte[] bytes = zooKeeperWrapper.zooKeeper.getData(
								event.getPath(), true, stat);
						System.out.println("new value ["+event.getPath()+"]: " + new String(bytes));
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
				JZoonKeeperLoggerSupport.loggerWatchedEvent(event,"default zookeeper watcher");
			}
		});
		zooKeeperWrapper.zooKeeper = zk;
		return zk;
	}

}
