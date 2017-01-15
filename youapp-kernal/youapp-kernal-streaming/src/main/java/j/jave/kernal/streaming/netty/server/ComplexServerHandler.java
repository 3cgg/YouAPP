package j.jave.kernal.streaming.netty.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import j.jave.kernal.streaming.netty.HeaderNames;
import j.jave.kernal.streaming.netty.HeaderValues;
import j.jave.kernal.streaming.netty.msg.FormRPCFullMessage;
import j.jave.kernal.streaming.netty.msg.KryoRPCFullMessage;
import j.jave.kernal.streaming.netty.msg.RPCFullMessage;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public class ComplexServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ComplexServerHandler.class);
	
	private static final String title="------------%s------------";
	
	public ComplexServerHandler() {
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
    	
    	if(LOGGER.isDebugEnabled()){
    		LOGGER.debug("access to url : "+msg.uri());
    	}
    	
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
    	
    	AsyncRequestExecutingEvent asyncRequestExecutingEvent=new AsyncRequestExecutingEvent(this);
    	asyncRequestExecutingEvent.setCtx(ctx);
    	asyncRequestExecutingEvent.setHttpObject(msg);
    	RPCFullMessage fullMessage=null;
    	String encoderName=null;
    	if((encoderName=headers.get(HeaderNames.ENCODER_NAME))!=null){
    		if(HeaderValues.ENCODER_KRYO.equals(encoderName)){
    			byte[] bytes=new byte[]{};
    	    	ByteBuf content=msg.content().copy();
    	    	if(content.isReadable()){
    	    		bytes=new byte[content.capacity()];
    	    		content.readBytes(bytes);
    	    	}
    			fullMessage=new KryoRPCFullMessage();
    			fullMessage.setContent(bytes);
    		}else if(HeaderValues.ENCODER_JSON.equals(encoderName)){
    			
    		}
    	}else{
    		Map<String, Object> content=Maps.newHashMap();
    		
    		if(HttpMethod.GET==msg.method()){
    			QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());
    			for(Entry<String, List<String>> entry:decoder.parameters().entrySet()){
    				List<String> values=entry.getValue();
                	content.put(entry.getKey(), values.size()>1?values.toArray(new String[]{}):values.get(0));
    			}
    		}else if(HttpMethod.POST==msg.method()){
    			HttpPostRequestDecoder httpPostRequestDecoder=new HttpPostRequestDecoder(msg);
            	while(httpPostRequestDecoder.hasNext()){
            		InterfaceHttpData interfaceHttpData= httpPostRequestDecoder.next();
            		if (interfaceHttpData.getHttpDataType() == HttpDataType.Attribute) {  
                        Attribute attribute = (Attribute) interfaceHttpData;  
                        try {
    						content.put(attribute.getName(), attribute.getValue());
    					} catch (IOException e) {
    					}
                    }  
            	}
    		}
    		fullMessage=new FormRPCFullMessage();
    		fullMessage.setContent(content);
    	}
    	try {
			fullMessage.setUri(new java.net.URI(msg.uri()).getPath());
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage(), e);
		}
    	asyncRequestExecutingEvent.setRpcFullMessage(fullMessage);
    	JServiceHubDelegate.get().addDelayEvent(asyncRequestExecutingEvent);
		
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}