package me.bunny.kernel._c.model.support.detect;

import java.util.List;

import me.bunny.kernel._c.model.JBaseModel;

public abstract class JAbstractGetOnModel implements JGetOnModel {

	protected final Class<? extends JBaseModel> baseModelClass;
	
	protected final List<JColumnInfo> columnInfos; 
	
	public JAbstractGetOnModel(Class<? extends JBaseModel> baseModelClass){
		this.baseModelClass=baseModelClass;
		columnInfos=JModelDetect.get().getColumnInfos(baseModelClass);
	}
	
}
