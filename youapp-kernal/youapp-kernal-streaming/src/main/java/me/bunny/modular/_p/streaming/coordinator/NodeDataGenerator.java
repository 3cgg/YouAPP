package me.bunny.modular._p.streaming.coordinator;

import java.util.Map;

public interface NodeDataGenerator {

	NodeData generate(String name,Map map);
	
}
