package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.streaming.netty.controller.ControllerService;

public class SimpleInterfaceImplUtil {

	static DynamicChannelExecutor dynamicChannelExecutor=new DynamicChannelExecutor() {
		@Override
		protected ChannelExecutor<NioChannelRunnable> doGetActive(String host, int port) throws Exception {
			return new KryoChannelExecutor(host,port);
		}
	}; 
	
	public static <M extends ControllerService> M syncProxy(Class<M> controllerService ){
		KryoInterfaceImpl<M> intarface=
				new KryoInterfaceImpl<M>(controllerService, dynamicChannelExecutor);
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
	 * <br/>SimpleIntarfaceImplUtil.async(unitController.name("myname"))
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
