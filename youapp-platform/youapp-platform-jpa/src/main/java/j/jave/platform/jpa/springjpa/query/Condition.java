package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Condition implements JModel {

	/**
	 * how to link this condition.
	 */
	private LinkType linkType;  
	
	private SingleEntityQuery singleEntityQuery;
	
	private Class<?> entityClass;
	
	private List<String> conditionSliceClauses=new ArrayList<String>();
	
	private Map<String, Object> params=new HashMap<String, Object>();
	
	private boolean rootUsed=false;
	
	/**
	 * next condition
	 */
	private Condition next;
	
	/**
	 * previous condition
	 */
	private Condition pre;
	
	public Condition(Class<?> entityClass) {
		this(entityClass,LinkType.ROOT);
	}
	
	private Condition(Class<?> entityClass,LinkType linkType) {
		this(entityClass, linkType, null);
	}
	
	private Condition(Class<?> entityClass,LinkType linkType,Condition previousCondition) {
		this.entityClass=entityClass;
		this.linkType=linkType;
		this.pre=previousCondition;
	}
	
	void setSingleEntityQuery(SingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	
	private boolean validate(String property) throws IllegalArgumentException{
		return true;
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
	private String toNextWholeClause(Condition thisCondition){
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
	
	private Condition append(String property,Object value,String opeType,LinkType... linkType){
		validate(property);
		String linkTypeName=null;
		if(rootUsed){
			linkTypeName=linkType.length>0?linkType[0].name():LinkType.AND.name();
		}else{
			linkTypeName="";
			rootUsed=true;
		}
		String paramString=property+"_pm_";
		conditionSliceClauses.add(linkTypeName+" "+SingleEntityQueryMeta.ALIAS+"."+property+opeType+" :"+paramString);
		params.put(paramString, value);
		return this;
	}
	
	/**
	 * link to another condition, 
	 * such as <p> (1=1 and 1=2) <strong>[first condition]</strong> or (1=1 and 1=2)<strong>[second condition]</strong>           
	 */
	public Condition link(LinkType linkType){
		next=new Condition(entityClass, linkType,this);
		return next;
	}
	
	public SingleEntityQuery ready(){
		return singleEntityQuery;
	}
	
	public Condition startLikes(String property,String value,LinkType... linkType){
		return append(property, value+"%", Ope.LIKE,linkType);
	}
	
	public Condition endLikes(String property,String value,LinkType... linkType){
		return append(property, "%"+value, Ope.LIKE,linkType);
	}
	
	public Condition likes(String property,String value,LinkType... linkType){
		return append(property, "%"+value+"%", Ope.LIKE,linkType);
	}
	
	public Condition equals(String property,Object value,LinkType... linkType){
		append(property, value, Ope.EQUAL,linkType);
		return this;
	}
	
	public Condition notEquals(String property,Object value,LinkType... linkType){
		append(property, value, Ope.NOT_EQUAL,linkType);
		return this;
	}
	
	public Condition larger(String property,Object value,LinkType... linkType){
		append(property, value, Ope.LARGER,linkType);
		return this;
	}

	public Condition largerAndEquals(String property,Object value,LinkType... linkType){
		append(property, value, Ope.LARGER_EQUAL,linkType);
		return this;
	}
	
	public Condition smaller(String property,Object value,LinkType... linkType){
		append(property, value, Ope.SMALLER,linkType);
		return this;
	}
	
	public Condition smallerAndEqual(String property,Object value,LinkType... linkType){
		append(property, value, Ope.SMALLER_EQUAL,linkType);
		return this;
	}
	
	
	
	
	
	
	
	
	
	
}
