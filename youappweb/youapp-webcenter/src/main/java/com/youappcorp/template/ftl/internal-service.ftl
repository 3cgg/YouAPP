package ${internalServiceModel.servicePackage};

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${repoModel.repoClassName};
import ${modelModel.modelClassName};

@Service(value="${internalServiceModel.serviceSimpleClassName}.transation.jpa")
public class ${internalServiceModel.serviceSimpleClassName} extends InternalServiceSupport<${modelModel.modelSimpleClassName}>{

	@Autowired
	private ${repoModel.repoSimpleClassName} repo;
	
	@Override
	public JIPersist<?, ${modelModel.modelSimpleClassName}, String> getRepo() {
		return repo;
	}

}
