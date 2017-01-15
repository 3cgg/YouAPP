package com.youappcorp.project.websupport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.codetable.service.CodeTableService;
import com.youappcorp.project.websupport.model.CodeTableCacheModel;

import me.bunny.app._c._web.web.cache.resource.coderef.CodeRefCacheModel;
import me.bunny.app._c._web.web.cache.resource.coderef.CodeRefCacheModelService;

@Service
public class CodeRefCacheModelServiceImpl implements CodeRefCacheModelService {

	@Autowired
	private CodeTableService paramService;
	
	@Override
	public List<? extends CodeRefCacheModel> getResourceCacheModels() {
		List<CodeTableCacheModel> params= paramService.getCodeTableCacheModels();
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
