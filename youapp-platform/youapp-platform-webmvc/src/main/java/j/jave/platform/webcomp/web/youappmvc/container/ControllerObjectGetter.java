package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.data.web.mapping.MappingMeta;

/**
 * determine how to get the controller object.
 * 
 * @author J
 *
 */
public interface ControllerObjectGetter extends JService{

	Object getObjet(MappingMeta mappingMeta) throws Exception;
	
}
