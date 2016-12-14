package test.j.jave.kernal.streaming.netty;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelFuture;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
import j.jave.kernal.streaming.netty.client.CallPromise;
import j.jave.kernal.streaming.netty.client.ChannelResponseCall;
import j.jave.kernal.streaming.netty.client.DefaultCallPromise;
import j.jave.kernal.streaming.netty.client.DefaultCallPromise._DefaultCallPromiseUtil;
import j.jave.kernal.streaming.netty.client.KryoChannelExecutor;
import j.jave.kernal.streaming.netty.client.NioChannelRunnable;
import j.jave.kernal.streaming.netty.client.Request;
import j.jave.kernal.streaming.netty.client.RequestMeta;

public class ExecutorTest {

	public static void main(String[] args) throws Exception {
		final JSerializerFactory factory=new KryoSerializerFactory();
		
		List<CallPromise<byte[]>> callPromises=new ArrayList<>();
		
		KryoChannelExecutor channelExecutor=new KryoChannelExecutor("127.0.0.1", 8080);
		for(int i=0;i<100;i++){
			RequestMeta requestMeta=new RequestMeta();
			requestMeta.setContent(SerializerUtils.serialize(factory, new Object[]{"test-"+i}));
			requestMeta.setUrl("http://127.0.0.1:8080/unit/name");
			Request request=Request.post(requestMeta);
			CallPromise<byte[]> callPromise= channelExecutor.execute(new NioChannelRunnable(request,new ChannelResponseCall() {
				@Override
				public void run(Request request, Object object) {
					System.out.println("-------async-call---\r\n"+request.toString()+"\r\n"
				+SerializerUtils.deserialize(factory, (byte[])object, String.class)+"\r\n");
				}
			}));
			callPromises.add(callPromise);
			System.out.println("------------response-------------\r\n"+
					SerializerUtils.deserialize(factory,callPromise.get(), String.class)
			);
		}
		
		for(CallPromise<byte[]> callPromise:callPromises){
			ChannelFuture channelFuture=(ChannelFuture) _DefaultCallPromiseUtil.getChannelFuture((DefaultCallPromise<?>) callPromise);
			System.out.println("CHANNEL : "+channelFuture.channel().hashCode()+"-hc]"+channelFuture.channel().toString());
		}
		
		Thread.sleep(100000);
		
	}
}
