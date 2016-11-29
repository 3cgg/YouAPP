package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
import j.jave.kernal.streaming.kryo.KryoUtils;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.DefaultFastMessageMeta;

public class KryoInterfaceImpl<T extends ControllerService> extends InterfaceImpl<T> {

	public KryoInterfaceImpl(Class<T> intarface,ChannelExecutor<NioChannelRunnable> kryoChannelExecutor) {
		super(intarface, kryoChannelExecutor, new KryoSerializerFactory());
	}
	
	@Override
	protected Object deserialize(Object proxy, Method method, Object[] args, Object returnVal) {
		DefaultFastMessageMeta fastMessageMeta=(DefaultFastMessageMeta)returnVal;
		Class clazz=JClassUtils.load(fastMessageMeta.getClassName());
		if(!method.getReturnType().isAssignableFrom(clazz)){
			throw new RuntimeException("return object type["+
					clazz+"]is not applicable for "+method.getReturnType()+".");
		}
		return KryoUtils.deserialize(getFactory(), fastMessageMeta.bytes(), 
				JClassUtils.load(fastMessageMeta.getClassName()));
		
	}
	
}
