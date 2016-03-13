package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public abstract class JProtocolReceiver {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	protected final byte[] bytes;
	
	public JProtocolReceiver(byte[] bytes) {
		super();
		this.bytes = bytes;
	}

	public JObjectTransModel receive() {
		try{
			JObjectTransModel objectTransModel=doReceive();
			return objectTransModel;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JDataExchangeException(e);
		}
	}

	protected abstract JObjectTransModel doReceive() throws JDataExchangeException;
	
	static class ObjectProtocolReceiver extends JProtocolReceiver{
		
		public ObjectProtocolReceiver(byte[] bytes) {
			super(bytes);
		}

		@Override
		protected JObjectTransModel doReceive() throws JDataExchangeException{
			JObjectTransModel objectTransModel=JObjectSerializableUtils.deserialize(bytes, JObjectTransModel.class);
			return objectTransModel;
		}
		
	} 
	
}
