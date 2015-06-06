package j.jave.framework.tkdd;

import j.jave.framework.utils.JUniqueUtils;


@SuppressWarnings("serial")
public abstract class JDefaultTaskMetadata implements JTaskMetadata {

	protected String name;
	
	/**
	 *  default value , can be replaced by call {@link #setId(String)}
	 */
	private String id=this.getClass().getName();
	
	/**
	 * dynamic generate for each instance.
	 */
	private String snapshotId=JUniqueUtils.unique();
	
	protected boolean enabled=true;
	
	protected boolean installed=true;
	
	protected boolean uninstalled=false;
	
	protected String describer;
	
	/**
	 * may be different one for each instance. 
	 * it is always null if the metadata is not snapshot,or the metadata is the root.
	 */
	protected JTaskMetadata owner=null;
	
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String id() {
		return this.id;
	}

	@Override
	public boolean enabled() {
		return this.enabled;
	}

	@Override
	public boolean installed() {
		return this.installed;
	}

	@Override
	public boolean uninstalled() {
		return this.uninstalled;
	}

	@Override
	public String describer() {
		return this.describer;
	}

	@Override
	public JTaskMetadata owner() {
		return this.owner;
	}

	public abstract void setName(String name) ;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	public void setUninstalled(boolean uninstalled) {
		this.uninstalled = uninstalled;
	}

	public abstract void setDescriber(String describer) ;

	public void setOwner(JTaskMetadata owner) {
		this.owner = owner;
	}
	
	void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String snapshotId() {
		return this.snapshotId;
	}
	
	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}
	
}
