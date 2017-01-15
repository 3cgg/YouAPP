package me.bunny.kernel.dataexchange.model;

public interface MessageMeta {
	
	public static interface MessageMetaNames{
		String CONVERSATION_ID="CONVERSATION_ID";
		String CONVERSATION_ID_MISSING="CONVERSATION_ID_MISSING";
		
	}
	
	public String url();
	
	/**
	 * get the base64 String
	 * @return the base64 string
	 */
	public String data();
	
	/**
	 * the encoder that encode some object to the {@link #data()} bytes.
	 * @return
	 */
	public String dataEncoder();
	
	
}
