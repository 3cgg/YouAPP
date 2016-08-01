package com.youappcorp.project.runtimeurl.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.support._package.JDefaultParamMeta;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlService;
import com.youappcorp.project.runtimeurl.vo.MethodInfoVO;
import com.youappcorp.project.runtimeurl.vo.MockEditVO;
import com.youappcorp.project.runtimeurl.vo.MockInfoVO;
import com.youappcorp.project.runtimeurl.vo.ParamInfoVO;
import com.youappcorp.project.runtimeurl.vo.RuntimeUrlCriteriaInVO;
import com.youappcorp.project.runtimeurl.vo.RuntimeUrlRecordOutVO;

/**
 * @author JIAZJ
 */
@Controller
@RequestMapping("/runtimeurl")
public class RuntimeUrlController extends SimpleControllerSupport {

	@Autowired
	private RuntimeUrlService runtimeUrlService;
	
	@ResponseBody
	@RequestMapping("/updateMockState")
	public ResponseModel updateMockState(ServiceContext serviceContext, MockEditVO mockEditVO ) throws Exception{
		// do something validation on the SysParam or nothing.
		runtimeUrlService.updateMockState(serviceContext, mockEditVO.getUrl(), mockEditVO.isMock());
		return ResponseModel.newSuccess(true);
	}
	
	private RuntimeUrlRecordOutVO genRuntimeUrlRecordOutVO(RuntimeUrl runtimeUrl){
		RuntimeUrlRecordOutVO runtimeUrlRecordOutVO=new RuntimeUrlRecordOutVO();
		runtimeUrlRecordOutVO.setId(runtimeUrl.getId());
		runtimeUrlRecordOutVO.setDesc(runtimeUrl.getDesc());
		runtimeUrlRecordOutVO.setName(runtimeUrl.getName());
		runtimeUrlRecordOutVO.setUrl(runtimeUrl.getUrl());
		
		//mock info
		MockInfoVO mockInfoVO=new MockInfoVO();
		mockInfoVO.setUrl(runtimeUrlRecordOutVO.getUrl());
		mockInfoVO.setMock(runtimeUrl.getMockInfo().isMock());
		runtimeUrlRecordOutVO.setMockInfo(mockInfoVO);
		
		JDefaultMethodMeta defaultMethodMeta=runtimeUrl.getMethodMeta();
		
		//method info
		MethodInfoVO methodInfoVO=new MethodInfoVO();
		methodInfoVO.setMethodName(defaultMethodMeta.getMethodName());
		methodInfoVO.setUrl(runtimeUrlRecordOutVO.getUrl());
		
		//param info
		JDefaultParamMeta[] defaultParamMetas=defaultMethodMeta.getParamMetas();
		ParamInfoVO[] paramInfoVOs=new ParamInfoVO[defaultParamMetas.length];
		int i=0;
		for(JDefaultParamMeta defaultParamMeta:defaultParamMetas){
			ParamInfoVO paramInfoVO=new ParamInfoVO();
			paramInfoVO.setName(defaultParamMeta.getName());
			paramInfoVO.setIndex(defaultParamMeta.getIndex());
			paramInfoVO.setType(defaultParamMeta.getType().getName());
			paramInfoVOs[i++]=paramInfoVO;
		}
		methodInfoVO.setParamInfos(paramInfoVOs);
		runtimeUrlRecordOutVO.setMethodInfo(methodInfoVO);
		return runtimeUrlRecordOutVO;
	}
	
	@ResponseBody
	@RequestMapping("/getRuntimeUrlById")
	public ResponseModel getRuntimeUrlById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the SysParam or nothing.
		RuntimeUrl runtimeUrl= runtimeUrlService.getRuntimeUrlById(serviceContext, id);
		RuntimeUrlRecordOutVO runtimeUrlRecordOutVO=genRuntimeUrlRecordOutVO(runtimeUrl);
		return ResponseModel.newSuccess(runtimeUrlRecordOutVO);
	}
	
	@ResponseBody
	@RequestMapping("/getRuntimeUrlByUrl")
	public ResponseModel getRuntimeUrlByUrl(ServiceContext serviceContext, String url) throws Exception{
		// do something validation on the SysParam or nothing.
		RuntimeUrl runtimeUrl= runtimeUrlService.getRuntimeUrlById(serviceContext, url);
		RuntimeUrlRecordOutVO runtimeUrlRecordOutVO=genRuntimeUrlRecordOutVO(runtimeUrl);
		return ResponseModel.newSuccess(runtimeUrlRecordOutVO);
	}
	
	@ResponseBody
	@RequestMapping("/getRuntimeUrlsByPage")
	public ResponseModel getRuntimeUrlsByPage(ServiceContext serviceContext, RuntimeUrlCriteriaInVO runtimeUrlCriteriaInVO ,JSimplePageable simplePageable ) throws Exception{
		// do something validation on the SysParam or nothing.
		JPage<RuntimeUrl> page=runtimeUrlService.getRuntimeUrlsByPage(serviceContext, runtimeUrlCriteriaInVO, simplePageable);
		
		List<RuntimeUrl> content=page.getContent();
		List<RuntimeUrlRecordOutVO> outContent=new ArrayList<RuntimeUrlRecordOutVO>();
		for(RuntimeUrl runtimeUrl:content){
			RuntimeUrlRecordOutVO runtimeUrlRecordOutVO= genRuntimeUrlRecordOutVO(runtimeUrl);
			outContent.add(runtimeUrlRecordOutVO);
		}
		page.setContent(outContent);
		return ResponseModel.newSuccess(page);
	}
	
}
