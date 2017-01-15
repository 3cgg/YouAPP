package j.jave.platform.standalone.server.controller;

import java.util.Map;

import me.bunny.kernel.jave.base64.JBase64;
import me.bunny.kernel.jave.base64.JBase64FactoryProvider;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public class DefaultMethodParamParser implements MethodParamParser {

	private JLogger logger=JLoggerFactory.getLogger(DefaultMethodParamParser.class);
	
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
		
		String jsonString=new String(data,"UTF-8");
		
		if(logger.isDebugEnabled()){
			logger.debug("the data that is passed to the controller :"+jsonString);
		}
		Map map= JJSON.get().parse(jsonString);
		for(int i=0;i<methodParamMetas.length;i++){
			MethodParamMeta methodParamMeta=methodParamMetas[i];
			String name=methodParamMeta.getName();
			params[i]=map.get(name);
		}
		return params;
	}

}
