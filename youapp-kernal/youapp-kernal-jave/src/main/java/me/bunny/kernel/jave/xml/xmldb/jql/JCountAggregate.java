package me.bunny.kernel.jave.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.utils.JStringUtils;

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
