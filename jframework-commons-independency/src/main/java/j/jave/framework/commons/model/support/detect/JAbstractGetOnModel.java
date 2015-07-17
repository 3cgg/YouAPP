package j.jave.framework.commons.model.support.detect;

import java.util.List;

import j.jave.framework.commons.model.JBaseModel;

public abstract class JAbstractGetOnModel implements JGetOnModel {

	protected final Class<? extends JBaseModel> baseModelClass;
	
	protected final List<JColumnInfo> columnInfos; 
	
	public JAbstractGetOnModel(Class<? extends JBaseModel> baseModelClass){
		this.baseModelClass=baseModelClass;
		columnInfos=JModelDetect.get().getColumnInfos(baseModelClass);
	}
	
}
