package me.bunny.modular._p.streaming.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;

import me.bunny.kernel._c.utils.JObjectUtils;

@SuppressWarnings("serial")
public class KafkaProducerConfig extends KafkaConfig{
	
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
	
	
	public static KafkaProducerConfig build(Map conf){
		KafkaConfig kafkaConfig=KafkaConfig.build(conf);
		KafkaProducerConfig producerConfig=JObjectUtils.simpleCopy(kafkaConfig, KafkaProducerConfig.class);
		producerConfig.setAcks(String.valueOf(conf.get("acks")));
		producerConfig.setBatchSize(Integer.valueOf(String.valueOf(conf.get("batch.size"))));
		producerConfig.setBufferMemory(Integer.valueOf(String.valueOf(conf.get("buffer.memory"))));
		producerConfig.setLingerMs(Integer.valueOf(String.valueOf(conf.get("linger.ms"))));
		producerConfig.setRetries(Integer.valueOf(String.valueOf(conf.get("retries"))));
		producerConfig.setKeySerializer(String.valueOf(conf.get("key.serializer")));
		producerConfig.setValueSerializer(String.valueOf(conf.get("value.serializer")));
		return producerConfig;
	}
	
	public static Map<String, Object> def(){
		Map<String, Object> conf=new HashMap<>();
		conf.put("acks", "all");
		conf.put("batch.size", 16384);
		conf.put("buffer.memory", 33554432);
		conf.put("linger.ms", 1);
		conf.put("retries", 0);
		conf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		conf.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		return conf;
	}
	

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
