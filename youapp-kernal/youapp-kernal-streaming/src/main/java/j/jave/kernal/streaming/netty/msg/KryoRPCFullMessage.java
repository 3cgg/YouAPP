package j.jave.kernal.streaming.netty.msg;

import java.io.ByteArrayInputStream;

import j.jave.kernal.streaming.kryo._KryoSerializerFactoryGetter;
import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller.MethodParamMeta;
import me.bunny.kernel._c.serializer.JSerializerFactory;

public class KryoRPCFullMessage extends SimpleRPCFullMessage {

	private KryoDecoder decoder=new KryoDecoder();
	
	private KryoResponse response=new KryoResponse();
	
	@Override
	public KryoDecoder decoder() {
		return decoder;
	}
	
	@Override
	public RPCFullResponseWriter response() {
		return response;
	}
	
	private class KryoDecoder implements RPCMsgDecoder<MappingMeta>{
		
		private JSerializerFactory factory=_KryoSerializerFactoryGetter.get();
		
		@Override
		public Object[] decode(MappingMeta mappingMeta) {
			MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
			Object[] params=new Object[methodParamMetas.length];
			byte[] bytes=(byte[]) content();
			if(bytes!=null&&bytes.length>0){
				params= factory.newSerializer(Object.class).read(new ByteArrayInputStream(
						bytes),
						Object[].class);
			}
			return params;
		}
	}
	
	
	
	

}
