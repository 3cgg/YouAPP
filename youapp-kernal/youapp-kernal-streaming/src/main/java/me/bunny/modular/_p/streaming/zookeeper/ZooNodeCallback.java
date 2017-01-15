package me.bunny.modular._p.streaming.zookeeper;

import java.io.Serializable;

public interface ZooNodeCallback extends Serializable {
	
	public void call(ZooNode node);
	
}