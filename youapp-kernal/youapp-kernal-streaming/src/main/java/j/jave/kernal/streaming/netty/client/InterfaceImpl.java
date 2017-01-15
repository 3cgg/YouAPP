package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.util.Util;
import com.google.common.collect.Maps;

import j.jave.kernal.streaming.netty.HeaderNames;
import j.jave.kernal.streaming.netty.HeaderValues;
import j.jave.kernal.streaming.netty.controller.ClassProvidedMappingFinder;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller._Util;
import me.bunny.kernel.jave.aop.JAdvisedSupport;
import me.bunny.kernel.jave.aop.JJdkDynamicAopProxy;
import me.bunny.kernel.jave.aop.JSingletonTargetSource;
import me.bunny.kernel.jave.aop.JTargetSource;
import me.bunny.kernel.jave.proxy.JSimpleProxy;
import me.bunny.kernel.jave.serializer.JSerializerFactory;
import me.bunny.kernel.jave.serializer.SerializerUtils;
import net.sf.cglib.proxy.MethodProxy;

/**
 * interface proxy has two connection ways of async / sync related to the proxy object. 
 * And async object must be combined with {@link SimpleInterfaceImplUtil#asyncExecute(Object, ControllerAsyncCall)}
 * , {@link ControllerCallPromise} and {@link ControllerAsyncCall}
 * @author JIAZJ
 *
 * @param <T>
 * @see #asyncProxy()
 * @see #syncProxy()
 * @see ControllerCallPromise
 * @see SimpleInterfaceImplUtil
 */
public class InterfaceImpl<T extends ControllerService> {

	private final JSerializerFactory factory;
	
	private final ChannelExecutor<NioChannelRunnable> channelExecutor;
	
	private final Class<T> intarface;
	
	private Map<Method, MappingMeta> mappingMetas=Maps.newHashMap();

	public InterfaceImpl(Class<T> intarface,ChannelExecutor<NioChannelRunnable> channelExecutor,JSerializerFactory factory) {
		this.intarface = intarface;
		this.channelExecutor=channelExecutor;
		this.factory=factory;
		List<Class<?>> interfaces=new ArrayList<>();
		allInterfaces(intarface, interfaces);
		for(Class<?> intarfaceClass:interfaces){
			findOnAnnotation(intarfaceClass);
			findOnInterface(intarfaceClass);
		}
		
	}
	
	private void findOnInterface(Class<?> intarfaceClass) {
		ClassProvidedMappingFinder classProvidedMappingFinder
		=new ClassProvidedMappingFinder(intarfaceClass,ClassProvidedMappingFinder.INTERFACE);
		List<MappingMeta> mappingMetas= classProvidedMappingFinder
					.find().getMappingMetas();
		for(MappingMeta mappingMeta:mappingMetas){
			this.mappingMetas.put(mappingMeta.getMethod(), mappingMeta);
		}
	}

	private void findOnAnnotation(Class<?> intarfaceClass) {
		ClassProvidedMappingFinder classProvidedMappingFinder
		=new ClassProvidedMappingFinder(intarfaceClass);
		List<MappingMeta> mappingMetas= classProvidedMappingFinder
					.find().getMappingMetas();
		for(MappingMeta mappingMeta:mappingMetas){
			this.mappingMetas.put(mappingMeta.getMethod(), mappingMeta);
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
			requestMeta.setContent(SerializerUtils.serialize(factory, args));
			requestMeta.setUrl(uri()+mappingMeta.getPath());
			requestMeta.addHeader(HeaderNames.ENCODER_NAME
					, HeaderValues.ENCODER_KRYO);
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
				controllerCallPromise.setIntarfaceImpl(InterfaceImpl.this);
				SimpleInterfaceImplUtil.THREAD_LOCAL.set(controllerCallPromise);
				Class<?> wrapperType=Util.getWrapperClass(method.getReturnType());
				if(Byte.class==wrapperType
						||Short.class==wrapperType
						||Integer.class==wrapperType
						||Long.class==wrapperType
						||Float.class==wrapperType
						||Double.class==wrapperType
						){
					return 0;
				}else if(Boolean.class==wrapperType){
					return true;
				}
				return null;
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
