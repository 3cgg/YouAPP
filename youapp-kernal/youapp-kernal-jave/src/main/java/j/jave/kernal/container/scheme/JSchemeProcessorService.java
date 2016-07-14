package j.jave.kernal.container.scheme;

import j.jave.kernal.jave.service.JService;

import java.net.URI;

public interface JSchemeProcessorService extends JService {

	public Object process(URI uri);
	
}
