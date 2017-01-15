package j.jave.kernal.streaming.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;

import me.bunny.kernel.jave.utils.JObjectUtils;

@SuppressWarnings("serial")
public class KafkaConsumerConfig extends KafkaConfig{
	
	/**
	 * session.timeout.ms
	 */
	private String sessionTimeoutMs;
	
	/**
	 * request.timeout.ms
	 */
	private String requestTimeoutMs;
	
	/**
	 * enable.auto.commit  "false"/"true"
	 */
	private String enableAutoCommit;
	
	/**
	 * auto.commit.interval.ms
	 */
	private String autoCommitIntervalMs;
	
	
	/**
	 * group.id
	 */
	private String groupId;
	
	/**
	 * key.deserializer
	 * {@link StringDeserializer}
	 */
	private String keyDeserializer;
	
	/**
	 * value.deserializer
	 * {@link StringDeserializer}
	 */
	private String valueDeserializer;
	
	public static KafkaConsumerConfig build(Map conf){
		KafkaConfig kafkaConfig=KafkaConfig.build(conf);
		KafkaConsumerConfig consumerConfig=JObjectUtils.simpleCopy(kafkaConfig, KafkaConsumerConfig.class);
		consumerConfig.setAutoCommitIntervalMs(String.valueOf(conf.get("auto.commit.interval.ms")));
		consumerConfig.setEnableAutoCommit(String.valueOf(conf.get("enable.auto.commit")));
		consumerConfig.setGroupId(String.valueOf(conf.get("group.id")));
		consumerConfig.setKeyDeserializer(String.valueOf(conf.get("key.deserializer")));
		consumerConfig.setValueDeserializer(String.valueOf(conf.get("value.deserializer")));
		consumerConfig.setRequestTimeoutMs(String.valueOf(conf.get("request.timeout.ms")));
		consumerConfig.setSessionTimeoutMs(String.valueOf(conf.get("session.timeout.ms")));
		return consumerConfig;
	}
	
	public static Map<String, Object> def(){
		Map<String, Object> conf=new HashMap<>();
		conf.put("auto.commit.interval.ms", 2000);
		conf.put("enable.auto.commit", "false");
		conf.put("group.id", "consumer-default");
		conf.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		conf.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		conf.put("request.timeout.ms", 60000);
		conf.put("session.timeout.ms", 40000);
		return conf;
	}

	/**
	 * @return the sessionTimeoutMs
	 */
	public String getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	/**
	 * @param sessionTimeoutMs the sessionTimeoutMs to set
	 */
	public void setSessionTimeoutMs(String sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	/**
	 * @return the requestTimeoutMs
	 */
	public String getRequestTimeoutMs() {
		return requestTimeoutMs;
	}

	/**
	 * @param requestTimeoutMs the requestTimeoutMs to set
	 */
	public void setRequestTimeoutMs(String requestTimeoutMs) {
		this.requestTimeoutMs = requestTimeoutMs;
	}

	/**
	 * @return the enableAutoCommit
	 */
	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	/**
	 * @param enableAutoCommit the enableAutoCommit to set
	 */
	public void setEnableAutoCommit(String enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	/**
	 * @return the autoCommitIntervalMs
	 */
	public String getAutoCommitIntervalMs() {
		return autoCommitIntervalMs;
	}

	/**
	 * @param autoCommitIntervalMs the autoCommitIntervalMs to set
	 */
	public void setAutoCommitIntervalMs(String autoCommitIntervalMs) {
		this.autoCommitIntervalMs = autoCommitIntervalMs;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * @return the keyDeserializer
	 */
	public String getKeyDeserializer() {
		return keyDeserializer;
	}

	/**
	 * @param keyDeserializer the keyDeserializer to set
	 */
	public void setKeyDeserializer(String keyDeserializer) {
		this.keyDeserializer = keyDeserializer;
	}

	/**
	 * @return the valueDeserializer
	 */
	public String getValueDeserializer() {
		return valueDeserializer;
	}

	/**
	 * @param valueDeserializer the valueDeserializer to set
	 */
	public void setValueDeserializer(String valueDeserializer) {
		this.valueDeserializer = valueDeserializer;
	}
}
