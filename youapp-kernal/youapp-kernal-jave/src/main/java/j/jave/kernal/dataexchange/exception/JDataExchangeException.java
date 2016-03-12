/**
 * 
 */
package j.jave.kernal.dataexchange.exception;

/**
 * @author J
 */
public class JDataExchangeException extends RuntimeException {
	
	private static final long serialVersionUID = 638712363288955742L;

	public JDataExchangeException(String message){
		super(message);
	}
	
	public JDataExchangeException(Exception e){
		super(e);
	}
	
	
}
