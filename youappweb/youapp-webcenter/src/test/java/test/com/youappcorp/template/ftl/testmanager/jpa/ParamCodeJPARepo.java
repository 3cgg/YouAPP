package test.com.youappcorp.template.ftl.testmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;

@Repository(value="ParamCodeJPARepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode,String>{
	
}
