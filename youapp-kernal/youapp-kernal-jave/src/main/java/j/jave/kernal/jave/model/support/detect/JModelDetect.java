package j.jave.kernal.jave.model.support.detect;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.support._package.JFieldOnSingleClassFinder;

import java.util.List;

public class JModelDetect {
	
	private static JModelDetect modelDetect=new JModelDetect();
	
	private JModelDetect(){
		
	}
	
	public static JModelDetect get(){
		return modelDetect;
	}
	
	public List<JColumnInfo> getColumnInfos(JBaseModel baseModel){
		return getColumnInfos(baseModel.getClass());
	}
	
	public List<JColumnInfo> getColumnInfos(Class<?> baseModelClazz){
		JFieldOnSingleClassFinder<JColumnInfo> fieldOnSingleClassFinder=new JFieldOnSingleClassFinder<JColumnInfo>(baseModelClazz);
		fieldOnSingleClassFinder.setFieldFilter(new JColumnFieldFilter());
		fieldOnSingleClassFinder.setFieldInfo(new JColumnFieldInfo() );
		return fieldOnSingleClassFinder.find().getFieldInfos();
	}
	
	public JTableInfo getTableInfo(JBaseModel baseModel){
		JTable table=baseModel.getClass().getAnnotation(JTable.class);
		if(table==null) return null;
		JTableInfo tableInfo=new JTableInfo();
		tableInfo.setTable(table);
		tableInfo.setName(table.name());
		tableInfo.setSchema(table.schema());
		return tableInfo;
	}
	
	
}
