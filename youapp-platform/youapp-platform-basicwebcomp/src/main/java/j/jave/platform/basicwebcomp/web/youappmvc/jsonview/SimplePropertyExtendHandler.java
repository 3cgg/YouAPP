package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendBinder;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendable;

public class SimplePropertyExtendHandler implements DataModifyHandler {

	@Override
	public void handle(ResponseModel responseModel) {
		Object object=responseModel.getData();
		if(PropertyExtendable.class.isInstance(object)){
			//can extend property
			PropertyExtendBinder.bind(object);
		}
	}

}
