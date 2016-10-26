package j.jave.kernal.streaming.kafka;

import java.io.Serializable;

/**
 * the abstract high level interface for all records recorded in the kafka
 * @author JIAZJ
 *
 */
public interface FetchObj extends Serializable {

	/**
	 * according to the offset in the kafka partition
	 * @return
	 */
	public long offset();
	
	/**
	 * the record time , generally the record is create at the time.
	 * @return
	 */
	public long recordTime();
	
	/**
	 * the unique id
	 * @return
	 */
	public String id();
	
	/**
	 * the record is partitioned according to the hash value;
	 * hash value% partition number
	 * @return
	 */
	public String hashKey();
	
}
