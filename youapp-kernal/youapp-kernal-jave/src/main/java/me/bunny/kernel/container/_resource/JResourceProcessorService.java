package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel._c.service.JService;

public interface JResourceProcessorService extends JService {

	public Object process(URI uri,Object object );
	
}
