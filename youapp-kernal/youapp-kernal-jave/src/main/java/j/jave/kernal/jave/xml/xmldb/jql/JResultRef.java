package j.jave.kernal.jave.xml.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.validate.JFieldIsColumnValidator;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.xmldb.JSelect;

import java.util.Map;
import java.util.regex.Pattern;

public class JResultRef extends JValueRef implements JResult{
	protected String name;
	
	public JResultRef(String property,String alias) {
		super(property, alias);
	}
	
	public JResultRef(String property,String alias,String name ) {
		super(property, alias);
		this.name=name;
	}
	
	public JResultRef(String property) {
		super(property);
	}
	
	public JResultRef(){}
	
	public String getName() {
		
		if(JStringUtils.isNullOrEmpty(name))
			return getProperty();
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString()+" as "+getName();
	}
	
	@Override
	public String jql() {
		return super.jql()+(JStringUtils.isNullOrEmpty(name)?"":(" as "+getName()));
	}
	
	
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from){
		if(from.get(getAlias())==null){
			throw new RuntimeException("invalid expression \""+jql()+"\""+",alias \""+getAlias()+"\" (alias "+JSelect.DEFAULT_ALIAS+" is default if alias of property not defined) dont exists in From part.");
		}
		else{
			JFieldIsColumnValidator fieldIsColumnValidator=new JFieldIsColumnValidator(from.get(getAlias()));
			
			if(!fieldIsColumnValidator.validate(getProperty())){
				
				Pattern pattern=Pattern.compile("^\\s*(\\w|_)+\\s+(\\w|_)+\\s*$");
				if(pattern.matcher(getProperty()).find()){
					throw new RuntimeException("invalid expression \""+jql()+"\""+" , \"as\" keyword is mandatory if specifing an alias for result.");
				}
				else{
					throw new RuntimeException("invalid expression \""+jql()+"\""+" ,prperty (xml mapping) \""+getProperty()+"\" dont exists in class in From part.");
				}
			}
			
		}
	}
	
}
