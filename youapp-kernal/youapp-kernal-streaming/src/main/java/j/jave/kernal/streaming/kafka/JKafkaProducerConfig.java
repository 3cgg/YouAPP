package j.jave.kernal.streaming.kafka;

import org.apache.kafka.common.serialization.StringSerializer;

@SuppressWarnings("serial")
public class JKafkaProducerConfig extends JKafkaConfig{
	
	/**
	 * acks ;  "all"/...
	 */
	private String acks;
	
	/**
	 * retries  , 0,1,2,...
	 */
	private int retries;
	
	/**
	 * batch.size; 16384
	 */
	private int batchSize;
	
	/**
	 * linger.ms; 1
	 */
	private int lingerMs;
	
	
	/**
	 * buffer.memory;  33554432
	 */
	private int bufferMemory;

	
	/**
	 * key.serializer
	 * {@link StringSerializer}
	 */
	private String keySerializer;
	
	
	/**
	 * value.serializer
	 * {@link StringSerializer}
	 */
	private String valueSerializer;
	
	

	/**
	 * @return the acks
	 */
	public String getAcks() {
		return acks;
	}


	/**
	 * @param acks the acks to set
	 */
	public void setAcks(String acks) {
		this.acks = acks;
	}


	/**
	 * @return the retries
	 */
	public int getRetries() {
		return retries;
	}


	/**
	 * @param retries the retries to set
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}


	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}


	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}


	/**
	 * @return the lingerMs
	 */
	public int getLingerMs() {
		return lingerMs;
	}


	/**
	 * @param lingerMs the lingerMs to set
	 */
	public void setLingerMs(int lingerMs) {
		this.lingerMs = lingerMs;
	}


	/**
	 * @return the bufferMemory
	 */
	public int getBufferMemory() {
		return bufferMemory;
	}


	/**
	 * @param bufferMemory the bufferMemory to set
	 */
	public void setBufferMemory(int bufferMemory) {
		this.bufferMemory = bufferMemory;
	}


	/**
	 * @return the keySerializer
	 */
	public String getKeySerializer() {
		return keySerializer;
	}


	/**
	 * @param keySerializer the keySerializer to set
	 */
	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}


	/**
	 * @return the valueSerializer
	 */
	public String getValueSerializer() {
		return valueSerializer;
	}


	/**
	 * @param valueSerializer the valueSerializer to set
	 */
	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

}
