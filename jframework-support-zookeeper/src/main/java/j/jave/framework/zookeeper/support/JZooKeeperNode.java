package j.jave.framework.zookeeper.support;

import j.jave.framework.commons.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.List;

public class JZooKeeperNode {

	private final String unique = JUniqueUtils.unique();

	/**
	 * like "/root/category/type/instance"
	 */
	private JZooKeeperNodePath zooNodePath;

	private JCreateMode createMode = JCreateMode.EPHEMERAL;

	private JZooKeeperNodeValue instance;

	private List<JACL> acls = new ArrayList<JACL>();

	public JZooKeeperNode() {

	}

	public JZooKeeperNode(JZooKeeperNodePath zooNodePath) {
		this.zooNodePath = zooNodePath;
	}

	public JZooKeeperNodePath getZooNodePath() {
		return zooNodePath;
	}

	public void setZooNodePath(JZooKeeperNodePath zooNodePath) {
		this.zooNodePath = zooNodePath;
	}

	public String getUnique() {
		return unique;
	}

	public JZooKeeperNodeValue getInstance() {
		return instance;
	}

	public void setInstance(JZooKeeperNodeValue instance) {
		this.instance = instance;
	}

	public JCreateMode getCreateMode() {
		return createMode;
	}

	public void setCreateMode(JCreateMode createMode) {
		this.createMode = createMode;
	}

	public List<JACL> getAcls() {
		return acls;
	}

	public void setAcls(List<JACL> acls) {
		this.acls = acls;
	}

}
