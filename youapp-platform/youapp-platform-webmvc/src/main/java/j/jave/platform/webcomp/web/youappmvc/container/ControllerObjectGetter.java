package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.data.web.mapping.MappingMeta;
import me.bunny.kernel.jave.service.JService;

/**
 * determine how to get the controller object.
 * 
 * @author J
 *
 */
public interface ControllerObjectGetter extends JService{

	Object getObjet(MappingMeta mappingMeta) throws Exception;
	
}
