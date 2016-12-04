package j.jave.kernal.streaming.netty.server;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpUtil;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.streaming.netty.msg.RPCFullMessage;

public class AsyncRequestExecutingService 
extends JServiceFactorySupport<AsyncRequestExecutingService>
implements JService , AsyncRequestExecutingListener{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(AsyncRequestExecutingService.class);
	
	private ServerExecutorService serverExecutorService=JServiceHubDelegate.get()
			.getService(this, ServerExecutorService.class);
	
	@Override
	public Object trigger(AsyncRequestExecutingEvent event) {
		try{
//			String className=null;
			RPCFullMessage rpcFullMessage=event.getRpcFullMessage();
	    	try{
	    		Object obj=serverExecutorService.execute(
	    				rpcFullMessage
	    				,event.getCtx()
	    				,event.getHttpObject());
	    		rpcFullMessage.response().offer(obj);
//	    		className=obj.getClass().getName();
//	    		if(obj instanceof byte[]){
//	    			bytes=(byte[]) obj;
//	    		}
//	    		else if(obj instanceof String){
//	    			bytes=String.valueOf(obj).getBytes(Charset.forName("utf-8"));
//	    		}
//	    		else{
//	    			bytes=(byte[]) rpcFullMessage.response().offer(obj).get();
//	    		}
	    		if(LOGGER.isDebugEnabled()){
	    			LOGGER.debug(obj);
	    		}
	    	}catch(Exception e){
	    		ServerExecuteException exception=new ServerExecuteException(ErrorCode.E0001,e);
	    		rpcFullMessage.response().offer(exception);
//	    		className=ServerExecuteException.class.getName();
	    	}
			writeResponse(event.getCtx()
    				,event.getHttpObject()
    				,rpcFullMessage);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	private void writeResponse(ChannelHandlerContext ctx,Object object
			,RPCFullMessage rpcFullMessage) {
		HttpObject httpObject=(HttpObject) object;
		// Decide whether to close the connection or not.
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, httpObject.decoderResult().isSuccess()? OK : BAD_REQUEST,
                Unpooled.copiedBuffer((byte[]) rpcFullMessage.response().get()));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE,
        		"text/plain; charset=UTF-8");

        // Add 'Content-Length' header only for a keep-alive connection.
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // Add keep alive header as per:
        // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
        boolean keepAlive=HttpUtil.isKeepAlive((HttpMessage) httpObject);
        if(keepAlive){
        	response.headers().set(HttpHeaderNames.CONNECTION, 
            		HttpHeaderValues.KEEP_ALIVE);
        }else{
        	response.headers().set(HttpHeaderNames.CONNECTION, 
            		HttpHeaderValues.CLOSE);
        }
        // Write the response.
        ctx.writeAndFlush(response);
        if(!keepAlive){
        	ctx.close();
        }
	}
	
	@Override
	protected AsyncRequestExecutingService doGetService() {
		return this;
	}
	
	
	
	

}
