package j.jave.kernal.dataexchange.modelprotocol;

public interface JObjectEncoder {
	
	byte[] encode(Object data) throws Exception;

}
