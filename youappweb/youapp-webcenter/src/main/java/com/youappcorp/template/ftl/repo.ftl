package ${repoModel.repoPackage};

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import ${modelModel.modelClassName};

@Repository(value="${repoModel.repoSimpleClassName}")
public interface ${repoModel.repoSimpleClassName} extends JSpringJpaRepository<${modelModel.modelSimpleClassName},String>{
	
}
