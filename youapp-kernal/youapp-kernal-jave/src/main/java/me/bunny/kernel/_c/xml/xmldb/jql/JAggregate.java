package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.xml.xmldb.JSelect;

public abstract class JAggregate  extends JResultRef {
	
	public JAggregate(String property,String alias){
		super(property, alias);
	}
	
	public JAggregate(String property,String alias,String name ) {
		super(property, alias, name);
	}
	
	public JAggregate(String property) {
		super(property);
	}
	
	public JAggregate(){}
	
	//public abstract Object execute(Model model);
	
	public abstract Object execute(Map<String, JBaseModel> models);
	
	public abstract Object get();
	
	public abstract void reset();
	
	@Override
	public String jql() {
		return ((JStringUtils.isNotNullOrEmpty(alias)&&!JSelect.DEFAULT_ALIAS.equals(alias))? getAlias()+".":"")
				+getProperty();
	}
}
