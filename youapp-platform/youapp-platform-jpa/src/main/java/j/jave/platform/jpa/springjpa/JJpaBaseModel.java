package j.jave.platform.jpa.springjpa;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import me.bunny.kernel.jave.model.JBaseModel;

@MappedSuperclass
public class JJpaBaseModel extends JBaseModel {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "assigned")
	@GenericGenerator(name="assigned", strategy="assigned")
    @Column(name = "ID")
	@Override
	public String getId() {
		return super.getId();
	}
	
	@Column(name="CREATEID")
	@Override
	public String getCreateId() {
		return super.getCreateId();
	}
	
	@Column(name="CREATETIME")
	@Override
	public Timestamp getCreateTime() {
		return super.getCreateTime();
	}
	
	@Column(name="UPDATEID")
	@Override
	public String getUpdateId() {
		return super.getUpdateId();
	}
	
	@Column(name="UPDATETIME")
	@Override
	public Timestamp getUpdateTime() {
		return super.getUpdateTime();
	}
	
	@Column(name="DELETED")
	@Override
	public String getDeleted() {
		return super.getDeleted();
	}
	
	@Column(name="VERSION")
	@Override
	public int getVersion() {
		return super.getVersion();
	}
	
}
