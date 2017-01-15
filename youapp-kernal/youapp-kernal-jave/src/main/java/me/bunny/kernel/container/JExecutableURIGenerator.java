package me.bunny.kernel.container;

public interface JExecutableURIGenerator {

	String getGetRequestURI(String unique,String path);
	
	String getPutRequestURI(String unique,String path);
	
	String getDeleteRequestURI(String unique,String path);
	
	String getExistRequestURI(String unique,String path);
	
}
