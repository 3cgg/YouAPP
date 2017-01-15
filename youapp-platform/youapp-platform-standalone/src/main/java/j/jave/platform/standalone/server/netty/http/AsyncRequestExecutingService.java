package j.jave.platform.standalone.server.netty.http;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import me.bunny.kernel.dataexchange.model.MessageMeta.MessageMetaNames;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.service.JService;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class AsyncRequestExecutingService 
extends JServiceFactorySupport<AsyncRequestExecutingService>
implements JService , AsyncRequestExecutingListener,AsyncTestRequestExecutingListener
{
	private static final JLogger logger=JLoggerFactory.getLogger(AsyncRequestExecutingService.class);
	
	
	private ServerExecutorService serverExecutorService=JServiceHubDelegate.get()
			.getService(this, ServerExecutorService.class);
	
	@Override
	public Object trigger(AsyncRequestExecutingEvent event) {
		try{
			RequestContext requestContext=event.getRequestContext();
			byte[] bytes=null;
	    	try{
	    		Object object=serverExecutorService.execute(requestContext.getRealData().getBytes("utf-8"));
	    		bytes=JJSON.get().formatObject(object).getBytes("utf-8");
	    	}catch(Exception e){
	    		logger.error("-------------error----------\r\n"
	    				+"------real data----\r\n"+requestContext.getRealData()
						+"---------full content----\r\n"+requestContext.getFullData(), e);
	    		try {
					bytes=("exception:"+e.getMessage()).getBytes("utf-8");
				} catch (UnsupportedEncodingException e1) {
				}
	    	}
			writeResponse(event.getRequestContext(),bytes);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	@Override
	public Object trigger(AsyncTestRequestExecutingEvent event) {
		try{
			byte[] bytes=null;
			try{
				RequestContext requestContext=event.getRequestContext();
				bytes=requestContext.getFullData().getBytes("utf-8");
			}catch(Exception e){
				try {
					bytes=("exception:"+e.getMessage()).getBytes("utf-8");
				} catch (UnsupportedEncodingException e1) {
				}
			}
			writeResponse(event.getRequestContext(),bytes);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	private void writeResponse(RequestContext requestContext,byte[] bytes) {
		HttpRequest request=requestContext.getRequest();
		LastHttpContent lastHttpContent=requestContext.getLastHttpContent();
		ChannelHandlerContext ctx=requestContext.getChannelHandlerContext();
    	// Decide whether to close the connection or not.
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, lastHttpContent.decoderResult().isSuccess()? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(bytes));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(MessageMetaNames.CONVERSATION_ID, request.headers().get(MessageMetaNames.CONVERSATION_ID, 
        		MessageMetaNames.CONVERSATION_ID_MISSING));
        
        
        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        // Encode the cookie.
        String cookieString = request.headers().get(HttpHeaderNames.COOKIE);
        if (cookieString != null) {
            Set<io.netty.handler.codec.http.cookie.Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (io.netty.handler.codec.http.cookie.Cookie cookie: cookies) {
                    response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode("key1", "value1"));
            response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode("key2", "value2"));
        }

        // Write the response.
        ctx.write(response);
        
        if(!keepAlive){
        	ctx.write(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
        
        ctx.flush();
	}
	
	@Override
	protected AsyncRequestExecutingService doGetService() {
		return this;
	}
	
	
	
	

}
