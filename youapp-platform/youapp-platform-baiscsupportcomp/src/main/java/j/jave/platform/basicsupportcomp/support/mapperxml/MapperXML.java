package j.jave.platform.basicsupportcomp.support.mapperxml;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperXML {

	private String tableName="";
	
	private String namespace="";
	
	private Class<?> namespaceClass;
	
	private Class<?> modelClass ; 
	
	private StringBuffer mapper=new StringBuffer();
	
	private Column UPDATEID=null;
	
	private Column UPDATETIME=null;
	
	private Column DELETED=null;
	
	private Column ID=null;
	
	private Column CREATEID=null;
	
	private Column CREATETIME=null;
	
	/**
	 * key begin from zero ...
	 */
	private Map<Integer,List<Column>> columns=new HashMap<Integer, List<Column>>();
	
	private List<Column> all=new ArrayList<MapperXML.Column>();
	
	class Column{
		String name;
		
		String property;
		
		String jdbcType;
		
		int level;
		
		String id;
		
		String type;
		
		String parent;
	}
	public void setNamespaceClass(Class<?> namespaceClass) {
		this.namespaceClass = namespaceClass;
	}
	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}
	private void setNamespace(Class<?> namespace){
		this.namespace=namespace.getName();
	}
	
	private void setTableName(Class<?> model) {
		JTable table=model.getAnnotation(JTable.class);
		String schema=table.schema();
		tableName = table.name();
		if(JStringUtils.isNotNullOrEmpty(schema)){
			tableName=schema+"."+tableName;
		}
	}
	
	private void setColumns(Class<?> model) {
		Class<?> superClass=model;
		int level=0;
		while(superClass!=null){
			String parent="";
			if(superClass.getSuperclass()!=null&&superClass.getSuperclass()!=Object.class){
				Class superSuperClass=superClass.getSuperclass();
				String className=superSuperClass.getSimpleName();
				parent=className.substring(0, 1).toLowerCase()+className.substring(1);
			}
			
			Field[] fields=superClass.getDeclaredFields();
			String className=superClass.getSimpleName();
			String id=className.substring(0, 1).toLowerCase()+className.substring(1);
			String type=superClass.getName();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				if(!JClassUtils.isAccessable(field)){
					field.setAccessible(true);
				}
				
				JColumn column=field.getAnnotation(JColumn.class);
				// check whether the property is mapping to a column of a table.
				if(column==null){
					continue;
				}
				else{
					Column clm=new Column();
					clm.name=column.name();
					clm.property=field.getName();
					clm.jdbcType=column.type().name();
					clm.level=level;
					clm.id=id;
					clm.type=type;
					clm.parent=parent;
					
					extend(clm);
					
					all.add(clm);
					List<Column> inners=columns.get(level);
					if(inners==null){
						inners=new ArrayList<MapperXML.Column>();
						inners.add(clm);
						columns.put(level, inners);
					}
					else{
						inners.add(clm);
					}
				}
			}
			superClass=superClass.getSuperclass();
			level++;
		}
	}
	
	private void extend(Column clm){
		if("DELETED".equals(clm.name)){
			DELETED=clm;
		}
		else if("UPDATETIME".equals(clm.name)){
			UPDATETIME=clm;
		}
		else if("UPDATEID".equals(clm.name)){
			UPDATEID=clm;
		}
		else if("ID".equals(clm.name)){
			ID=clm;
		}
		else if("CREATEID".equals(clm.name)){
			CREATEID=clm;
		}
		else if("CREATETIME".equals(clm.name)){
			CREATETIME=clm;
		}
	}
	
	private static final String RN="\n";
	
	private String getResultMapId() {
		return columns.get(0).get(0).id;
	}
	
	private StringBuffer getResultMap(){
		StringBuffer stringBuffer=new StringBuffer();
		int maxLevel=columns.size()-1;
		for(int i=columns.size()-1;i>=0;i--){
			
			List<Column> clms=columns.get(i);
			StringBuffer resultMap=new StringBuffer();
			StringBuffer results=new StringBuffer();
			String id="";
			String type="";
			int level=i;
			String parent="";
			for(int c=0;c<clms.size();c++){
				Column clm=clms.get(c);
				results.append(RN);
				String result="<result column=\""+clm.name+"\" property=\""+clm.property+"\"  jdbcType=\""+clm.jdbcType+"\" />";
				results.append(result);
				id=clm.id;
				type=clm.type;
				parent=clm.parent;
			}
			
			resultMap.append("<resultMap id=\""+id+"\"  type=\""+type+"\"  "+
					(level<maxLevel?"extends=\""+parent+"\"":"")
					+" >");
			resultMap.append(results.toString());
			resultMap.append(RN);
			resultMap.append("</resultMap>");
			
			stringBuffer.append(RN);
			stringBuffer.append(RN);
			stringBuffer.append(resultMap.toString());
		}
		return stringBuffer;
	}
	
	
	private StringBuffer getSave(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<insert  id=\"save\" parameterType=\""+modelClass.getName()+"\">");
		stringBuffer.append(RN);
		stringBuffer.append("insert into "+tableName
				+"\n"
				+"(");
		int level=0;
		StringBuffer itemBuffer=new StringBuffer();
		for(int i=0;i<all.size();i++){
			Column column=all.get(i);
			if(column.level>level){
				itemBuffer.append(RN);
				level=column.level;
			}
			
			itemBuffer.append((i==0?"":",")+column.name);
		}
		
		stringBuffer.append(RN);
		stringBuffer.append(itemBuffer);
		
		stringBuffer.append(RN);
		stringBuffer.append(")"
		 +"values" 
		+"(") ;
		
		
		level=0;
		itemBuffer=new StringBuffer();
		for(int i=0;i<all.size();i++){
			Column column=all.get(i);
			if(column.level>level){
				itemBuffer.append(RN);
				level=column.level;
			}
			itemBuffer.append(
					(i==0?"":",")
					+"#{"+column.property+"}");
		}
		stringBuffer.append(RN);
		stringBuffer.append(itemBuffer);
		
		stringBuffer.append(RN);
		stringBuffer.append(")"+"\n"+"</insert>");
		return stringBuffer;
	}
	
	private StringBuffer getUpdate(){
		StringBuffer stringBuffer=new StringBuffer();
		
		stringBuffer.append("<update id=\"update\">");
		
		stringBuffer.append(RN);
		stringBuffer.append("update "+tableName);
		stringBuffer.append(RN);
		stringBuffer.append("<set>  ");
//		int level=0;
		StringBuffer itemBuffer=new StringBuffer();
		
		//add update id 
		itemBuffer.append(UPDATEID.name+" =#{"+UPDATEID.property+"}");
		
		// add update time
		stringBuffer.append(RN);
		itemBuffer.append(","+UPDATETIME.name+" =#{"+UPDATETIME.property+"}");
		
		for(int i=0;i<all.size();i++){
			Column column=all.get(i);
			if(!CREATEID.name.equals(column.name)
					&&!CREATETIME.name.equals(column.name)
					&&!DELETED.name.equals(column.name)
					&&!ID.name.equals(column.name)
					&&!UPDATEID.name.equals(column.name)
					&&!UPDATETIME.name.equals(column.name)
					){
				itemBuffer.append(RN);
				String item="<if test=\""+column.property+"!=null\">"
						+","+column.name+" =#{"+column.property+"}"
					    +"</if>";
				itemBuffer.append(item);
			}
		}
		stringBuffer.append(itemBuffer);
		
		stringBuffer.append(RN);
		stringBuffer.append("</set>  ");
		
		stringBuffer.append(RN);
		stringBuffer.append("where ID=#{id}");
		
		stringBuffer.append(RN);
		stringBuffer.append("</update>");
		
		
		return stringBuffer;
	}
	
	private StringBuffer getSelectfor(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<sql id=\"selectfor\">");
		stringBuffer.append(RN);
		stringBuffer.append("select a.* from "+tableName+" a ");
		stringBuffer.append(RN);
		stringBuffer.append("</sql>");
		return stringBuffer;
	}
	
	private StringBuffer getGet(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<select id=\"get\"   resultMap=\""+getResultMapId()+"\"  >");
		stringBuffer.append(RN);
		stringBuffer.append("<include refid=\"selectfor\"></include> ");
		stringBuffer.append(RN);
		stringBuffer.append("where a.id=#{param1}");
		stringBuffer.append(RN);
		stringBuffer.append("</select>");
		return stringBuffer;
	}
	
	private StringBuffer getGetsByPage(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<select id=\"getsByPage\"   resultMap=\""+getResultMapId()+"\"  parameterType=\""+JPagination.class.getName()+"\">");
		stringBuffer.append(RN);
		stringBuffer.append("select * from "+tableName+"  ");
		stringBuffer.append(RN);
		stringBuffer.append("<if test=\"page.sortColumn!=null \">");
		stringBuffer.append(RN);
		stringBuffer.append("order by ${page.sortColumn} ${page.sortType}   ");
		stringBuffer.append(RN);
		stringBuffer.append("</if>");
		stringBuffer.append(RN);
		stringBuffer.append("</select>");
		return stringBuffer;
	}
	
	private StringBuffer getMarkDeleted(){
		
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<delete id=\"markDeleted\" >");
		stringBuffer.append(RN);
		stringBuffer.append("update "+tableName+" set DELETED='Y' where ID=#{id}");
		stringBuffer.append(RN);
		stringBuffer.append("</delete>");
		return stringBuffer;
	}
	
	public <T extends JBaseModel> MapperXML generate(Class<?> namespace,Class<T> model ){
		
		setNamespaceClass(namespace);
		
		setModelClass(model);
		
		// set namespace
		setNamespace(namespace);
		
		//set table name
		setTableName(model);
		
		//set column
		setColumns(model);
		
		mapper=getMapper();
		
		return this;
	}
	
	
	
	public void write(JFile file) throws Exception{
		file.write(mapper.toString().getBytes("UTF-8"));
	}
	
	private StringBuffer getMapper(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuffer.append(RN);
		stringBuffer.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> ");
		stringBuffer.append(RN);
		stringBuffer.append("<mapper namespace=\""+namespace+"\">");
		
		
		stringBuffer.append(RN);
		stringBuffer.append(getResultMap());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getSave());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getUpdate());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getMarkDeleted());

		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getGet());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getSelectfor());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append(getGetsByPage());
		
		stringBuffer.append(RN);
		stringBuffer.append(RN);
		stringBuffer.append("</mapper>");
		return stringBuffer;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
