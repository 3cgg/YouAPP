package j.jave.kernal.dataexchange.channel;


public class DefaultResponseFuture implements ResponseFuture {
	
	private final ExchangeChannel<?> exchangeChannel;
	
	private Object request;
	
	private Object response;
	
	public DefaultResponseFuture(ExchangeChannel<?> exchangeChannel) {
		this.exchangeChannel=exchangeChannel;
	}
	
	@Override
	public ExchangeChannel<?> channel() {
		return exchangeChannel;
	}
	
	@Override
	public ResponseFuture await() throws InterruptedException {
		for(;;){
			if(response==null){
				try{
					Thread.sleep(1000l);
				}catch(InterruptedException e){
					//ignore
				}
			}
			else{
				return this;
			}
		}
	}
	
	@Override
	public Object getResponse() throws Exception {
		return response;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
