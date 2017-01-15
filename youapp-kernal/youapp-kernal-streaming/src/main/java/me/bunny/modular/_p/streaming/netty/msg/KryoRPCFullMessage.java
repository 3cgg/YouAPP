package me.bunny.modular._p.streaming.netty.msg;

import java.io.ByteArrayInputStream;

import me.bunny.kernel._c.serializer.JSerializerFactory;
import me.bunny.modular._p.streaming.kryo._KryoSerializerFactoryGetter;
import me.bunny.modular._p.streaming.netty.controller.MappingMeta;
import me.bunny.modular._p.streaming.netty.controller.MethodParamMeta;

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
