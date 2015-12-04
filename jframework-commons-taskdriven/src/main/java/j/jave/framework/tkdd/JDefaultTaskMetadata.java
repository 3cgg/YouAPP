package j.jave.framework.tkdd;

import j.jave.framework.commons.auth.JAuthUtils;
import j.jave.framework.commons.auth.JCredentials;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JUniqueUtils;

import java.util.Set;

import javax.security.auth.Subject;


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
	 * may be different one for each meta data instance. 
	 */
	protected Subject owner=null;
	
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
	public Subject owner() {
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

	public void setOwner(Subject owner) {
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
	
	@Override
	public boolean authorize(Subject subject) {
		if(owner==null) return true; // anyone the task never belong to.
		Set<JCredentials> holdCredentials= owner.getPrivateCredentials(JCredentials.class);
		if(!JCollectionUtils.hasInCollect(holdCredentials)) return  true;  // never need authetication
		JCredentials holdCredential=holdCredentials.iterator().next(); // only one 
		if(subject==null) return false; // not provide an entity for the access.
		Set<JCredentials> providedCredentials= subject.getPrivateCredentials(JCredentials.class);
		if(!JCollectionUtils.hasInCollect(providedCredentials)) return false; // never hold credential
		JCredentials providedCredential=providedCredentials.iterator().next(); // only one 
		
		if(JAuthUtils.matchesAccess(holdCredential, providedCredential))  return true; // credential matches 
		
		return false;
	}
	
	
	
}
