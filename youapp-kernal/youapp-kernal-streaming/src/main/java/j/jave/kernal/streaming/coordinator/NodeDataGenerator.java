package j.jave.kernal.streaming.coordinator;

import java.util.Map;

public interface NodeDataGenerator {

	NodeData generate(String name,Map map);
	
}
