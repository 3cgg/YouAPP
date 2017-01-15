package j.jave.platform.jpa.springjpa.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JAssert;

public class JCondition implements JModel {
	
	/**
	 * how to link this condition.
	 */
	private LinkType linkType;  
	
	private JSingleEntityQuery singleEntityQuery;
	
	private Class<?> entityClass;
	
	private List<String> conditionSliceClauses=new ArrayList<String>();
	
	private Map<String, Object> params=new HashMap<String, Object>();
	
	private boolean rootUsed=false;
	
	/**
	 * next condition
	 */
	private JCondition next;
	
	/**
	 * previous condition
	 */
	private JCondition pre;
	
	public JCondition(Class<?> entityClass) {
		this(entityClass,LinkType.ROOT);
	}
	
	private JCondition(Class<?> entityClass,LinkType linkType) {
		this(entityClass, linkType, null);
	}
	
	private JCondition(Class<?> entityClass,LinkType linkType,JCondition previousCondition) {
		this.entityClass=entityClass;
		this.linkType=linkType;
		this.pre=previousCondition;
	}
	
	void setSingleEntityQuery(JSingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	
	private boolean validate(String property) throws IllegalArgumentException{
		return JEntityUtilService.get().getEntityValidateService().propertyExists(property,entityClass);
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public Map<String, Object> toParams() {
		if(pre==null){
			return getParams();
		}
		Map<String, Object> preSliceParams=pre.toParams();
		Map<String, Object> thisSliceParams=getParams();
		Map<String, Object> allParams=new HashMap<String, Object>(preSliceParams.size()+thisSliceParams.size());
		allParams.putAll(preSliceParams);
		allParams.putAll(thisSliceParams);
		return allParams;
	}
	
	public String toSliceClause(){
		StringBuffer stringBuffer=new StringBuffer("");
		String prefix=LinkType.ROOT==linkType?"":linkType.name();
		for(String clause:conditionSliceClauses){
			stringBuffer.append(" "+clause+" ");
		}
		String inner=stringBuffer.toString().trim();
		return " "+prefix+" ("+inner+")";
	}
	
	/**
	 * include this condition
	 * @return
	 */
	private String toPreWholeClause(){
		if(pre==null){
			return toSliceClause();
		}
		String preSliceClause=pre.toPreWholeClause();
		String thisSliceClause=toSliceClause();
		return preSliceClause+ thisSliceClause;
	}
	
	/**
	 * exclude this condition.
	 * @param thisCondition
	 * @return
	 */
	private String toNextWholeClause(JCondition thisCondition){
		if(next==null){
			if(this!=thisCondition){
				return toSliceClause();
			}
			else{
				return "";
			}
		}
		String nextSliceClause=next.toNextWholeClause(thisCondition);
		String thisSliceClause="";
		if(this!=thisCondition){
			thisSliceClause=toSliceClause();
		}
		return thisSliceClause+nextSliceClause;
	}
	
	public String toWhereClause(){
		return " where "+toPreWholeClause()+toNextWholeClause(this);
	}
	
	public static enum LinkType{
		AND("AND"),OR("OR"),ROOT("ROOT");
		private LinkType(String type){
			
		}
		
	}
	
	private interface Ope{
		String EQUAL=" = ";
		String NOT_EQUAL=" != ";
		String LARGER=" > ";
		String LARGER_EQUAL=" >= ";
		String SMALLER=" < ";
		String SMALLER_EQUAL=" <= ";
		String LIKE=" like ";
	}
	
	public static class Condition{
		
		private String column="";
		
		private String ope;
		
		private Object value;

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getOpe() {
			return ope;
		}

		public void setOpe(String ope) {
			this.ope = ope;
		}
		
		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public static Condition likes(String column,Object value){
			Condition condition=new Condition();
			condition.column=column;
			condition.ope=Ope.LIKE;
			condition.value="%"+value+"%";
			return condition;
		}
		
		public static Condition startLikes(String column,Object value){
			Condition condition=new Condition();
			condition.column=column;
			condition.ope=Ope.LIKE;
			condition.value=value+"%";
			return condition;
		}
		
		public static Condition endLikes(String column,Object value){
			Condition condition=new Condition();
			condition.column=column;
			condition.ope=Ope.LIKE;
			condition.value="%"+value;
			return condition;
		}
		
		public static Condition equals(String column,Object value){
			Condition condition=new Condition();
			condition.column=column;
			condition.ope=Ope.EQUAL;
			condition.value=value;
			return condition;
		}
		
		public static Condition notEquals(String column,Object value){
			Condition condition=new Condition();
			condition.column=column;
			condition.ope=Ope.NOT_EQUAL;
			condition.value=value;
			return condition;
		}
		
		public static Condition likes(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.LIKE;
			condition.value="%"+value+"%";
			return condition;
		}
		
		public static Condition startLikes(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.LIKE;
			condition.value=value+"%";
			return condition;
		}
		
		public static Condition endLikes(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.LIKE;
			condition.value="%"+value;
			return condition;
		}
		
		public static Condition equal(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.EQUAL;
			condition.value=value;
			return condition;
		}
		
		public static Condition notEquals(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.NOT_EQUAL;
			condition.value=value;
			return condition;
		}
		
		
		public static Condition smaller(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.SMALLER;
			condition.value=value;
			return condition;
		}
		
		public static Condition larger(Object value){
			Condition condition=new Condition();
			condition.ope=Ope.LARGER;
			condition.value=value;
			return condition;
		}
		
		
	}
	
	private JCondition append(String property,Object value,String opeType,LinkType... linkType){
		if(value==null) return this;
		validate(property);
		String linkTypeName=null;
		if(rootUsed){
			linkTypeName=linkType.length>0?linkType[0].name():LinkType.AND.name();
		}else{
			linkTypeName="";
			rootUsed=true;
		}
		String paramString=property+"_pm_";
		conditionSliceClauses.add(linkTypeName+" "+JSingleEntityQueryMeta.ALIAS+"."+property+opeType+" :"+paramString);
		params.put(paramString, value);
		return this;
	}
	
	/**
	 * link to another condition, 
	 * such as <p> (1=1 and 1=2) <strong>[first condition]</strong> or (1=1 and 1=2)<strong>[second condition]</strong>           
	 */
	public JCondition link(LinkType linkType){
		next=new JCondition(entityClass, linkType,this);
		return next;
	}
	
	public JSingleEntityQuery ready(){
		return singleEntityQuery;
	}
	
	public JCondition startLikes(String property,String value,LinkType... linkType){
		String val=value==null?null:value+"%";
		return append(property, val, Ope.LIKE,linkType);
	}
	
	public JCondition endLikes(String property,String value,LinkType... linkType){
		String val=value==null?null:"%"+value;
		return append(property, val, Ope.LIKE,linkType);
	}
	
	public JCondition likes(String property,String value,LinkType... linkType){
		String val=value==null?null:"%"+value+"%";
		return append(property, val, Ope.LIKE,linkType);
	}
	
	public JCondition equals(String property,Object value,LinkType... linkType){
		JAssert.isNotNull(value, "the parameter["+property+"] is null.");
		append(property, value, Ope.EQUAL,linkType);
		return this;
	}
	
	public JCondition notEquals(String property,Object value,LinkType... linkType){
		append(property, value, Ope.NOT_EQUAL,linkType);
		return this;
	}
	
	public JCondition larger(String property,Object value,LinkType... linkType){
		append(property, value, Ope.LARGER,linkType);
		return this;
	}

	public JCondition largerAndEquals(String property,Object value,LinkType... linkType){
		append(property, value, Ope.LARGER_EQUAL,linkType);
		return this;
	}
	
	public JCondition smaller(String property,Object value,LinkType... linkType){
		append(property, value, Ope.SMALLER,linkType);
		return this;
	}
	
	public JCondition smallerAndEqual(String property,Object value,LinkType... linkType){
		append(property, value, Ope.SMALLER_EQUAL,linkType);
		return this;
	}
	
	
	
	
	
	
	
	
	
	
}
