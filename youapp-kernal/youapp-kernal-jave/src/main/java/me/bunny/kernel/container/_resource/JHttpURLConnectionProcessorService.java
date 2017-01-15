package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.support._resource.JResourceStreamException;
import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JURIInfo;
import me.bunny.kernel.http.JHttpBase;
import me.bunny.kernel.http.JHttpFactoryProvider;
import me.bunny.kernel.http.JResponseHandler;

public class JHttpURLConnectionProcessorService implements JResourceProcessorService {

	private JHttpBase<?> HTTP_GET=JHttpFactoryProvider.getHttpFactory(JConfiguration.get())
			.getHttpGet();
	
	private JHttpBase<?> HTTP_POST=JHttpFactoryProvider.getHttpFactory(JConfiguration.get())
			.getHttpPost();
	
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
			byte[] requestBytes=new byte[]{};
			if(object!=null){
				requestBytes=(byte[])object;
			}
			if(JExecutableURIUtil.isPut(uriInfo)){
				bytes=(byte[]) HTTP_POST.setEntry(requestBytes)
						.setResponseHandler(BYTE_HANDLER).execute(queryPath);
			}
			else{
				bytes=(byte[]) HTTP_GET.setEntry(requestBytes)
						.setResponseHandler(BYTE_HANDLER).execute(queryPath);
			}
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}

}
