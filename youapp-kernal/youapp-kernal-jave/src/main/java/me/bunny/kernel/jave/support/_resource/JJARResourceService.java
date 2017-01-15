package me.bunny.kernel.jave.support._resource;

import java.io.File;
import java.net.URI;

import me.bunny.kernel.jave.utils.JJARUtils;

public class JJARResourceService {

	public URI getURI(String jarFilePath,String expectFilePath) throws Exception{
		
		String uri=JJARUtils.getURI(new File(jarFilePath).toURI().toString(), expectFilePath);

		return null;
		
		
		
	}
	
	
}
