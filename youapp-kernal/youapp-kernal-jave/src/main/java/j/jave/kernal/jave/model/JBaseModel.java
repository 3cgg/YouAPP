package j.jave.kernal.jave.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;

import java.sql.Timestamp;


/**
 * The super class that those all implementations can be serialized
 * @author J
 *
 */
@SuppressWarnings("serial")
public abstract class JBaseModel implements JModel {

	/**
	 * the primary key , uuid 
	 */
	@JColumn(name="ID",type=JSQLType.VARCHAR,length=32)
	private String id;
	
	/**
	 * create user id
	 */
	@JColumn(name="CREATEID",type=JSQLType.VARCHAR,length=32)
	private String createId;
	
	/**
	 * update user id 
	 */
	@JColumn(name="UPDATEID",type=JSQLType.VARCHAR,length=32)
	private String updateId;
	
	/**
	 * create time
	 */
	@JColumn(name="CREATETIME",type=JSQLType.TIMESTAMP)
	private Timestamp createTime;
	
	/**
	 * update time
	 */
	@JColumn(name="UPDATETIME",type=JSQLType.TIMESTAMP)
	private Timestamp updateTime;
	
	/**
	 * marks whether the record is deleted. Value is Y|N
	 */
	@JColumn(name="DELETED",type=JSQLType.VARCHAR,length=1)
	private String deleted;
	
	/**
	 * the property can limit the async operation effectively 
	 */
	@JColumn(name="VERSION",type=JSQLType.INTEGER)
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

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Override
	public JBaseModel clone() throws CloneNotSupportedException {
		return (JBaseModel) super.clone();
	}
	
}
