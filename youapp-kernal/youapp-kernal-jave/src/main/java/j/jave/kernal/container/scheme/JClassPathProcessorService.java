package j.jave.kernal.container.scheme;

import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.jave.support._resource.JResourceStreamException;
import j.jave.kernal.jave.utils.JIOUtils;

import java.net.URI;

public class JClassPathProcessorService implements JSchemeProcessorService {

	@Override
	public Object process(URI uri) {
		
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			bytes=JIOUtils.getBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream(queryPath), true);
		}catch(Exception e){
			throw new JResourceStreamException(queryPath);
		}
		return bytes;
	}

}
