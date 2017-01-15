package me.bunny.kernel._c.model.support.detect;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.model.support.JSQLType;

public class JGetSQLTypeOnModel extends JAbstractGetOnModel {

	public JGetSQLTypeOnModel(Class<? extends JBaseModel> baseModelClass) {
		super(baseModelClass);
	}

	@Override
	public JSQLType get(String property) {
		for(int i=0;i<columnInfos.size();i++){
			JColumnInfo columnInfo=columnInfos.get(i);
			if(columnInfo.getField().getName().equals(property.trim())){
				return columnInfo.getColumn().type();
			}
		}
		return null;
	}

}
