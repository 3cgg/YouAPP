package me.bunny.modular._p.streaming.netty.client;

import java.lang.reflect.Method;

import me.bunny.kernel._c.serializer.SerializerUtils;
import me.bunny.kernel._c.utils.JLangUtils;
import me.bunny.modular._p.streaming.kryo._KryoSerializerFactoryGetter;
import me.bunny.modular._p.streaming.netty.controller.ControllerService;
import me.bunny.modular._p.streaming.netty.msg.SimpleRPCFullResponse;
import me.bunny.modular._p.streaming.netty.server.ServerExecuteException;

public class KryoInterfaceImpl<T extends ControllerService> extends InterfaceImpl<T> {

	public KryoInterfaceImpl(Class<T> intarface,ChannelExecutor<NioChannelRunnable> kryoChannelExecutor) {
		super(intarface, kryoChannelExecutor, _KryoSerializerFactoryGetter.get());
	}
	
	@Override
	protected Object deserialize(Object proxy, Method method, Object[] args, Object returnVal) {
		SimpleRPCFullResponse simpleRPCFullResponse=(SimpleRPCFullResponse)returnVal;
		Object[] objects=SerializerUtils.deserialize(getFactory(), (byte[])simpleRPCFullResponse.content(),Object[].class);
		if(objects!=null&&objects.length>0){
			Object object=objects[0];
			if(object instanceof ServerExecuteException){
				throw (ServerExecuteException)object;
			}
			if(!JLangUtils.wrapper(method.getReturnType())
					.isAssignableFrom(JLangUtils.wrapper(object.getClass()))){
				throw new RuntimeException("return object type["+
						object.getClass()+"]is not applicable for "+method.getReturnType()+".");
			}
			return object;
		}else{
			throw new RuntimeException("data is invalid.");
		}
	}
	
	
	
}
