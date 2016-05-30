package j.jave.kernal.dataexchange.protocol;

public interface JProtocolByteHandler {
	Object handle(byte[] bytes) throws Exception;
}
