package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.netty.controller.ControllerService;

public class SimpleInterfaceImplUtil {

	static DynamicChannelExecutor dynamicChannelExecutor=null;
	
	static{
		String znodePath=JConfiguration.get().getString(ConfigNames.STREAMING_LEADER_RPC_HOST_ZNODE);
		dynamicChannelExecutor=new DynamicChannelExecutor(znodePath) {
			@Override
			protected ChannelExecutor<NioChannelRunnable> doGetActive(String host, int port) throws Exception {
				return new KryoChannelExecutor(host,port);
			}
		}; 
	}
	
	public static <M extends ControllerService> M syncProxy(Class<M> controllerService ){
		KryoInterfaceImpl<M> intarface=
				new KryoInterfaceImpl<M>(controllerService, dynamicChannelExecutor);
		return intarface.syncProxy();
	}
	
	public static <M extends ControllerService> M syncProxy(Class<M> controllerService,ChannelExecutor<NioChannelRunnable> channelExecutor){
		KryoInterfaceImpl<M> intarface=
				new KryoInterfaceImpl<M>(controllerService, channelExecutor);
		return intarface.syncProxy();
	}
	
	public static <M extends ControllerService> M asyncProxy(Class<M> controllerService ){
		KryoInterfaceImpl<M> intarface=
				new KryoInterfaceImpl<M>(controllerService, dynamicChannelExecutor);
		return intarface.asyncProxy();
	}
	
	static final ThreadLocal<ControllerCallPromise> THREAD_LOCAL=new ThreadLocal<>();
	
	
	/**
	 * the method is together with {@link #asyncProxy(Class)}, like
	 * <p>IUnitController unitController=SimpleIntarfaceImplUtil.asyncProxy(IUnitController.class);
	 * <br/>SimpleInterfaceImplUtil.async(unitController.name("myname"))
	 * 												.addControllerAsyncCall(...)
	 * @param expression  like object.methed(args)
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <V> ControllerCallPromise<V> async(Object expression){
		try{
			ControllerCallPromise callPromise=THREAD_LOCAL.get();
			ControllerAsyncExecutor.get().execute(callPromise);
			return callPromise;
		}finally{
			THREAD_LOCAL.remove();
		}
		
	}
	

}
