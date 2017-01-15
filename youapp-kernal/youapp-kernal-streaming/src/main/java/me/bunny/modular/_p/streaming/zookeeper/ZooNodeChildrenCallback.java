package me.bunny.modular._p.streaming.zookeeper;

import java.io.Serializable;
import java.util.List;

public interface ZooNodeChildrenCallback extends Serializable {
	
	public void call(List<ZooNode> nodes);
	
}