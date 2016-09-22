package com.youappcorp.template.ftl;

import java.util.List;

public interface InternalConfig extends Iterable<InternalConfig.ModelConfig> {

	Config passConfig();
	
	String javaRelativePath();
	
	String uiRelativePath();

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
	
	List<FileWrapper> files();
	
	void addFile(FileWrapper fileWrapper);
	
	interface ModelConfig{
		
		InternalConfig internalConfig();
		
		String modelName();
		
		String pageMethodName();
		
		String saveMethodName();
		
		String updateMethodName();
		
		String deleteMethodName();
		
		String deleteByIdMethodName();
		
		String getMethodName();
		
		ModelModel modelModel();
		
		void setModelModel(ModelModel modelModel);
		
		RepoModel repoModel();
		
		void setRepoModel(RepoModel repoModel);
		
		InternalServiceModel internalServiceModel();
		
		void setInternalServiceModel(InternalServiceModel internalServiceModel);
		
		CriteriaModel criteriaModel();
		
		void setCriteriaModel(CriteriaModel criteriaModel);
		
		ModelRecordModel modelRecordModel();
		
		void setModelRecordModel(ModelRecordModel modelRecordModel);
		
		ServiceModel serviceModel();
		
		void setServiceModel(ServiceModel serviceModel);
		
		ServiceImplModel serviceImplModel();
		
		void setServiceImplModel(ServiceImplModel serviceImplModel);
		
		ModelRecordVOModel modelRecordVOModel();
		
		void setModelRecordVOModel(ModelRecordVOModel modelRecordVOModel);
		
		ControllerModel controllerModel();
		
		void setControllerModel(ControllerModel controllerModel);
	}

}
