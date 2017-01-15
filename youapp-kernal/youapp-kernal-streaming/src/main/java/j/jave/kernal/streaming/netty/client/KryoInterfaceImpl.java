package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;

import j.jave.kernal.streaming.kryo._KryoSerializerFactoryGetter;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.msg.SimpleRPCFullResponse;
import j.jave.kernal.streaming.netty.server.ServerExecuteException;
import me.bunny.kernel.jave.serializer.SerializerUtils;
import me.bunny.kernel.jave.utils.JLangUtils;

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
