package test.j.jave.kernal.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import me.bunny.kernel.jave.utils.JStringUtils;

public class JZooKeeper2 {

	public static void main(String[] args) throws Exception {

		new JZooKeeper2().run();
		
		Thread.sleep(1000000);
	}

	class ZooKeeperWrapper {
		ZooKeeper zooKeeper;
	}

	public void run() throws IOException,
			KeeperException, InterruptedException {
		final String root = "/eeeee";
		ZooKeeper zk = get();

		// 创建一个目录节点
		zk.create(root, "rootNode".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zk.create(root + "/testChildPathOne", "testChildDataOne".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		Stat stat = new Stat();

		byte[] bytes = zk.getData(root + "/testChildPathOne", new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				System.out.println("triggered by "+event.getPath()+" event type : "+event.getType());
			}
		}, stat);
		System.out.println(new String(bytes));
		
		// 删除子目录节点
		zk.delete(root + "/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete(root, -1);

		// 关闭连接
		zk.close();

	}

	private ZooKeeper get() throws IOException {
		String hostPort="127.0.0.1:2181,127.0.0.1:2182";
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
		return zk;
	}

}
