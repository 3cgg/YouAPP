package me.bunny.kernel.jave.service;

/**
 * all implementer can expose the concrete service namespace for any service.
 * @author JIAZJ
 * @see JServiceInterface
 */
public interface JServiceInterfaceProvider extends JService {

	public <T> Class<T> getServiceInterface() ;
	
}
