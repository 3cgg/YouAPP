package test.j.jave.kernal.streaming.netty;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelFuture;
import j.jave.kernal.streaming.netty.client.CallPromise;
import j.jave.kernal.streaming.netty.client.ChannelExecutors;
import j.jave.kernal.streaming.netty.client.ChannelResponseCall;
import j.jave.kernal.streaming.netty.client.DefaultCallPromise;
import j.jave.kernal.streaming.netty.client.NioChannelExecutor;
import j.jave.kernal.streaming.netty.client.NioChannelRunnable;
import j.jave.kernal.streaming.netty.client.Request;
import j.jave.kernal.streaming.netty.client.RequestMeta;

public class ExecutorTest {

	public static void main(String[] args) throws Exception {
		
		List<DefaultCallPromise> callPromises=new ArrayList<>();
		
		NioChannelExecutor channelExecutor=ChannelExecutors
					.newNioChannelExecutor("127.0.0.1", 8080);
		for(int i=0;i<100;i++){
			RequestMeta requestMeta=new RequestMeta();
			requestMeta.setContent(("test-"+i).getBytes(Charset.forName("utf-8")));
			requestMeta.setUrl("http://127.0.0.1:8080/DataRemote/admin/galaxy/index");
			Request request=Request.post(requestMeta);
			CallPromise<String> callPromise= channelExecutor.execute(new NioChannelRunnable(request,new ChannelResponseCall() {
				@Override
				public void run(Request request, Object object) {
					System.out.println("-------async---\r\n"+request.toString()+"\r\n"+object+"\r\n");
				}
			}));
			callPromises.add((DefaultCallPromise) callPromise);
			System.out.println(callPromise);
		}
		
		for(DefaultCallPromise defaultCallPromise:callPromises){
			ChannelFuture channelFuture=(ChannelFuture) defaultCallPromise.getFuture();
			System.out.println("CHANNEL : "+channelFuture.channel().hashCode()+"-hc]"+channelFuture.channel().toString());
		}
		
		Thread.sleep(100000);
		
	}
}
