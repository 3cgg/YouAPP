/**
 * 
 */
package j.jave.framework.commons.support.validate;


/**
 * equals the parameter object. 
 * @author J
 */
public class JFileNameValidator extends JTextValidator {
	
	private String fileName;

	public JFileNameValidator(String fileName) {
		this.fileName=fileName;
	}
	
	@Override
	public boolean validate(String object) {
		return fileName.equals(object);
	}
	

}
