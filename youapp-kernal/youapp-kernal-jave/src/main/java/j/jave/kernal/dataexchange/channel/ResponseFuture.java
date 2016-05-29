package j.jave.kernal.dataexchange.channel;



public interface ResponseFuture {
	
	/**
     * Returns a channel where the I/O operation associated with this
     * future takes place.
     */
    ExchangeChannel<?> channel();
    
    ResponseFuture await() throws InterruptedException;
    
    Object getResponse() throws Exception;
    
}
