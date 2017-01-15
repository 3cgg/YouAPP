package me.bunny.kernel.dataexchange.channel;

import java.io.IOException;

import me.bunny.kernel.dataexchange.exception.JDataExchangeException;



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
