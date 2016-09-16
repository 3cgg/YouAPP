package ${repoModel.classPackage};

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import ${modelModel.className};

@Repository(value="${repoModel.simpleClassName}")
public interface ${repoModel.simpleClassName} extends JSpringJpaRepository<${modelModel.simpleClassName},String>{
	
}
