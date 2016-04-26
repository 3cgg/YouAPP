package j.jave.kernal.container;


public class JExecutableURIUtil {

	public static enum Type{
		
		GET("/get"),PUT("/put"),DELETE("/delete"),EXIST("/exist"),EXECUTE("/execute");
		
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
	private static final String URI="%s://localhost%s?"+UNIQUE+"=%s&"+PATH+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&path=([a-zA-Z:0-9_]+)$
	 */
	public static final String REGX="^"+UNIQUE+"=([a-zA-Z:0-9_.]+)&"+PATH+"=([a-zA-Z:0-9_/]+)$";
	
	
	public static final String getGetRequestURI(String unique,String path,Scheme scheme){
		return String.format(URI,scheme.getValue(),Type.GET.getValue(),unique,path);
	}
	
	public static final String getPutRequestURI(String unique,String path,Scheme scheme){
		return String.format(URI,scheme.getValue(),Type.PUT.getValue(),unique,path);
	}
	
	public static final String getDeleteRequestURI(String unique,String path,Scheme scheme){
		return String.format(URI,scheme.getValue(),Type.DELETE.getValue(),unique,path);
	}
	
	public static final String getExistRequestURI(String unique,String path,Scheme scheme){
		return String.format(URI,scheme.getValue(),Type.EXIST.getValue(),unique,path);
	}
	
	public static final String getExecuteRequestURI(String unique,String path,Scheme scheme){
		return String.format(URI,scheme.getValue(),Type.EXECUTE.getValue(),unique,path);
	}
	
}
