package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.kernel._c.service.JService;

/**
 * determine how to get the controller object.
 * 
 * @author J
 *
 */
public interface ControllerObjectGetter extends JService{

	Object getObjet(MappingMeta mappingMeta) throws Exception;
	
}
