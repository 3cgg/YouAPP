package j.jave.kernal.streaming.netty.msg;

import java.util.Map;

import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller.MethodParamMeta;

public class FormRPCFullMessage extends SimpleRPCFullMessage {

	@Override
	public  FormDecoder decoder() {
		return new FormDecoder();
	}
	
	private class FormDecoder implements RPCMsgDecoder{
		@Override
		public Object[] decode(MappingMeta mappingMeta) {
			MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
			Object[] params=new Object[methodParamMetas.length];
			Map<String, Object> content=(Map<String, Object>) content();
			for(int i=0;i<methodParamMetas.length;i++){
				MethodParamMeta meta=methodParamMetas[i];
				params[i]=content.get(meta.getName());
			}
			return params;
		}
	}
}
