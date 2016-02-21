package j.jave.kernal.jave.model.support.detect;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.support.detect.JFieldDetector;

import java.util.List;

public class JModelDetect {
	
	private static JModelDetect modelDetect=new JModelDetect();
	
	private JModelDetect(){
		
	}
	
	public static JModelDetect get(){
		return modelDetect;
	}
	
	public List<JColumnInfo> getColumnInfos(JBaseModel baseModel){
		JFieldDetector<JColumnInfo> fieldDetect=new JFieldDetector<JColumnInfo>(new JColumnFieldInfo());
		fieldDetect.setFieldFilter(new JColumnFieldFilter());
		fieldDetect.detect(baseModel.getClass());
		return fieldDetect.getFieldInfos();
	}
	
	public List<JColumnInfo> getColumnInfos(Class<?> baseModelClazz){
		JFieldDetector<JColumnInfo> fieldDetect=new JFieldDetector<JColumnInfo>(new JColumnFieldInfo());
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
