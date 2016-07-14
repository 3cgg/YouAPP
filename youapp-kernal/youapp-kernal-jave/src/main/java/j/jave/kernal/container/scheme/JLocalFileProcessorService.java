package j.jave.kernal.container.scheme;

import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.jave.support._resource.JResourceNotFoundException;
import j.jave.kernal.jave.support._resource.JResourceStreamException;
import j.jave.kernal.jave.utils.JIOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class JLocalFileProcessorService implements JSchemeProcessorService {
	
	@Override
	public Object process(URI uri) {
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			File file=new File(new URI(queryPath));
			if(!file.exists()){
				throw new JResourceNotFoundException(queryPath);
			}
			bytes=JIOUtils.getBytes(new FileInputStream(file));
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}

}
