package j.jave.framework.model;

import java.sql.Timestamp;


/**
 * The super class that those all implementations can be serialized
 * @author J
 *
 */
public abstract class JBaseModel implements JModel {

	/**
	 * the primary key , uuid 
	 */
	private String id;
	
	/**
	 * create user id
	 */
	private String createId;
	
	/**
	 * update user id 
	 */
	private String updateId;
	
	/**
	 * create time
	 */
	private Timestamp createTime;
	
	/**
	 * update time
	 */
	private Timestamp updateTime;
	
	/**
	 * marks whether the record is deleted. Value is Y|N
	 */
	private String deleted;
	
	/**
	 * the property can limit the async operation effectively 
	 */
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
