package ${repoModel.repoPackage};

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import ${repoModel.modelFullClassName};

@Repository(value="jpa${repoModel.modelSimpleClassName}JPARepo")
public interface ${repoModel.modelSimpleClassName}JPARepo extends JSpringJpaRepository<${repoModel.modelSimpleClassName},String>{
	
}
