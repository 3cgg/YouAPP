package j.jave.kernal.zookeeper;

@SuppressWarnings("serial")
public class JServiceZooKeeperNodePath implements JZooKeeperNodePath {

	private String root;

	private String category;

	private String type;

	private String instance;

	public JServiceZooKeeperNodePath() {
	}

	public JServiceZooKeeperNodePath(Class<?> serviceInterface,
			Class<?> serviceImplementation) {
		this.type = serviceInterface.getName();
		this.instance = serviceImplementation.getName();
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public String getPath() {
		return SLASH + root + SLASH + category + SLASH + type + SLASH
				+ instance;
	}

}
