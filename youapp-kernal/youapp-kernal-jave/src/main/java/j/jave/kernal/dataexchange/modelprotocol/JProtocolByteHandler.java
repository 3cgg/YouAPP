package j.jave.kernal.dataexchange.modelprotocol;

public interface JProtocolByteHandler {
	Object handle(byte[] bytes) throws Exception;
}
