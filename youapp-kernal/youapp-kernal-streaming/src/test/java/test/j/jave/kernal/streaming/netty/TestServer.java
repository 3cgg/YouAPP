package test.j.jave.kernal.streaming.netty;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;

public class TestServer {
	
	private static JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();

	protected static JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	public static void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}

	public static void main(String[] args) throws Exception {
		initialize();
		SimpleHttpNioChannelServer channelServer =
				new SimpleHttpNioChannelServer(8080);
		try {
			channelServer.start();
		} catch (Exception e) {
			channelServer.close();
		}
		Thread.sleep(100000);

	}
}
