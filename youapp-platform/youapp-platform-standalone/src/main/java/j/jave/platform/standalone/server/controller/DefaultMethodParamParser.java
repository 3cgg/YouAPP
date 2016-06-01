package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;

public class DefaultMethodParamParser implements MethodParamParser {

	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	@Override
	public final Object[] parse(ControllerService controllerService,
			MappingMeta mappingMeta, String data) throws Exception {
		
		return doParse(controllerService, mappingMeta, base64Service.decodeBase64(data));

	}
	
	protected Object[] doParse(ControllerService controllerService,
			MappingMeta mappingMeta, byte[] data) throws Exception{
		MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
		Object[] params=new Object[methodParamMetas.length];
		return params;
	}

}
