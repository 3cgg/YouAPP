package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.model.JBaseModel;

import java.util.Map;

public class JIFString extends JIF{

	private static final JLogger logger =JLoggerFactory.getLogger(JIFString.class);

	private String key;
	private String value;
	
	public JIFString() {
	}
	
	public JIFString(String key,String compare,String value) {
		this.key=key;
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
			return validate(key, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void push(JFilter filter) {
		throw new UnsupportedOperationException("IF cannot contain other filter. ");
		
	}

	@Override
	public String jql() {
		return key+" "+compare+" "+value;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
