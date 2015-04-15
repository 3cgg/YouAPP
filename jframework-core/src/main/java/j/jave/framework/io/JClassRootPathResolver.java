package j.jave.framework.io;

import java.io.File;
import java.net.URI;


/**
 * path based on class root path. 
 * @author J
 *
 */
public class JClassRootPathResolver implements JPathResolver {

	private final String fileName;
	public JClassRootPathResolver(String fileName){
		this.fileName=fileName;
	}
	
	@Override
	public URI resolver() throws Exception {
		String classPath=Thread.currentThread().getContextClassLoader().getResource("").toString();
		return new URI(classPath+fileName);
	}
	
	@Override
	public String getDescription() {
		return "file [" + fileName+ "]";
	}

}
