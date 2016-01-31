package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;

import java.util.Map;

public class JIFRef extends JIF {

	private static final JLogger logger =JLoggerFactory.getLogger(JIFRef.class);

	private JValueRef key;
	private JValueRef value;
	
	public JIFRef() {
	}
	
	public JIFRef(JValueRef key,String compare,JValueRef value) {
		this.key=key;
		this.compare=compare;
		this.value=value;
	}
	
	
	@Override
	public String name() {
		return JIFRef.class.getName();
	}
	
	@Override
	public boolean validate(Map<String, JBaseModel> models) {
		try {
			logger.debug("key="+key+"||value="+value+"||compare="+compare);
			Object keyVale=key.value(models);
			Object valueVale=value.value(models);
			return validate(keyVale, valueVale);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	

	public JValueRef getKey() {
		return key;
	}

	public void setKey(JValueRef key) {
		this.key = key;
	}

	public JValueRef getValue() {
		return value;
	}

	public void setValue(JValueRef value) {
		this.value = value;
	}

	@Override
	public void push(JFilter filter) {
		throw new UnsupportedOperationException("IF cannot contain other filter. ");
		
	}
	
	
	@Override
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from) {
		key.validateExpress(from);
		value.validateExpress(from);
	}

	@Override
	public String jql() {
		return key.jql()+" "+compare+" "+value.jql();
	}
}
