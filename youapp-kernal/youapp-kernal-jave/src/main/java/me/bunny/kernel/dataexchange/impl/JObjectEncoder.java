package me.bunny.kernel.dataexchange.impl;

public interface JObjectEncoder {
	
	byte[] encode(Object data) throws Exception;

}
