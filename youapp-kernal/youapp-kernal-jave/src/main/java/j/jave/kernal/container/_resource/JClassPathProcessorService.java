package j.jave.kernal.container._resource;

import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JExecutableURIUtil.Type;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.support._resource.JResourceStreamException;
import j.jave.kernal.jave.utils.JIOUtils;

import java.net.URI;

public class JClassPathProcessorService implements JResourceProcessorService {

	@Override
	public Object process(URI uri,Object object) {
		
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		
		if(!Type.GET.getValue().equals(uriInfo.getPath())){
			throw new JOperationNotSupportedException("only support get : "+uriInfo.getWholeUri());
		}
		
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			bytes=JIOUtils.getBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream(queryPath), true);
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}

}
