package j.jave.kernal.container;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	
	private static final String HOST="localcontaineruri.com";
	
	/**
	 * controller://get/put/delete?unique=%s&path=%s
	 */
	private static final String URI="%s://"+HOST+"%s?"+UNIQUE+"=%s&"+PATH+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&path=([a-zA-Z:0-9_]+)$
	 */
	public static final String REGX="^"+UNIQUE+"=([a-zA-Z:0-9_.-]+)&"+PATH+"=([a-zA-Z:0-9_./]+)$";
	
	
	public static final String getGetRequestURI(String unique,String path,JScheme scheme){
		return String.format(URI,scheme.getValue(),Type.GET.getValue(),unique,path);
	}
	
	public static final String getPutRequestURI(String unique,String path,JScheme scheme){
		return String.format(URI,scheme.getValue(),Type.PUT.getValue(),unique,path);
	}
	
	public static final String getDeleteRequestURI(String unique,String path,JScheme scheme){
		return String.format(URI,scheme.getValue(),Type.DELETE.getValue(),unique,path);
	}
	
	public static final String getExistRequestURI(String unique,String path,JScheme scheme){
		return String.format(URI,scheme.getValue(),Type.EXIST.getValue(),unique,path);
	}
	
	public static final String getExecuteRequestURI(String unique,String path,JScheme scheme){
		return String.format(URI,scheme.getValue(),Type.EXECUTE.getValue(),unique,path);
	}
	
	public static final String getScheme(URI uri) {
		return uri.getScheme();
	}
	
	public static final String getHost(URI uri) {
		return uri.getHost();
	}
	
	public static final String getPath(URI uri) {
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		String path=null;
		if(matcher.matches()){
			path=matcher.group(2);
		}
		return path;
	}
	
	public static final String getUnique(URI uri) {
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		String unique=null;
		if(matcher.matches()){
			unique=matcher.group(1);
		}
		return unique;
	}
	
	public static final JURIInfo getURIInfo(URI uri){
		JURIInfo uriInfo=new JURIInfo();
		uriInfo.setScheme(uri.getScheme());
		uriInfo.setHost(uri.getHost());
		uriInfo.setPath(uri.getPath());
		
		String query= uri.getQuery();
		Pattern pattern=Pattern.compile(REGX);
		Matcher matcher=pattern.matcher(query);
		String queryUnique=null;
		String queryPath=null;
		if(matcher.matches()){
			queryUnique=matcher.group(1);
			queryPath=matcher.group(2);
		}
		uriInfo.setQueryUnique(queryUnique);
		uriInfo.setQueryPath(queryPath);
		uriInfo.setWholeUri(uri.toString());
		return uriInfo;
	}
	
	public static boolean isWrapped(URI uri){
		return HOST.equals(uri.getHost());
	}
	
}
