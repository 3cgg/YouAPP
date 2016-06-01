package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.support.parser.JParser;

public interface MethodParamParser extends JParser{

	/**
	 * resolve the method parameters
	 * @param controllerService
	 * @param mappingMeta
	 * @param data  the base64 string data
	 * @return
	 * @throws Exception
	 */
	Object[] parse(ControllerService controllerService,MappingMeta mappingMeta,String data) throws Exception;
	
}
