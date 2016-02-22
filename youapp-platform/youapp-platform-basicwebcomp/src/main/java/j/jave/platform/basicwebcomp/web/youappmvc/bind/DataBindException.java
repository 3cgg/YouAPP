/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.bind;

/**
 * @author J
 */
public class DataBindException extends RuntimeException {

	public DataBindException(String message){
		super(message);
	}
	
	public DataBindException(Exception e){
		super(e);
	}
	
	
}
