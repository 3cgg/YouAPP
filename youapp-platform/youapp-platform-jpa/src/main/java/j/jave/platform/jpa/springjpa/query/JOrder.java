package j.jave.platform.jpa.springjpa.query;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JStringUtils;

public class JOrder implements JModel {

	private Class<?> entityClass;
	
	private JSingleEntityQuery singleEntityQuery;
	
	public JOrder(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	void setSingleEntityQuery(JSingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	private List<String> orderClause=new ArrayList<String>();
	
	private boolean validate(String property) throws IllegalArgumentException{
		return JEntityUtilService.get().getEntityValidateService().propertyExists(property,entityClass);
	}
	
	public JSingleEntityQuery ready(){
		return singleEntityQuery;
	}
	
	public String toOrderClause(){
		StringBuffer stringBuffer=new StringBuffer("");
		String prefix=",";
		for(String clause:orderClause){
			stringBuffer.append(prefix);
			stringBuffer.append(" "+clause+" ");
		}
		String inner=stringBuffer.toString().replaceFirst(prefix, "").trim();
		return JStringUtils.isNullOrEmpty(inner)?"":(" order by "+inner);
	}
	
	public static enum OrderType{
		ASC("ASC"),DESC("DESC");
		private OrderType(String type){
			
		}
	}
	
	private JOrder append(String property,String orderType){
		orderClause.add(JSingleEntityQueryMeta.ALIAS+"."+property+" "+orderType);
		return this;
	}
	
	public JOrder asc(String property){
		validate(property);
		append(property, OrderType.ASC.name());
		return this;
	}
	
	public JOrder desc(String property){
		validate(property);
		append(property, OrderType.DESC.name());
		return this;
	}
	
	
}
