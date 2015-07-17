package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.utils.JNumberUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.xmldb.JSelect;


public class JDescSort extends JSort {
	
	public JDescSort(String property, String alias) {
		super(property, alias);
	}
	/*
	public DescSort(String property, String alias, String name) {
		super(property, alias, name);
	}
	*/
	public JDescSort(String property) {
		super(property);
	}

	@Override
	protected boolean compare(JSQLType dataType, Object thisValue,
			Object beforeValue) {
		if(dataType==JSQLType.DOUBLE||dataType==JSQLType.INTEGER){
			return JNumberUtils.gtEqualNumber(thisValue, beforeValue);
		}
		else {
			return JStringUtils.gtEqualString(thisValue, beforeValue);
		}
	}

	public String jql(){
		if(!JSelect.DEFAULT_ALIAS.equals(alias)){
			return alias+"."+property+" desc";
		}
		else{
			return property+" desc";
		}
	}
}
