/**
 * 
 */
package j.jave.kernal.sqlloader;

/**
 * thrown when loading SQL automatically. 
 * @author J
 */
public class JSQLLoaderException extends RuntimeException {
	
	private static final long serialVersionUID = 638712363288955742L;

	public JSQLLoaderException(String message){
		super(message);
	}
	
	public JSQLLoaderException(Exception e){
		super(e);
	}
	
	
}
