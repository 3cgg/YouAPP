package j.jave.kernal.streaming.kafka;

public  class BaseFetchObj implements KafkaFetchObj {

	private String id;
	
	private long offset;
	
	private long recordTime;
	
	private String recordTimeStr;
	
	private String hashKey;
	
	private Notify notify;
	
	@Override
	public long offset() {
		return offset;
	}
	
	public void setOffset(long offset) {
		this.offset = offset;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public long getOffset() {
		return offset;
	}

	@Override
	public long recordTime() {
		return recordTime;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}
	@Override
	public String id() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String hashKey() {
		return hashKey;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getRecordTimeStr() {
		return recordTimeStr;
	}

	public void setRecordTimeStr(String recordTimeStr) {
		this.recordTimeStr = recordTimeStr;
	}
	
}
