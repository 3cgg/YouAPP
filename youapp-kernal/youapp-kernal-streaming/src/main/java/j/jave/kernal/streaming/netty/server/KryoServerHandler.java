package j.jave.kernal.streaming.netty.server;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.streaming.netty.Utils;
import j.jave.kernal.streaming.netty.controller.DefaultFastMessageMeta;

public class KryoServerHandler extends SimpleChannelInboundHandler<FullHttpMessage> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(KryoServerHandler.class);
	
	private static final String title="------------%s------------";
	
	public KryoServerHandler() {
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) {
    	HttpHeaders headers = msg.headers();
    	if(LOGGER.isDebugEnabled()){
    		StringBuffer stringBuffer=new StringBuffer();
            if (!headers.isEmpty()) {
            	stringBuffer.append(String.format(title, "REQUEST HEADER")).append("\r\n");
                for (Map.Entry<String, String> h: headers) {
                    String key = h.getKey();
                    String value = h.getValue();
                    stringBuffer.append(key).append(" = ").append(value).append("\r\n");
                }
                stringBuffer.append("\r\n");
            }
            LOGGER.debug(stringBuffer.toString());
    	}
    	
    	byte[] bytes=new byte[]{};
    	ByteBuf content=msg.content();
    	if(content.isReadable()){
    		bytes=new byte[content.capacity()];
    		content.readBytes(bytes);
    	}
    	
    	AsyncRequestExecutingEvent asyncRequestExecutingEvent=new AsyncRequestExecutingEvent(this);
    	asyncRequestExecutingEvent.setCtx(ctx);
    	asyncRequestExecutingEvent.setHttpObject(msg);
    	DefaultFastMessageMeta fastMessageMeta=new DefaultFastMessageMeta();
    	fastMessageMeta.setBytes(bytes);
    	asyncRequestExecutingEvent.setFastMessageMeta(fastMessageMeta);
    	fastMessageMeta.setUrl(Utils.controllerUri(headers));
    	JServiceHubDelegate.get().addDelayEvent(asyncRequestExecutingEvent);
		
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}