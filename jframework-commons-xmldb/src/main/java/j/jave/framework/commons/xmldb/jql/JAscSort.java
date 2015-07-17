package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.utils.JNumberUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.xmldb.JSelect;


public class JAscSort extends JSort {
	
	public JAscSort(String property, String alias) {
		super(property, alias);
	}
	/*
	public AscSort(String property, String alias, String name) {
		super(property, alias, name);
	}
	*/
	public JAscSort(String property) {
		super(property);
	}

	@Override
	protected boolean compare(JSQLType dataType, Object thisValue,
			Object beforeValue) {
		
		if(dataType==JSQLType.DOUBLE||dataType==JSQLType.INTEGER){
			return JNumberUtils.ltEqualNumber(thisValue, beforeValue);
		}
		else {
			return JStringUtils.ltEqualString(thisValue, beforeValue);
		}
	}
	
	public String jql(){
		if(!JSelect.DEFAULT_ALIAS.equals(alias)){
			return alias+"."+property+" asc";
		}
		else{
			return property+" asc";
		}
	}

}
