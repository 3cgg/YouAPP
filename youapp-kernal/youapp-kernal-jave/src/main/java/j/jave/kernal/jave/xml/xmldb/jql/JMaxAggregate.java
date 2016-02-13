package j.jave.kernal.jave.xml.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.detect.JGetSQLTypeOnModel;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.Map;

public class JMaxAggregate extends JAggregate {

	private Object max;
	
	public JMaxAggregate(String property, String alias) {
		super(property, alias);
		name="MAX("+property+")";
	}
	
	public JMaxAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	
	public JMaxAggregate(String property) {
		super(property);
		name="MAX("+property+")";
	}
	
	public JMaxAggregate() {
	}

	@Override
	public Object execute(Map<String, JBaseModel> models) {
		
		Object obj=value(models);
		String objString=obj==null?"":String.valueOf(obj);
		
		JGetSQLTypeOnModel getSQLTypeOnModel=new JGetSQLTypeOnModel(models.get(getAlias()).getClass());
		JSQLType dataType= getSQLTypeOnModel.get(property);
		
		if(dataType==JSQLType.DOUBLE||dataType==JSQLType.INTEGER){
			try{
				if(JStringUtils.isNotNullOrEmpty(objString)){
					double value=Double.valueOf(String.valueOf(objString));
					double max=Double.valueOf(this.max.toString());
					this.max=value>max?value:max;
				}
				return this.max;
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else{
			if(JStringUtils.isNotNullOrEmpty(objString)){
				this.max=objString.compareTo(this.max.toString())>0?
						objString:this.max;
			}
		}
		return this.max;
	}

	@Override
	public Object get() {
		return this.max;
	}

	@Override
	public void reset() {
		this.max="";
	}
	
	@Override
	public String jql() {
		return "max("
				+super.jql()
				+")"
				+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}

}
