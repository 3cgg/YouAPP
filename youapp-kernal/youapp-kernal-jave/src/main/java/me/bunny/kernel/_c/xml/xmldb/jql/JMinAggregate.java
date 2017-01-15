package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.model.support.JSQLType;
import me.bunny.kernel._c.model.support.detect.JGetSQLTypeOnModel;
import me.bunny.kernel._c.utils.JStringUtils;

public class JMinAggregate extends JAggregate {

	private Object min;
	
	public JMinAggregate(String property, String alias) {
		super(property, alias);
		name="MIN("+property+")";
	}
	public JMinAggregate() {
	}
	public JMinAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	
	public JMinAggregate(String property) {
		super(property);
		name="MIN("+property+")";
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
					if(this.min==null){
						this.min=value;
					}
					else{
						double min=Double.valueOf(this.min.toString());
						this.min=value<min?value:min;
					}
				}
				return this.min;
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else{
			if(JStringUtils.isNotNullOrEmpty(objString)){
				if(this.min==null){
					this.min=objString;
				}
				else{
				this.min=objString.compareTo(this.min.toString())<0?
						objString:this.min;
				}
			}
		}
		return this.min;
	}

	@Override
	public Object get() {
		return this.min;
	}

	@Override
	public void reset() {
		this.min=null;
	}
	
	@Override
	public String jql() {
		return "min("
				+super.jql()
				+")"
				+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}

}
