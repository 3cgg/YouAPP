package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.exception.JDataExchangeException;

import java.io.IOException;



public interface ResponseFuture {
	
	/**
     * Returns a channel where the I/O operation associated with this
     * future takes place.
     */
    Channel<?> channel();
    
    ResponseFuture await() throws InterruptedException,JDataExchangeException;
    
    /**
     * 
     * @return
     * @throws IOException
     * @throws JDataExchangeException
     */
    Object getResponse() throws JDataExchangeException;
    
}
