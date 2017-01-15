package me.bunny.kernel.container._resource;

import java.net.URI;

public interface JResourceProcessorServiceFactory<T extends JResourceProcessorService>{

	public T getSchemeProcessorService();
	
	public boolean accept(URI uri);
	
}
