package me.bunny.kernel.dataexchange.channel;

import java.util.concurrent.atomic.AtomicBoolean;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.dataexchange.exception.JDataExchangeException;


public class JDefaultResponseFuture implements JResponseFuture {
	
	private final JChannel<?> exchangeChannel;
	
	private Object request;
	
	private Object response;
	
	private JMessageSendingEvent event;
	
	private Exception exception;
	
	private AtomicBoolean complete=new AtomicBoolean(false);
	
	public JDefaultResponseFuture(JChannel<?> exchangeChannel) {
		this.exchangeChannel=exchangeChannel;
	}
	
	@Override
	public JChannel<?> channel() {
		return exchangeChannel;
	}
	
	private static final int timeout=1000*JConfiguration.get().getInt(JProperties.SERVICE_CHANNEL_DATAE_XCHANGE_TIMEOUT, 3);

	// delay milliseconds
	private static final int delay=200;
	
	private static final int maxTryCount=timeout/delay;
	
	@Override
	public JResponseFuture await() throws InterruptedException,JDataExchangeException {
		int count=0;
		for(;;){
			
			if(complete.get()){
				if(exception!=null){
					throw new JDataExchangeException(exception);
				}
				break;
			}
			else{
				if(count>maxTryCount){
					throw new JDataExchangeException("the request is not replied,try it later. time(milliseconds): "+count*delay);
				}
				try{
					Thread.sleep(delay);
				}catch(InterruptedException e){
					//ignore
				}
				count++;
				continue;
			}
		}
		return this;
	}
	
	/*
	 * public ResponseFuture await() throws InterruptedException {
		for(;;){
			EventExecutionResult executionResult=null;
			try{
				executionResult=JServiceHubDelegate.get().getResultByEventId(event.getUnique());
			}catch(JEventExecutionException e){
				if(JEventExecutionException.EVENT_NOT_COMPLETE.equals(e.getCode())){
				}
				else{
					throw e;
				}
			}
			if(executionResult==null){
				try{
					Thread.sleep(1000l);
				}catch(InterruptedException e){
					//ignore
				}
				continue;
			}
			
			if(executionResult.getException()!=null){
				throw new RuntimeException(executionResult.getException());
			}
			Object[] objects=executionResult.getObjects();
			
			if(objects==null||objects.length==0){
				throw new RuntimeException("execute failly.");
			}
			this.response=objects[0];
			this.complete=new AtomicBoolean(true);
			break;
		}
		return this;
	}
	 */
	
	@Override
	public Object getResponse() throws JDataExchangeException {
		
		if(!complete.get()){
			throw new JDataExchangeException("the request is not replied,try it later");
		}
		
		return response;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	void setResponse(Object response) {
		this.response = response;
	}

	public JMessageSendingEvent getEvent() {
		return event;
	}

	public void setEvent(JMessageSendingEvent event) {
		this.event = event;
	}

	public AtomicBoolean getComplete() {
		return complete;
	}

	void setComplete(AtomicBoolean complete) {
		this.complete = complete;
	}

	public Exception getException() {
		return exception;
	}

	void setException(Exception exception) {
		this.exception = exception;
	}
	
}
