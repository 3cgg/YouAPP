package j.jave.kernal.streaming.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;

@SuppressWarnings("serial")
public class JKafkaConsumerConfig extends JKafkaConfig{
	
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
