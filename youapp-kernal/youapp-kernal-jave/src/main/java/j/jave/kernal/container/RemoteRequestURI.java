package j.jave.kernal.container;


public class RemoteRequestURI {

	public static enum Type{
		
		GET("/get"),PUT("/put"),DELETE("/delete"),EXIST("/exist");
		
		private String value;
		
		Type(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	private static final String UNIQUE="unique";
	
	private static final String PATH="path";
	
	/**
	 * controller://get/put/delete?unique=%s&path=%s
	 */
	private static final String URI=Scheme.REMOTE_HTTP.getValue()+"://localhost%s?"+UNIQUE+"=%s&"+PATH+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&path=([a-zA-Z:0-9_]+)$
	 */
	public static final String REGX="^"+UNIQUE+"=([a-zA-Z:0-9_.]+)&"+PATH+"=([a-zA-Z:0-9_/]+)$";
	
//	private static final String PUT=Scheme.CONTROLLER.getValue()+"://put?unique={}&path={}";
	
	static final String getRemoteGetRequest(String unique,String path){
		return String.format(URI,Type.GET.getValue(),unique,path);
	}
	
	static final String getRemoteExistRequest(String unique,String path){
		return String.format(URI,Type.EXIST.getValue(),unique,path);
	}
	
	static final String getRemotePutRequest(String unique,String path){
		return String.format(URI,Type.PUT.getValue(),unique,path);
	}
	
}
