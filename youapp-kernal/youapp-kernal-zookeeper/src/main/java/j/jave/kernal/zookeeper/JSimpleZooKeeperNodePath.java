package j.jave.kernal.zookeeper;

@SuppressWarnings("serial")
public class JSimpleZooKeeperNodePath implements JZooKeeperNodePath {

	private final String path;

	public JSimpleZooKeeperNodePath(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return this.path;
	}

}
