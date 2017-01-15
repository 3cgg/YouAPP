package com.youappcorp.template.ftl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.youappcorp.template.ftl.ui.UITemplateUIContext;

import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JStringUtils;

public class DefaultInternalConfig implements InternalConfig{
	
	private String modelPath;
	
	private InnerCfg innerCfg;
	
	private Config config;
	
	public DefaultInternalConfig(Config config) {
		this.config=config;
		this.modelPath=config.getModelPath();
		init(modelPath);
	}
	
	@Override
	public String javaRelativePath() {
		return innerCfg.javaRelativePath;
	}
	
	@Override
	public String uiRelativePath() {
		return innerCfg.uiRelativePath;
	}
	
	
	@Override
	public String basePackage() {
		return modelPackage().substring(0, modelPackage().lastIndexOf('.'));
	}

	@Override
	public String servicePackage() {
		return basePackage()+".service";
	}

	@Override
	public String repoPackage() {
		return basePackage()+".jpa";
	}

	@Override
	public String voPackage() {
		return basePackage()+".vo";
	}

	@Override
	public String controllerPackage() {
		return basePackage()+".controller";
	}

	@Override
	public String modelPackage() {
		return innerCfg.modelPackage;
	}
	
	
	@Override
	public List<String> modelNames() {
		return innerCfg.modelNames;
	}
	
	@Override
	public String controllerBaseMapping() {
		return innerCfg.controllerBaseMapping;
	}

	@Override
	public Iterator<ModelConfig> iterator() {
		return innerCfg.modelConfigs.iterator();
	}
	
	private static class DefaultModelConfig implements ModelConfig{

		private String modelName;
		
		private InternalConfig internalConfig;
		
		private ModelModel modelModel;
		
		private RepoModel repoModel;
		
		private InternalServiceModel internalServiceModel;
		
		private CriteriaModel criteriaModel;
		
		private ModelRecordModel modelRecordModel;
		
		private ServiceModel serviceModel;
		
		private ServiceImplModel serviceImplModel;
		
		private ModelRecordVOModel modelRecordVOModel;
		
		private ControllerModel controllerModel;
		
		private UITemplateUIContext uiTemplateUIContext;
		
		public DefaultModelConfig(InternalConfig internalConfig,String modelName) {
			this.internalConfig=internalConfig;
			this.modelName=modelName;
		}
		
		@Override
		public InternalConfig internalConfig() {
			return internalConfig;
		}

		@Override
		public String modelName() {
			return modelName;
		}

		@Override
		public String pageMethodName() {
			return "get"+modelName()+"sByPage";
		}

		@Override
		public String saveMethodName() {
			return "save"+modelName();
		}

		@Override
		public String updateMethodName() {
			return "update"+modelName();
		}

		@Override
		public String deleteMethodName() {
			return "delete"+modelName();
		}
		
		
		@Override
		public String deleteByIdMethodName() {
			return "delete"+modelName()+"ById";
		}

		@Override
		public String getMethodName() {
			return "get"+modelName()+"ById";
		}

		@Override
		public ModelModel modelModel() {
			return modelModel;
		}

		@Override
		public void setModelModel(ModelModel modelModel) {
			this.modelModel=modelModel;
		}

		@Override
		public RepoModel repoModel() {
			return repoModel;
		}

		@Override
		public void setRepoModel(RepoModel repoModel) {
			this.repoModel=repoModel;
		}

		@Override
		public InternalServiceModel internalServiceModel() {
			return internalServiceModel;
		}

		@Override
		public void setInternalServiceModel(
				InternalServiceModel internalServiceModel) {
			this.internalServiceModel=internalServiceModel;
		}
		
		@Override
		public CriteriaModel criteriaModel() {
			return criteriaModel;
		}
		
		@Override
		public void setCriteriaModel(CriteriaModel criteriaModel) {
			this.criteriaModel=criteriaModel;
		}

		@Override
		public ModelRecordModel modelRecordModel() {
			return modelRecordModel;
		}

		@Override
		public void setModelRecordModel(ModelRecordModel modelRecordModel) {
			this.modelRecordModel=modelRecordModel;
		}

		@Override
		public ServiceModel serviceModel() {
			return serviceModel;
		}

		@Override
		public void setServiceModel(ServiceModel serviceModel) {
			this.serviceModel=serviceModel;
		}

		@Override
		public ServiceImplModel serviceImplModel() {
			return this.serviceImplModel;
		}

		@Override
		public void setServiceImplModel(ServiceImplModel serviceImplModel) {
			this.serviceImplModel=serviceImplModel;
		}

		@Override
		public ControllerModel controllerModel() {
			return controllerModel;
		}

		@Override
		public void setControllerModel(ControllerModel controllerModel) {
			this.controllerModel=controllerModel;
		}

		@Override
		public ModelRecordVOModel modelRecordVOModel() {
			return modelRecordVOModel;
		}

		@Override
		public void setModelRecordVOModel(ModelRecordVOModel modelRecordVOModel) {
			this.modelRecordVOModel=modelRecordVOModel;
		}

		@Override
		public UITemplateUIContext uiTemplateUIContext() {
			return uiTemplateUIContext;
		}

		@Override
		public void setUITemplateUIContext(
				UITemplateUIContext uiTemplateUIContext) {
			this.uiTemplateUIContext=uiTemplateUIContext;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void init(String modelPath){
		JAssert.isNotNull(modelPath,"please set at latest one model package.");
		this.modelPath=modelPath.replace("\\", "/");
		InnerCfg innerCfg=new InnerCfg();
		setModelPackage(innerCfg);
		setJavaRelativePath(innerCfg);
		setModelNames(innerCfg);
		setControllerBaseMapping(innerCfg);
		setModelConfigs(innerCfg);
		this.innerCfg=innerCfg;
	}
	
	private void setJavaRelativePath(InnerCfg innerCfg){
		String modelPackage=innerCfg.modelPackage;
		String javaRelativePath=modelPath.substring(0, modelPath.indexOf(modelPackage.replace(".", "/")));
		innerCfg.javaRelativePath=javaRelativePath;
	}
	
	private void setModelConfigs(InnerCfg innerCfg){
		List<String> modelNames=innerCfg.modelNames;
		List<ModelConfig> modelConfigs=new ArrayList<InternalConfig.ModelConfig>();
		for(String modelName:modelNames){
			ModelConfig config=new DefaultModelConfig(this, modelName);
			modelConfigs.add(config);
		}
		innerCfg.modelConfigs=modelConfigs;
	}
	
	private void setModelPackage(InnerCfg innerCfg){
		File file=new File(modelPath);
		String modelPath=this.modelPath;
		if(file.exists()&&file.isFile()){
			modelPath=file.getParentFile().getAbsolutePath().replace("\\", "/");
		}
		
		String split="/src/main/java/";
		if(!modelPath.contains(split)){
			split="/src/test/java/";
		}
		if(!modelPath.contains(split)){
			split="";
		}
		if(JStringUtils.isNotNullOrEmpty(split)){
			innerCfg.modelPackage=modelPath.split(split)[1].replace('/', '.');
		}
		else{
			split="/com/youappcorp/project";
			innerCfg.modelPackage=modelPath.substring(modelPath.indexOf(split)).replace('/', '.');
		}
	}
	
	private void setModelNames(InnerCfg innerCfg){
		List<String> names=new ArrayList<String>();
		File file=new File(modelPath);
		if(file.exists()&&file.isDirectory()){
			File[] files=file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".java");
				}
			});
			
			for(File fileItem:files){
				names.add(fileItem.getName().split("[.]")[0]);
			}
		}
		else if(file.exists()&&file.isFile()){
			names.add(file.getName().split("[.]")[0]);
		}
		innerCfg.modelNames=names;
	}
	
	private void setControllerBaseMapping(InnerCfg innerCfg){
		String[] pkgs=innerCfg.modelPackage.split("[.]");
		innerCfg.controllerBaseMapping="/"+pkgs[pkgs.length-2];
	}
	
	private class InnerCfg{
		
		private String javaRelativePath;
		
		private String uiRelativePath;
		
		private String modelPackage;
		
		private List<String> modelNames;
		
		private String controllerBaseMapping;
		
		private List<ModelConfig> modelConfigs;
		
		private List<FileWrapper> files=new ArrayList<FileWrapper>();
		
	}

	@Override
	public void addFile(FileWrapper fileWrapper) {
		innerCfg.files.add(fileWrapper);
	}

	@Override
	public List<FileWrapper> files() {
		return innerCfg.files;
	}

	@Override
	public Config passConfig() {
		return config;
	}

	
	
}
