package j.jave.framework.zookeeper.support;

import org.apache.zookeeper.data.Stat;

public class JStatWrapper {

	private Stat stat;
	
	public JStatWrapper(){
		this(new Stat());
	}
	
	JStatWrapper(Stat stat){
		this.stat=stat;
	}
	
	void setStat(Stat stat) {
		this.stat = stat;
	}

	public long getCzxid() {
		return stat.getCzxid();
	}

	public void setCzxid(long czxid) {
		stat.setCzxid(czxid);
	}

	public long getMzxid() {
		return stat.getMzxid();
	}

	public void setMzxid(long mzxid) {
		stat.setMzxid(mzxid);
	}

	public long getCtime() {
		return stat.getCtime();
	}

	public void setCtime(long ctime) {
		stat.setCtime(ctime);
	}

	public long getMtime() {
		return stat.getMtime();
	}

	public void setMtime(long mtime) {
		stat.setMtime(mtime);
	}

	public int getVersion() {
		return stat.getVersion();
	}

	public void setVersion(int version) {
		stat.setVersion(version);
	}

	public int getCversion() {
		return stat.getCversion();
	}

	public void setCversion(int cversion) {
		stat.setCversion(cversion);
	}

	public int getAversion() {
		return stat.getAversion();
	}

	public void setAversion(int aversion) {
		stat.setAversion(aversion);
	}

	public long getEphemeralOwner() {
		return stat.getEphemeralOwner();
	}

	public void setEphemeralOwner(long ephemeralOwner) {
		stat.setEphemeralOwner(ephemeralOwner);
	}

	public int getDataLength() {
		return stat.getDataLength();
	}

	public void setDataLength(int dataLength) {
		stat.setDataLength(dataLength);
	}

	public int getNumChildren() {
		return stat.getNumChildren();
	}

	public void setNumChildren(int numChildren) {
		stat.setNumChildren(numChildren);
	}

	public long getPzxid() {
		return stat.getPzxid();
	}

	public void setPzxid(long pzxid) {
		stat.setPzxid(pzxid);
	}

}
