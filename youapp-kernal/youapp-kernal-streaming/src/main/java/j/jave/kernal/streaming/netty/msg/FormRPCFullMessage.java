package j.jave.kernal.streaming.netty.msg;

import java.util.Map;

import j.jave.kernal.jave.support.parser.JDefaultSimpleDataParser;
import j.jave.kernal.streaming.netty.controller.MappingMeta;
import j.jave.kernal.streaming.netty.controller.MethodParamMeta;

public class FormRPCFullMessage extends SimpleRPCFullMessage {

	private JSONResponse response=new JSONResponse();
	
	private FormDecoder decoder=new FormDecoder();
	
	@Override
	public RPCMsgDecoder decoder() {
		return decoder;
	}
	
	@Override
	public RPCFullResponseWriter response() {
		return response;
	}
	
	
	private class FormDecoder implements RPCMsgDecoder<MappingMeta>{
		@Override
		public Object[] decode(MappingMeta mappingMeta) {
			MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
			Object[] params=new Object[methodParamMetas.length];
			Map<String, Object> content=(Map<String, Object>) content();
			for(int i=0;i<methodParamMetas.length;i++){
				MethodParamMeta meta=methodParamMetas[i];
				JDefaultSimpleDataParser dataParser=JDefaultSimpleDataParser.getDefault();
				params[i]=dataParser.parse(meta.getType(), content.get(meta.getName()));
			}
			return params;
		}
	}
}
