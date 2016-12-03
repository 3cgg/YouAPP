package j.jave.kernal.streaming.netty.msg;

import java.io.ByteArrayInputStream;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller.MethodParamMeta;

public class KryoRPCFullMessage extends SimpleRPCFullMessage {

	private static JSerializerFactory factory=new KryoSerializerFactory();

	@Override
	public KryoDecoder decoder() {
		return new KryoDecoder();
	}
	
	private class KryoDecoder implements RPCMsgDecoder{
		
		
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
