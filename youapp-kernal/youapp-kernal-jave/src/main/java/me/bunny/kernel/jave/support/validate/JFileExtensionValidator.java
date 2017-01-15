/**
 * 
 */
package me.bunny.kernel.jave.support.validate;

/**
 * @author Administrator
 *
 */
public class JFileExtensionValidator extends JTextValidator {
	
	/**
	 * @param text
	 */
	public JFileExtensionValidator(String text) {
		String extension=text.trim();
		if(!extension.startsWith(".")){
			extension="."+extension;
		}
		this.text=extension;
	}

	@Override
	public boolean validate(String object) {
		return object.endsWith(this.text);
	}
}
