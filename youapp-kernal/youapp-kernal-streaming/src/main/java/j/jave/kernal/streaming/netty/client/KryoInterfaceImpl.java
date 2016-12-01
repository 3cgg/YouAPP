package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JLangUtils;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
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
		if(!JLangUtils.wrapper(method.getReturnType())
				.isAssignableFrom(JLangUtils.wrapper(clazz))){
			throw new RuntimeException("return object type["+
					clazz+"]is not applicable for "+method.getReturnType()+".");
		}
		return SerializerUtils.deserialize(getFactory(), fastMessageMeta.bytes(), 
				JClassUtils.load(fastMessageMeta.getClassName()));
		
	}
	
	
	
}
