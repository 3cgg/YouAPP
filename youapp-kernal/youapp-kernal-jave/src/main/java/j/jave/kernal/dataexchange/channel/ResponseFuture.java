package j.jave.kernal.dataexchange.channel;



public interface ResponseFuture {
	
	public static final String COMPLETE="COMPLETE";
	
	/**
     * Returns a channel where the I/O operation associated with this
     * future takes place.
     */
    Channel<?> channel();
    
    ResponseFuture await() throws InterruptedException;
    
    Object getResponse() throws Exception;
    
}
