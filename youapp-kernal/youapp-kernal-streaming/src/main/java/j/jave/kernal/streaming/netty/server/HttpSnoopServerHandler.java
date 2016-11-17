package j.jave.kernal.streaming.netty.server;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import j.jave.kernal.dataexchange.model.MessageMeta.MessageMetaNames;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.netty.RequestContext;

public class HttpSnoopServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final JLogger logger=JLoggerFactory.getLogger(HttpSnoopServerHandler.class);
	
	public HttpSnoopServerHandler() {
		logger.info("-----------initialize server handler-----"+this.hashCode());
	}
	
    private volatile String currentUnique;

    private JDefaultHashCacheService hashCacheService=JServiceHubDelegate.get()
			.getService(this, JDefaultHashCacheService.class);
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
    	
        if (msg instanceof HttpRequest) {
        	HttpRequest request = (HttpRequest) msg;

        	RequestContext requestContext=new RequestContext();
            requestContext.setRequest(request);
            requestContext.setChannelHandlerContext(ctx);
            
            String conversationId=request.headers().get(MessageMetaNames.CONVERSATION_ID);
            
            if(JStringUtils.isNullOrEmpty(conversationId)){
            	// FOR BROWSER REQUEST
            	conversationId=JUniqueUtils.unique();
            	requestContext.setOnlyTest(true);
            }
            requestContext.setConversationId(conversationId);
            
            currentUnique=JUniqueUtils.unique();
            requestContext.setRequestUnique(currentUnique);
            hashCacheService.putNeverExpired(currentUnique, requestContext);
            
            
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            
            requestContext.appendFullData("\r\nWELCOME TO THE WILD WILD WEB SERVER\r\n");
            requestContext.appendFullData("===================================\r\n");

            requestContext.appendFullData("VERSION: ")
            .appendFullData(request.protocolVersion().protocolName())
            .appendFullData("\r\n");
            
            requestContext.appendFullData("HOSTNAME: ")
            .appendFullData( request.headers().get(HttpHeaderNames.HOST, "unknown"))
            .appendFullData("\r\n");
            
            requestContext.appendFullData("REQUEST_URI: ")
            .appendFullData(request.uri())
            .appendFullData("\r\n\r\n");

            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> h: headers) {
                    String key = h.getKey();
                    String value = h.getValue();
                    requestContext.appendFullData("HEADER: ")
                    .appendFullData(key).appendFullData(" = ").appendFullData(value)
                    .appendFullData("\r\n");
                }
                requestContext.appendFullData("\r\n");
            }
            
            
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        requestContext.appendFullData("PARAM: ")
                        .appendFullData(key).appendFullData(" = ").appendFullData(val)
                        .appendFullData("\r\n");
                    }
                }
                requestContext.appendFullData("\r\n");
            }

            appendDecoderResult(requestContext, request);
        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            RequestContext requestContext=(RequestContext) hashCacheService.get(currentUnique);
			
            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
            	requestContext.appendFullData("CONTENT: \r\n");
            	String contentStr=content.toString(CharsetUtil.UTF_8);
            	if(JStringUtils.isNotNullOrEmpty(contentStr)){
            		requestContext.appendFullData(contentStr);
        			requestContext.appendRealData(contentStr);
        		}
                requestContext.appendFullData("\r\n");
                appendDecoderResult(requestContext, httpContent);
            }

            if (msg instanceof LastHttpContent) {
                requestContext.appendFullData("END OF CONTENT\r\n");

                LastHttpContent trailer = (LastHttpContent) msg;
                requestContext.setLastHttpContent(trailer);
                
                if (!trailer.trailingHeaders().isEmpty()) {
                    requestContext.appendFullData("\r\n");
                    for (String name: trailer.trailingHeaders().names()) {
                        for (String value: trailer.trailingHeaders().getAll(name)) {
                            requestContext.appendFullData("TRAILING HEADER: ");
                            requestContext.appendFullData(name).appendFullData(" = ").appendFullData(value)
                            .appendFullData("\r\n");
                        }
                    }
                    requestContext.appendFullData("\r\n");
                }
                
                if(logger.isDebugEnabled()){
                	logger.debug("requset content(only include content): "+requestContext.getRealData());
                	logger.debug("full requset content(include head/content): "+requestContext.getFullData());
                }
            }
        }
    }

    private static void appendDecoderResult(RequestContext requestContext, HttpObject o) {
        DecoderResult result = o.decoderResult();
        if (result.isSuccess()) {
            return;
        }

        requestContext.appendFullData(".. WITH DECODER FAILURE: ");
        requestContext.appendFullData(result.cause().getMessage());
        requestContext.appendFullData("\r\n");
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}