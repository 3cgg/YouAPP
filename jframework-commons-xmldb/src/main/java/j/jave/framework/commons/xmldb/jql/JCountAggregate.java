package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.utils.JStringUtils;

import java.util.Map;

public class JCountAggregate extends JAggregate {

	private int count;
	
	public JCountAggregate(String property, String alias) {
		super(property, alias);
		name="Count("+property+")";
	}
	
	public JCountAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	
	public JCountAggregate(String property) {
		super(property);
		name="Count("+property+")";
	}
	
	public JCountAggregate(){}

	@Override
	public Object execute(Map<String, JBaseModel> models) {
		return count++;
	}

	@Override
	public Object get() {
		return count;
	}

	@Override
	public void reset() {
		this.count=0;
	}
	
	
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from){
		
	}
	
	@Override
	public String jql() {
		return "count("
				+super.jql()
				+")"
				+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}
}
