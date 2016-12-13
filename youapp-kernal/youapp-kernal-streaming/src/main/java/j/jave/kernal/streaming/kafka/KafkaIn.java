package j.jave.kernal.streaming.kafka;

public interface KafkaIn {

	/**
	 * according to the offset in the kafka partition
	 * @return
	 */
	long offset();

}