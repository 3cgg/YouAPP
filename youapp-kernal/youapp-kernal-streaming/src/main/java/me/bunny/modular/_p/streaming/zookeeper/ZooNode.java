package me.bunny.modular._p.streaming.zookeeper;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class ZooNode implements JModel{

	private String path;
	
	private byte[] data;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getData() {
		return data;
	}
	
	public byte[] getDataAsPossible(ZookeeperExecutor executor) {
		byte[] bytes=getData();
		if(bytes==null){
			bytes=executor.getPath(getPath());
		}
		return bytes;
	}
	

	public void setData(byte[] data) {
		this.data = data;
	}
	
	@Deprecated
	public String getStringData(){
		byte[] bytes=getData();
		if(bytes==null){
			return null;
		}
		return JStringUtils.utf8(getData());
	}
	

}
