package test.j.jave.kernal.streaming.netty;

import java.nio.charset.Charset;

import io.netty.util.concurrent.Future;
import j.jave.kernal.streaming.netty.client.ChannelExecutors;
import j.jave.kernal.streaming.netty.client.NioChannelExecutor;
import j.jave.kernal.streaming.netty.client.NioChannelRunnable;
import j.jave.kernal.streaming.netty.client.Request;
import j.jave.kernal.streaming.netty.client.RequestMeta;

public class ExecutorTest {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		
		NioChannelExecutor channelExecutor=ChannelExecutors
					.newNioChannelExecutor("127.0.0.1", 8080);
		for(int i=0;i<10;i++){
			RequestMeta requestMeta=new RequestMeta();
			requestMeta.setContent(("test-"+i).getBytes(Charset.forName("utf-8")));
			requestMeta.setUrl("http://127.0.0.1:8080/DataRemote/admin/galaxy/index");
			Request request=Request.post(requestMeta);
			Future future=channelExecutor.execute(new NioChannelRunnable(request));
			System.out.println(future);
		}
		
		
		Thread.sleep(100000);
		
	}
}
