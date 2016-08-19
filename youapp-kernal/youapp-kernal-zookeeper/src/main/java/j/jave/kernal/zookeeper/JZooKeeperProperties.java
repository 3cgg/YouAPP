package j.jave.kernal.zookeeper;


public class JZooKeeperProperties {

	/**
	 * connectString comma separated host:port pairs, each corresponding to a zk server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002" If the optional chroot suffix is used the example would look like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a" where the client would be rooted at "/app/a" and all paths would be relative to this root - ie getting/setting/etc... "/foo/bar" would result in operations being run on "/app/a/foo/bar" (from the server perspective).
	 */
	public static final String SERVICE_ZOOKEEPER_SERVERS="youapp.service.zookeeper.servers";
	
}
