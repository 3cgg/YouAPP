package ${internalServiceModel.classPackage};

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${repoModel.className};
import ${modelModel.className};

@Service(value="${internalServiceModel.simpleClassName}.transation.jpa")
public class ${internalServiceModel.simpleClassName} extends InternalServiceSupport<${modelModel.simpleClassName}>{

	@Autowired
	private ${repoModel.simpleClassName} repo;
	
	@Override
	public JIPersist<?, ${modelModel.simpleClassName}, String> getRepo() {
		return repo;
	}

}
