package j.jave.platform.standalone.client.netty.http;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpSnoopClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static JDefaultHashCacheService defaultHashCacheService=
			JServiceHubDelegate.get().getService(new Object(), JDefaultHashCacheService.class);
	
	private String data;
	
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
            HttpContent content = (HttpContent) msg;

            System.err.print(content.content().toString(CharsetUtil.UTF_8));
            System.err.flush();

            if (content instanceof LastHttpContent) {
                stringBuffer.append("} END OF CONTENT");
//                ctx.close();
            data=stringBuffer.toString();
            String requestId=response.headers().get("request-unique-id", "exception");
            defaultHashCacheService.putNeverExpired(requestId, stringBuffer.toString());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}