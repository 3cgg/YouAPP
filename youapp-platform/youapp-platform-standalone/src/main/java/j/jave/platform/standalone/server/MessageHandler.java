package j.jave.platform.standalone.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class MessageHandler extends ChannelInboundHandlerAdapter {

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
	    try {
	    	ReferenceCountUtil.retain(msg);
	        while (in.isReadable()) { 
	            System.out.print((char) in.readByte());
	            System.out.flush();
	        }
	    } finally {
	        ReferenceCountUtil.release(in);
	    }
    }
	
}
