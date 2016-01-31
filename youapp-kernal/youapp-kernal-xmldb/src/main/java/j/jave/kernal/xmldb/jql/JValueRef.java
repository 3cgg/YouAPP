package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.validate.JFieldIsColumnValidator;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.xmldb.JSelect;

import java.util.Map;




public class JValueRef implements JValue{
	
	protected String property;
	
	protected String alias=JSelect.DEFAULT_ALIAS;
	
	public JValueRef(String property,String alias) {
		this.property=property.trim();
		
		if(JStringUtils.isNotNullOrEmpty(alias)){
			this.alias=alias;
		}
		else{
			throw new RuntimeException("invalid expression \""
						+(alias==null?"":alias)
						+"."
						+(property==null?"":property)
						+"\", alias cannot be empty.");
		}
	}
	
	public JValueRef(String property) {
		this.property=property.trim();
	}
	
	public JValueRef() {
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		
		if(JStringUtils.isNotNullOrEmpty(alias)){
			this.alias=alias;
		}
		else{
			throw new RuntimeException("alias cannot be empty.");
		}
	}
	
	@Override
	public String toString() {
		return 
				((JStringUtils.isNotNullOrEmpty(alias)&&!JSelect.DEFAULT_ALIAS.equals(alias))? getAlias()+".":"")
				+property;
	}
	
	
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from){
		if(from.get(getAlias())==null){
			throw new RuntimeException("invalid expression \""+jql()+"\""+",alias \""+getAlias()+"\" (alias "+JSelect.DEFAULT_ALIAS+" is default if alias of property not defined) dont exists in From part.");
		}
		else{
			JFieldIsColumnValidator fieldIsColumnValidator=new JFieldIsColumnValidator(from.get(getAlias()));
			if(!fieldIsColumnValidator.validate(getProperty())){
				throw new RuntimeException("invalid expression \""+jql()+"\""+" ,prperty (xml mapping) \""+getProperty()+"\" dont exists in class in From part.");
			}
			
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		JValueRef other=(JValueRef)obj;
		String thisAlias=alias==null?"":alias;
		String otherAlias=other.alias==null?"":other.alias;
		return thisAlias.equals(otherAlias)
				&&property.equals(other.property);
	}
	
	
	@Override
	public JValueRef clone() throws CloneNotSupportedException {
		return (JValueRef)super.clone();
	}
	
	
	public Object value(Map<String, JBaseModel> models){
		return JClassUtils.get(getProperty(), models.get(getAlias()));
	}
	
	
	public String jql(){
		if(!JSelect.DEFAULT_ALIAS.equals(alias)){
			return alias+"."+property;
		}
		else{
			return property;
		}
	}
}
