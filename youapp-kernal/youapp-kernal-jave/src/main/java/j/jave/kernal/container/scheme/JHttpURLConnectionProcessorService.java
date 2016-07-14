package j.jave.kernal.container.scheme;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.http.JHttpBase;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.http.JResponseHandler;
import j.jave.kernal.jave.support._resource.JResourceStreamException;

import java.net.URI;

public class JHttpURLConnectionProcessorService implements JSchemeProcessorService {

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
	public Object process(URI uri) {
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			bytes=(byte[]) HTTP_GET.setResponseHandler(BYTE_HANDLER).execute(uri.toURL().toString());
		}catch(Exception e){
			throw new JResourceStreamException(queryPath);
		}
		return bytes;
	}

}
