package j.jave.kernal.jave.xml.xmldb.jql;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.Map;

public class JIFPlain extends JIF  {

	private static final JLogger logger =JLoggerFactory.getLogger(JIFPlain.class); 
			
	private JValueRef key;
	private String value;
	
	public JIFPlain() {
	}
	
	public JIFPlain(JValueRef property,String compare,String value) {
		this.key=property;
		this.compare=compare;
		this.value=value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String name() {
		return JIFPlain.class.getName();
	}
	
	@Override
	public boolean validate(Map<String, JBaseModel> models) {
		try {
			logger.debug("key="+key+"||value="+value+"||compare="+compare);
			if (JStringUtils.isNullOrEmpty(value)) {
				return true;
			} else {
				Object obj= key.value(models);	
				return validate(obj, value);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void push(JFilter filter) {
		throw new UnsupportedOperationException("IF cannot contain other filter. ");
		
	}

	public JValueRef getKey() {
		return key;
	}

	public void setKey(JValueRef key) {
		this.key = key;
	}

	@Override
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from) {
		key.validateExpress(from);
	}

	@Override
	public String jql() {
		return key.jql()+" "+compare+":"+" "+value;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
