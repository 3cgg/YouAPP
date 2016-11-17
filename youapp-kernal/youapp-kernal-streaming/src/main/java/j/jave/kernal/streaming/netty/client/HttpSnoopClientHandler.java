package j.jave.kernal.streaming.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import j.jave.kernal.dataexchange.model.MessageMeta.MessageMetaNames;
import j.jave.kernal.eventdriven.servicehub.EventExecutionResult;
import j.jave.kernal.eventdriven.servicehub.JAsyncCallback;
import j.jave.kernal.eventdriven.servicehub.JEventExecution;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.sync.JSyncMonitorRegisterService;
import j.jave.kernal.jave.sync.JSyncMonitorWakeupEvent;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;

public class HttpSnoopClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static final JLogger logger=JLoggerFactory.getLogger(HttpSnoopClientHandler.class);
	
	private static JSyncMonitorRegisterService syncMonitorRegisterService
	=JServiceHubDelegate.get().getService(new Object(), JSyncMonitorRegisterService.class);
	
	private JDefaultHashCacheService hashCacheService=JServiceHubDelegate.get()
			.getService(this, JDefaultHashCacheService.class);
    
	
	private volatile String currentUnique; 
	
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpResponse) {
        	HttpResponse response = (HttpResponse) msg;
            ResponseContext responseContext=new ResponseContext();
            responseContext.setResponse(response);
            responseContext.setChannelHandlerContext(ctx);
            
        	String conversationId=response.headers().get(
            		MessageMetaNames.CONVERSATION_ID);
        	if(JStringUtils.isNullOrEmpty(conversationId)){
            	throw new IllegalStateException("the conversation id is is missing.");
            }
        	responseContext.setConversationId(conversationId);
        	
        	//generate new unique , avoid hiding actual request unique identifier. 
            currentUnique=JUniqueUtils.unique();
            responseContext.setReponseUnique(currentUnique);
            hashCacheService.putNeverExpired(currentUnique, responseContext);
        	
            responseContext.appendFullData("STATUS: " + response.status());
            responseContext.appendFullData("VERSION: " + response.protocolVersion());
            responseContext.appendFullData("\r\n");
            
            if (!response.headers().isEmpty()) {
                for (String name: response.headers().names()) {
                    for (String value: response.headers().getAll(name)) {
                        responseContext.appendFullData("HEADER: " + name + " = " + value);
                    }
                }
                responseContext.appendFullData("\r\n");
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                responseContext.appendFullData("CHUNKED CONTENT {");
            } else {
                responseContext.appendFullData("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ResponseContext responseContext=(ResponseContext) hashCacheService.get(currentUnique);
			
            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
            	String contentStr=content.toString(CharsetUtil.UTF_8);
            	if(JStringUtils.isNotNullOrEmpty(contentStr)){
            		responseContext.appendFullData(contentStr);
            		responseContext.appendRealData(contentStr);
        		}
            	responseContext.appendFullData("\r\n");
            }
            if (httpContent instanceof LastHttpContent) {
                responseContext.appendFullData("} END OF CONTENT");
                boolean isKeep=HttpUtil.isKeepAlive(responseContext.getResponse());
                if (!isKeep) {
					ctx.close();
				}
                
                if(logger.isDebugEnabled()){
                	logger.debug("response content(only include content): "+responseContext.getRealData());
                	logger.debug("full response content(include head/content): "+responseContext.getFullData());
                }
                
	            JSyncMonitorWakeupEvent syncMonitorWakeupEvent=
	            			new JSyncMonitorWakeupEvent(this, responseContext.getConversationId());
	            syncMonitorWakeupEvent.setData(responseContext);
	            syncMonitorWakeupEvent.addAsyncCallback(new JAsyncCallback() {
					
					@Override
					public void callback(EventExecutionResult result,
							JEventExecution eventExecution) {
						JSyncMonitorWakeupEvent event= (JSyncMonitorWakeupEvent) eventExecution.getEvent();
						hashCacheService.remove(((ResponseContext)event.getData()).getReponseUnique());
					}
				});
	            syncMonitorRegisterService.wakeup(syncMonitorWakeupEvent);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}