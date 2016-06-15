package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.jave.support.parser.JParser;
import j.jave.platform.data.web.mapping.MappingMeta;

/**
 * determine how to get the controller object.
 * 
 * @author J
 *
 */
public interface ControllerObjectParser extends JParser{

	Object parse(MappingMeta mappingMeta) throws Exception;
	
}
