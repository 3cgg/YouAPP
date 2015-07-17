package j.jave.framework.commons.model.support.validate;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.detect.JColumnInfo;
import j.jave.framework.commons.model.support.detect.JModelDetect;
import j.jave.framework.commons.support.validate.JValidator;

import java.util.List;

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
