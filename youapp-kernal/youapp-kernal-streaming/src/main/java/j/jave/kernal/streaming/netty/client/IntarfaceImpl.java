package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.aop.JAdvisedSupport;
import j.jave.kernal.jave.aop.JJdkDynamicAopProxy;
import j.jave.kernal.jave.aop.JSingletonTargetSource;
import j.jave.kernal.jave.aop.JTargetSource;
import j.jave.kernal.jave.proxy.JSimpleProxy;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.streaming.kryo.KryoUtils;
import j.jave.kernal.streaming.netty.controller.ClassProvidedMappingFinder;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.IControllerImplementer;
import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller._Util;
import net.sf.cglib.proxy.MethodProxy;

/**
 * interface proxy has two connection ways of async / sync related to the proxy object. 
 * And async object must be combined with {@link SimpleIntarfaceImplUtil#asyncExecute(Object, ControllerAsyncCall)}
 * , {@link ControllerCallPromise} and {@link ControllerAsyncCall}
 * @author JIAZJ
 *
 * @param <T>
 * @see #asyncProxy()
 * @see #syncProxy()
 * @see ControllerCallPromise
 * @see SimpleIntarfaceImplUtil
 */
public class IntarfaceImpl<T extends ControllerService> {

	private final JSerializerFactory factory;
	
	private final ChannelExecutor<NioChannelRunnable> channelExecutor;
	
	private final Class<T> intarface;
	
	private Map<Method, MappingMeta> mappingMetas=Maps.newHashMap();

	public IntarfaceImpl(Class<T> intarface,ChannelExecutor<NioChannelRunnable> channelExecutor,JSerializerFactory factory) {
		this.intarface = intarface;
		this.channelExecutor=channelExecutor;
		this.factory=factory;
		List<Class<?>> interfaces=new ArrayList<>();
		allInterfaces(intarface, interfaces);
		for(Class<?> intarfaceClass:interfaces){
			ClassProvidedMappingFinder classProvidedMappingFinder
			=new ClassProvidedMappingFinder(intarfaceClass);
			List<MappingMeta> mappingMetas= classProvidedMappingFinder
						.find().getMappingMetas();
			for(MappingMeta mappingMeta:mappingMetas){
				this.mappingMetas.put(mappingMeta.getMethod(), mappingMeta);
			}
		}
		
	}
	
	private void allInterfaces(Class<?> clazz,List<Class<?>> interfaces){
		_Util.allInterfaces(clazz, interfaces);
	}
	
	private String uri(){
		return channelExecutor.uri();
	}
	
	private Request get(Object proxy, Method method, Object[] args) throws Exception{
		if(method.getDeclaringClass()==intarface
				||(method.getDeclaringClass()!=Object.class
						&&method.getDeclaringClass().isAssignableFrom(intarface)
				)){
			Method iMethod=intarface.getMethod(method.getName(), method.getParameterTypes());
			MappingMeta mappingMeta=mappingMetas.get(iMethod);
			
			RequestMeta requestMeta=new RequestMeta();
			if(args==null) args=new Object[]{};
			requestMeta.setContent(KryoUtils.serialize(factory, args));
			requestMeta.setUrl(uri()+mappingMeta.getPath());
			Request request=Request.post(requestMeta);
			return request;
		}
		throw new RuntimeException("method["+method.getName()
		+"] not declared in the interface["+intarface+"]");
		
	}
	
	protected Object deserialize(Object proxy, Method method, Object[] args,Object returnVal){
		return returnVal;
	}
	
	
	public T syncProxy(){
		JAdvisedSupport advisedSupport=new JAdvisedSupport();
		JTargetSource targetSource=new JSingletonTargetSource(syncProxy0());
		advisedSupport.setTargetSource(targetSource);
		List<Class<?>> classes=new ArrayList<Class<?>>();
		classes.add(intarface);
		advisedSupport.setInterfaces(classes.toArray(new Class<?>[]{}));
		JJdkDynamicAopProxy jdkDynamicAopProxy=new JJdkDynamicAopProxy(advisedSupport);
		return (T) jdkDynamicAopProxy.getProxy();
	}

	private Object syncProxy0() {
		return JSimpleProxy.proxy(this, intarface, new JSimpleProxy.Callback() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Request request=get(proxy, method, args);
				CallPromise<Object> callPromise= channelExecutor
							.execute(new NioChannelRunnable(request));
				Object object=callPromise.get();
				return deserialize(proxy, method, args, object);
			}
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return null;
			}
		});
	}
	
	public T asyncProxy(){
		JAdvisedSupport advisedSupport=new JAdvisedSupport();
		JTargetSource targetSource=new JSingletonTargetSource(asyncProxy0());
		advisedSupport.setTargetSource(targetSource);
		List<Class<?>> classes=new ArrayList<Class<?>>();
		classes.add(intarface);
		advisedSupport.setInterfaces(classes.toArray(new Class<?>[]{}));
		JJdkDynamicAopProxy jdkDynamicAopProxy=new JJdkDynamicAopProxy(advisedSupport);
		return (T) jdkDynamicAopProxy.getProxy();
	}

	private Object asyncProxy0() {
		return JSimpleProxy.proxy(this, intarface, new JSimpleProxy.Callback() {
			@Override
			public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
				Request request=get(proxy, method, args);
				CallPromise<Object> callPromise= channelExecutor
							.execute(new NioChannelRunnable(request));
				ControllerCallPromise<Object> controllerCallPromise=new ControllerCallPromise<>(callPromise);
				controllerCallPromise.setProxy(proxy);
				controllerCallPromise.setMethod(method);
				controllerCallPromise.setArgs(args);
				controllerCallPromise.setIntarfaceImpl(IntarfaceImpl.this);
				return controllerCallPromise;
			}
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return null;
			}
		});
	}
	
	protected JSerializerFactory getFactory() {
		return factory;
	}
	
	
	
}
