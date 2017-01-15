package me.bunny.kernel._c.model.support.validate;

import java.util.List;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.model.support.detect.JColumnInfo;
import me.bunny.kernel._c.model.support.detect.JModelDetect;
import me.bunny.kernel._c.support.validate.JValidator;

/**
 * validate whether the field is column or not.
 * @author J
 *
 */
public class JFieldIsColumnValidator implements JValidator<String> {
	
	private List<JColumnInfo> columnInfos;
	
	public JFieldIsColumnValidator(Class<? extends JBaseModel> baseModelClass){
		columnInfos=JModelDetect.get().getColumnInfos(baseModelClass);
	}
	
	@Override
	public boolean validate(String object) {
		for(int i=0;i<columnInfos.size();i++){
			JColumnInfo columnInfo=columnInfos.get(i);
			if(columnInfo.getField().getName().equals(object.trim())){
				return true;
			}
		}
		return false;
	}
	
}
