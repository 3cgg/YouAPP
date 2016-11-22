package j.jave.kernal.streaming.netty;

import io.netty.handler.codec.http.HttpHeaders;

public abstract class Utils {

	public static final String CONTROLLER_URI_NAMES=HeaderNames.CONTROLLER_URI_NAMES;
	
	public static String controllerUri(HttpHeaders headers ){
		return headers.get(CONTROLLER_URI_NAMES);
	}
	
	public static void setControllerUri(HttpHeaders headers ,String curi){
		headers.add(CONTROLLER_URI_NAMES, curi);
	}
}
