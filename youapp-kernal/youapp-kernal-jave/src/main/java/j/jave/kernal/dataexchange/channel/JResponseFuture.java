package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.exception.JDataExchangeException;

import java.io.IOException;



public interface JResponseFuture {
	
	/**
     * Returns a channel where the I/O operation associated with this
     * future takes place.
     */
    JChannel<?> channel();
    
    JResponseFuture await() throws InterruptedException,JDataExchangeException;
    
    /**
     * 
     * @return
     * @throws IOException
     * @throws JDataExchangeException
     */
    Object getResponse() throws JDataExchangeException;
    
}
