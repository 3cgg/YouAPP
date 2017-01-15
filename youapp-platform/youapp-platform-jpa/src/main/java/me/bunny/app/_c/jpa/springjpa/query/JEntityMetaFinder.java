package me.bunny.app._c.jpa.springjpa.query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.reflect.JPropertyNotFoundException;
import me.bunny.kernel._c.support.JFinder;

public class JEntityMetaFinder implements JFinder<JEntityModelMeta>{

	private Class<?> entityClass;
	
	private JEntityModelMeta entityModelMeta;
	
	public JEntityMetaFinder(Class<?> entityClass) {
		this.entityClass=entityClass;
		entityModelMeta=new JEntityModelMeta(entityClass);
	}
	
	@Override
	public JEntityModelMeta find() {
		
		List<JEntityColumnMeta> columnMetas=findColumns();
		entityModelMeta.setColumnMetas(columnMetas);
		return entityModelMeta;
	}
	
	private List<JEntityColumnMeta> findColumns(){
		List<JEntityColumnMeta> columnMetas=new ArrayList<JEntityColumnMeta>(); 
		List<Field> fields=JClassUtils.getFields(entityClass, true);
		Set<String> calc=new HashSet<String>();
		for(Field  field:fields){
			String fieldName=field.getName();
			if(calc.contains(fieldName)){
				break;
			}
			calc.add(fieldName);
			Method getter=null;
			try{
				getter=JClassUtils.findGetterMethod(entityClass, fieldName);
			}catch(JPropertyNotFoundException e){
			}
			String columnName=null;
			//try getter
			if(getter!=null){
				Column column=getter.getAnnotation(Column.class);
				if(column!=null){
					columnName=column.name();
				}
			}
			
			//try field
			if(columnName==null){
				Column column=field.getAnnotation(Column.class);
				if(column!=null){
					columnName=column.name();
				}
			}
			
			if(columnName==null){
				continue;
			}
			
			JEntityColumnMeta columnMeta=new JEntityColumnMeta();
			columnMeta.setAnnotations(field.getAnnotations());
			columnMeta.setClazz(entityClass);
			columnMeta.setColumn(columnName);
			columnMeta.setField(field);
			columnMeta.setFieldName(field.getName());
			columnMeta.setProperty(field.getName());
			columnMeta.setSetterMethodName(JClassUtils.getSetterMethodName(field));
			columnMeta.setGetterMethodName(JClassUtils.getGetterMethodName(field));
			columnMetas.add(columnMeta);
			
		}
		
		return columnMetas;
	}
	
}
