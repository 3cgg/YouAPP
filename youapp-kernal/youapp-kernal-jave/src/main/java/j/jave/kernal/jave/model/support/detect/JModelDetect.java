package j.jave.kernal.jave.model.support.detect;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.support.detect.JFieldDetector;
import j.jave.kernal.jave.support.detect.JFieldOnSingleClassDetector;

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
		JFieldDetector<JColumnInfo> fieldDetect=new JFieldOnSingleClassDetector<JColumnInfo>(new JColumnFieldInfo());
		fieldDetect.setFieldFilter(new JColumnFieldFilter());
		fieldDetect.detect(baseModelClazz);
		return fieldDetect.getFieldInfos();
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
