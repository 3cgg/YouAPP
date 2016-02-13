package j.jave.kernal.jave.io;

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
	public URI resolve() throws Exception {
		String classPath=Thread.currentThread().getContextClassLoader().getResource("").toString();
		return new URI(classPath+fileName);
	}
	
	@Override
	public String getDescription() {
		return "file [" + fileName+ "]";
	}

}
