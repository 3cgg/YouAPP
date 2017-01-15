package me.bunny.modular._p.streaming.netty.client;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.modular._p.streaming.netty.msg.SimpleRPCFullResponse;

@Sharable
public class KryoClientHandler 
	extends SimpleChannelInboundHandler<FullHttpMessage> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(KryoClientHandler.class);
	
	public KryoClientHandler() {
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) {
    	HttpHeaders headers = msg.headers();
    	if(LOGGER.isDebugEnabled()){
    		String title="------------------------%s------------------------";
    		StringBuffer stringBuffer=new StringBuffer();
            if (!headers.isEmpty()) {
            	stringBuffer.append(String.format(title, "RESPONSE HEADER")).append("\r\n");
                for (Map.Entry<String, String> h: headers) {
                    String key = h.getKey();
                    String value = h.getValue();
                    stringBuffer.append(key).append(" = ").append(value).append("\r\n");
                }
                stringBuffer.append("\r\n");
            }
            LOGGER.debug(stringBuffer.toString());
    	}
    	ByteBuf content=msg.content();
    	byte[] bytes=new byte[]{};
    	if(content.isReadable()){
    		bytes=new byte[content.capacity()];
    		content.readBytes(bytes);
    	}
    	
    	SimpleRPCFullResponse simpleRPCFullResponse=new SimpleRPCFullResponse();
    	simpleRPCFullResponse.setContent(bytes);
    	ctx.fireChannelRead(simpleRPCFullResponse);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	try{
    		cause.printStackTrace();
            ctx.close();
    	}finally{
    		super.exceptionCaught(ctx, cause);
    	}
    }
}