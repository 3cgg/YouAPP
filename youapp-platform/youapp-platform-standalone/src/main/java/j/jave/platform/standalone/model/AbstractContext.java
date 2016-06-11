package j.jave.platform.standalone.model;


import io.netty.channel.ChannelHandlerContext;
import j.jave.kernal.jave.model.JModel;

public class AbstractContext implements JModel{
	
	private transient ChannelHandlerContext channelHandlerContext;
	
	/**
	 * only include content information.
	 */
	private String realData="";
	
	/**
	 * include head/content information.
	 */
	private String fullData="";
	
	/**
	 * the request-response unique identifier. 
	 */
	private String conversationId;
	
	private boolean onlyTest; 
	
	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}

	/**
	 * {@link #realData}
	 * @return
	 */
	public String getRealData() {
		return realData;
	}

	public void setRealData(String realData) {
		this.realData = realData;
	}

	/**
	 * {@link #fullData}
	 * @return
	 */
	public String getFullData() {
		return fullData;
	}

	public void setFullData(String fullData) {
		this.fullData = fullData;
	}
	
	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public AbstractContext appendRealData(String realData){
		this.realData=this.realData+realData;
		return this;
	}
	
	public AbstractContext appendFullData(String fullData){
		this.fullData=this.fullData+fullData;
		return this;
	}

	public boolean isOnlyTest() {
		return onlyTest;
	}

	public AbstractContext setOnlyTest(boolean onlyTest) {
		this.onlyTest = onlyTest;
		return this;
	}
	
	
}
