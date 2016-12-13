package j.jave.kernal.streaming.logging;

import java.util.Arrays;
import java.util.List;

public class KafkaLoggerTopicMatch {
	
	private final List<String> topics;

	public KafkaLoggerTopicMatch(List<String> topics) {
		this.topics = topics;
	}
	
	public KafkaLoggerTopicMatch(String... topic) {
		this.topics=Arrays.asList(topic);
	}
	
	public boolean matches(KafkaLogger kafkaLogger){
		return true;
	}
	
	public List<String> getTopics() {
		return topics;
	}
	
	
}
