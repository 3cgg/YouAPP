package j.jave.kernal.container.scheme;

import java.net.URI;

public interface JSchemeProcessorServiceFactory<T extends JSchemeProcessorService>{

	public T getSchemeProcessorService();
	
	public boolean accept(URI uri);
	
}
