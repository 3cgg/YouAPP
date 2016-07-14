package j.jave.kernal.container._resource;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.http.JHttpBase;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.http.JResponseHandler;
import j.jave.kernal.jave.support._resource.JResourceStreamException;

import java.net.URI;

public class JHttpURLConnectionProcessorService implements JResourceProcessorService {

	private JHttpBase<?> HTTP_GET=JHttpFactoryProvider.getHttpFactory(JConfiguration.get())
			.getHttpGet();
	
	private static final JResponseHandler<byte[]> BYTE_HANDLER= 
			new JResponseHandler<byte[]>(){
				@Override
				public byte[] process(byte[] bytes)
						throws ProcessException {
					return bytes;
				}
			};
	
	@Override
	public Object process(URI uri,Object object) {
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			bytes=(byte[]) HTTP_GET.setResponseHandler(BYTE_HANDLER).execute(queryPath);
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}

}
