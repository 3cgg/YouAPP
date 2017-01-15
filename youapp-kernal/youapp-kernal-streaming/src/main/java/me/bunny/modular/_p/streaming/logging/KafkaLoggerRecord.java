package me.bunny.modular._p.streaming.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.bunny.modular._p.streaming.kafka.BaseFetchObj;
import me.bunny.modular._p.streaming.kafka.Notify;

public class KafkaLoggerRecord extends BaseFetchObj {

	private Object message;
	
	private Throwable error;
	
	public KafkaLoggerRecord(Object message) {
		this(message,null);
	}
	
	public KafkaLoggerRecord(Object message, Throwable error) {
		this.message = message;
		this.error = error;
	}

	public Object getMessage() {
		return message;
	}
	
	public Throwable getError() {
		return error;
	}
	
	@JsonIgnore
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	@JsonIgnore
	@Override
	public long getRecordTime() {
		return super.getRecordTime();
	}
	
	@JsonIgnore
	@Override
	public long getOffset() {
		// TODO Auto-generated method stub
		return super.getOffset();
	}
	
	@JsonIgnore
	@Override
	public Notify getNotify() {
		return super.getNotify();
	}
	
	
}
