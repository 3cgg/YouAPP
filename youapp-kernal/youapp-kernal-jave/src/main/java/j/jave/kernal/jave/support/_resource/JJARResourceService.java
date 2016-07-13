package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.utils.JJARUtils;

import java.io.File;
import java.net.URI;

public class JJARResourceService {

	public URI getURI(String jarFilePath,String expectFilePath) throws Exception{
		
		String uri=JJARUtils.getURI(new File(jarFilePath).toURI().toString(), expectFilePath);

		return null;
		
		
		
	}
	
	
}
