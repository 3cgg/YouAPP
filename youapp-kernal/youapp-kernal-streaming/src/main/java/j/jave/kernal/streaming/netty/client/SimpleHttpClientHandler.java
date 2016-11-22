package j.jave.kernal.streaming.netty.client;

import java.nio.charset.Charset;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

public class SimpleHttpClientHandler extends SimpleChannelInboundHandler<FullHttpMessage> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(SimpleHttpClientHandler.class);
	
	private static final String title="------------------------%s------------------------";
	
	public SimpleHttpClientHandler() {
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) {
    	StringBuffer stringBuffer=new StringBuffer();
    	HttpHeaders headers = msg.headers();
        if (!headers.isEmpty()) {
        	stringBuffer.append(String.format(title, "RESPONSE HEADER")).append("\r\n");
            for (Map.Entry<String, String> h: headers) {
                String key = h.getKey();
                String value = h.getValue();
                stringBuffer.append(key).append(" = ").append(value).append("\r\n");
            }
            stringBuffer.append("\r\n");
        }
    	
    	ByteBuf content=msg.content();
    	if(content.isReadable()){
    		stringBuffer.append(String.format(title, " SERVER CONTENT")).append("\r\n");
    		byte[] bytes=new byte[content.capacity()];
    		content.readBytes(bytes);
    		stringBuffer.append(new String(bytes,Charset.forName("utf-8"))+"\r\n");
    	}
        if(LOGGER.isDebugEnabled()){
        	LOGGER.debug(stringBuffer.toString());
        }
    	ctx.fireChannelRead(stringBuffer);
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