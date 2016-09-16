package com.youappcorp.template.ftl;

import java.util.List;

public interface InternalConfig extends Iterable<InternalConfig.ModelConfig> {


	String basePackage();
	
	String servicePackage();
	
	String repoPackage();
	
	String voPackage();
	
	String controllerPackage();
	
	String modelPackage();
	
	/**
	 * file without .java, example User.java => User
	 * under the directory {@link Config#getModelPath()}
	 * @return
	 */
	List<String> modelNames();
	
	String controllerBaseMapping();
	
	interface ModelConfig{
		
		InternalConfig internalConfig();
		
		String modelName();
		
		String pageMapping();
		
		String saveMapping();
		
		String updateMapping();
		
		String deleteMapping();
		
		String getMapping();
		
		ModelModel modelModel();
		
		void setModelModel(ModelModel modelModel);
		
		RepoModel repoModel();
		
		void setRepoModel(RepoModel repoModel);
		
		InternalServiceModel internalServiceModel();
		
		void setInternalServiceModel(InternalServiceModel internalServiceModel);
	}

}
