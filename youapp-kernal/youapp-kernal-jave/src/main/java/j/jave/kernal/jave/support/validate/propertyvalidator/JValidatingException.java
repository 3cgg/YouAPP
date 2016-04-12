/**
 * 
 */
package j.jave.kernal.jave.support.validate.propertyvalidator;

/**
 * @author J
 */
public class JValidatingException extends RuntimeException {

	public JValidatingException(String message){
		super(message);
	}
	
	public JValidatingException(Exception e){
		super(e);
	}
	
	public static void throwException(Exception e) throws JValidatingException{
		if(JValidatingException.class==e.getClass()){
			throw (JValidatingException)e;
		}
		else{
			throw new JValidatingException(e);
		}
	}
	
}
