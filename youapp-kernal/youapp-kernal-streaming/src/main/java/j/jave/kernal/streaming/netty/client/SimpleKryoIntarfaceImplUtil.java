package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.streaming.netty.controller.ControllerService;

public class SimpleKryoIntarfaceImplUtil {

	static DynamicChannelExecutor dynamicChannelExecutor=new DynamicChannelExecutor() {
		@Override
		protected ChannelExecutor<NioChannelRunnable> doGetActive(String host, int port) throws Exception {
			return new KryoChannelExecutor(host,port);
		}
	}; 
	
	public static <M extends ControllerService> M syncProxy(Class<M> controllerService ){
		KryoIntarfaceImpl<M> intarface=
				new KryoIntarfaceImpl<M>(controllerService, dynamicChannelExecutor);
		return intarface.syncProxy();
	}
	
	public static <M extends ControllerService> M asyncProxy(Class<M> controllerService ){
		KryoIntarfaceImpl<M> intarface=
				new KryoIntarfaceImpl<M>(controllerService, dynamicChannelExecutor);
		return intarface.asyncProxy();
	}

}
