package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.xmldb.JSelect;

import java.util.Map;

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
