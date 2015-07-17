package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.JBaseModel;

import java.util.Map;

public class JResultPlain implements JResult {

	private String value;
	
	private String name;
	
	public JResultPlain(String value,String name){
		this.value=value;
		this.name=name;
	}
	
	public JResultPlain(String value){
		this.value=value;
		this.name=value;
	}
	
	
	@Override
	public Object value(Map<String, JBaseModel> models) {
		return value;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String jql() {
		return value+" as "+name;
	}

	@Override
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from) {
		
	}
	
}
