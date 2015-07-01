/**
 * 
 */
package j.jave.framework.commons.support.validate;

/**
 * super-class , which all validators on the simple text can extend .
 * @author J
 */
public class JTextValidator implements JValidator<String> {

	protected String text;
	
	public JTextValidator(String text) {
		this.text=text;
	}
	
	public JTextValidator() {
	}
	
	@Override
	public boolean validate(String object) {
		return text.indexOf(object)!=-1;
	}
}
