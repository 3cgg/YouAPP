package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.Map;

public class JSumAggregate extends JAggregate {

	private double sum;
	
	public JSumAggregate(String property, String alias) {
		super(property, alias);
		name="Sum("+property+")";
	}
	public JSumAggregate() {
	}
	public JSumAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	
	public JSumAggregate(String property) {
		super(property);
		name="Sum("+property+")";
	}

	@Override
	public Object execute(Map<String, JBaseModel> models) {
		
		Object obj=value(models);
		String objString=obj==null?"":String.valueOf(obj);
		try{
			if(JStringUtils.isNotNullOrEmpty(objString)){
				sum=sum+Double.valueOf(String.valueOf(objString));
			}
			return sum;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object get() {
		return sum;
	}

	@Override
	public void reset() {
		this.sum=0;
	}

	@Override
	public String jql() {
		return "sum("
				+super.jql()
				+")"
				+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}
}
