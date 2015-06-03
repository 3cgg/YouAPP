package j.jave.framework.model.support;



/**
 * 
 * @author J
 *
 * @param <T>
 */
public abstract class JAbstractType<T> implements JTYPE<T> , JFieldValidator<T> {

	protected final JSQLType sqlType;
	
	protected final StringBuffer invalidMessage=new StringBuffer();
	
	/**
	 * highest validator.
	 */
	protected JFieldValidator<T> validator; 
	
	public JAbstractType(JSQLType sqlType){
		this.sqlType=sqlType;
	}
	
	public void setValidator(JFieldValidator<T> validator) {
		this.validator = validator;
	}
	
	/**
	 * should check whether the property {@code validator}  is existing, 
	 * <p> which should be highest priority then caller self.
	 * {@inheritDoc}
	 */
	public  boolean validate(T object) {
		boolean valid=true;
		if(validator!=null) {
			valid= validator.validate(object);
			if(!valid){
				invalidMessage.append(validator.invalidMessage());
			}
			return valid;
		}
		else{
			return defaultValidate(object);
		}
	}
	
	@Override
	public String invalidMessage() {
		return invalidMessage.toString();
	}
	
	public abstract boolean defaultValidate(T object) ;
	
}
