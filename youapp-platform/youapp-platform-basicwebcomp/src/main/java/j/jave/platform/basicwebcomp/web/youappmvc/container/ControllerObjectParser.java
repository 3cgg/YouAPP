package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.jave.support.parser.JParser;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;

/**
 * determine how to get the controller object.
 * 
 * @author J
 *
 */
public interface ControllerObjectParser extends JParser{

	Object parse(MappingMeta mappingMeta) throws Exception;
	
}
