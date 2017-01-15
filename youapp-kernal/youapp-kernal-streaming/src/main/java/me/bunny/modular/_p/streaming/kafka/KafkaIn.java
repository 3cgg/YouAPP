package me.bunny.modular._p.streaming.kafka;

public interface KafkaIn {

	/**
	 * according to the offset in the kafka partition
	 * @return
	 */
	long offset();

}