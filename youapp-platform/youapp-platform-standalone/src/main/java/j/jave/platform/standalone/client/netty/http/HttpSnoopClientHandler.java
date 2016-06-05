package j.jave.platform.standalone.client.netty.http;

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
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.sync.JSyncMonitorRegisterService;
import j.jave.kernal.jave.sync.JSyncMonitorWakeupEvent;
import j.jave.kernal.jave.utils.JStringUtils;

public class HttpSnoopClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	
	private String data;
	
	private static JSyncMonitorRegisterService syncMonitorRegisterService
	=JServiceHubDelegate.get().getService(new Object(), JSyncMonitorRegisterService.class);
	
	public String getData() {
		return data;
	}
	
	private StringBuffer stringBuffer=new StringBuffer();
	
	private HttpResponse response;
	
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpResponse) {
            response = (HttpResponse) msg;
            
            stringBuffer.append("STATUS: " + response.status());
            stringBuffer.append("VERSION: " + response.protocolVersion());
            stringBuffer.append("\r\n");
            
            if (!response.headers().isEmpty()) {
                for (String name: response.headers().names()) {
                    for (String value: response.headers().getAll(name)) {
                        stringBuffer.append("HEADER: " + name + " = " + value);
                    }
                }
                stringBuffer.append("\r\n");
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                stringBuffer.append("CHUNKED CONTENT {");
            } else {
                stringBuffer.append("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;

            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
            	String contentStr=content.toString(CharsetUtil.UTF_8);
            	if(JStringUtils.isNotNullOrEmpty(contentStr)){
        			data=contentStr;
        		}
            	stringBuffer.append(contentStr);
            	stringBuffer.append("\r\n");
            }
            if (httpContent instanceof LastHttpContent) {
                stringBuffer.append("} END OF CONTENT");
//                ctx.close();
            String requestId=response.headers().get(
            		MessageMetaNames.CONVERSATION_ID, MessageMetaNames.CONVERSATION_ID_MISSING);
            
            JSyncMonitorWakeupEvent syncMonitorWakeupEvent=new JSyncMonitorWakeupEvent(this, requestId);
            syncMonitorWakeupEvent.setData(data);
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