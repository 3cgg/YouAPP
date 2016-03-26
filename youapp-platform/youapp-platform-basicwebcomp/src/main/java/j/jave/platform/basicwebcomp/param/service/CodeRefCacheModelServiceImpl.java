package j.jave.platform.basicwebcomp.param.service;

import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.param.model.CodeTableCacheModel;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheModel;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheModelService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeRefCacheModelServiceImpl implements CodeRefCacheModelService {

	@Autowired
	private ParamService paramService;
	
	@Override
	public List<? extends CodeRefCacheModel> getResourceCacheModels() {
		List<CodeTableCacheModel> params= paramService.getCodeTableCacheModels(DefaultServiceContext.getDefaultServiceContext());
//		List<CodeRefCacheModel> codeRefCacheModels=new ArrayList<CodeRefCacheModel>();
//		if(JCollectionUtils.hasInCollect(params)){
//			for(CodeTableCacheModel param:params){
//				CodeTableCacheModel codeTableCacheModel=new CodeTableCacheModel();
//				codeTableCacheModel.setCode(param.getCode());
//				codeTableCacheModel.setType(param.getType());
//				codeTableCacheModel.setName(param.getName());
//				codeRefCacheModels.add(codeTableCacheModel);
//			}
//		}
		return params;
	}

}
