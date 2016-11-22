package j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.jave.support.parser.JParser;

public interface MethodParamParser extends JParser{

	/**
	 * resolve the method parameters
	 * @param controllerService
	 * @param mappingMeta
	 * @return
	 * @throws Exception
	 */
	Object[] parse(ControllerService controllerService,MappingMeta mappingMeta,FastMessageMeta fastMessageMeta) throws Exception;
	
}
