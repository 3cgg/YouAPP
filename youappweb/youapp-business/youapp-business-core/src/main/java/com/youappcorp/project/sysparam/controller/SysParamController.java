package com.youappcorp.project.sysparam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.service.SysParamService;
import com.youappcorp.project.sysparam.vo.SysParamAddInVO;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;
import com.youappcorp.project.sysparam.vo.SysParamDetailOutVO;
import com.youappcorp.project.sysparam.vo.SysParamEditInVO;
import com.youappcorp.project.sysparam.vo.SysParamRecordOutVO;
import com.kcfy.platform.server.kernal.Copy;
import com.kcfy.platform.server.kernal.mapping.ControllerSupport;
import com.kcfy.platform.server.kernal.model.InvokeResult;
import com.kcfy.platform.server.kernal.model.JPage;
import com.kcfy.platform.server.kernal.model.JPageUtil;
import com.kcfy.platform.server.kernal.model.SimplePageRequest;
import com.kcfy.platform.server.kernal.model.SimplePageRequestVO;
import com.kcfy.platform.server.kernal.parameter.annotation.ParamValidation4Controller;
import com.kcfy.platform.server.kernal.service.JServiceLazyProxy;
import com.kcfy.platform.server.kernal.service.ServiceContext;

/**
 * @author JIAZJ
 */
@Controller
@RequestMapping("/sysparam")
public class SysParamController extends ControllerSupport {

	private SysParamService sysParamService = JServiceLazyProxy.proxy(SysParamService.class);
	
	@ResponseBody
	@RequestMapping("/saveSysParam")
	public InvokeResult saveSysParam(ServiceContext serviceContext, SysParamAddInVO sysParamAddInVO) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=Copy.simpleCopy(sysParamAddInVO, SysParam.class);
		sysParamService.saveSysParam(serviceContext, sysParam);
		return InvokeResult.success(sysParam.getId());
	}
	
	@ResponseBody
	@RequestMapping("/updateSysParam")
	public InvokeResult updateSysParam(ServiceContext serviceContext, SysParamEditInVO sysParamEditInVO) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=Copy.simpleCopy(sysParamEditInVO, SysParam.class);
		sysParamService.updateSysParam(serviceContext, sysParam);
		return InvokeResult.success(sysParam.getId());
	}
	
	@ResponseBody
	@RequestMapping("/getSysParamById")
	public InvokeResult getSysParamById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the SysParam or nothing.
		SysParam sysParam=sysParamService.getSysParamById(serviceContext, id);
		SysParamDetailOutVO sysParamDetailOutVO=null;
		if(sysParam!=null){
			sysParamDetailOutVO=Copy.simpleCopy(sysParam, SysParamDetailOutVO.class);
		}
		return InvokeResult.success(sysParamDetailOutVO);
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteSysParamById")
	public InvokeResult deleteSysParamById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the SysParam or nothing.
		sysParamService.deleteSysParamById(serviceContext, id);
		return InvokeResult.success(true);
	}
	
	
	@ResponseBody
	@RequestMapping("/getSysParamsByPage")
	public InvokeResult getSysParamsByPage(ServiceContext serviceContext, SysParamCriteriaInVO carCriteriaInVO,SimplePageRequestVO simplePageRequestVO ) throws Exception{
		// do something validation on the SysParam or nothing.
		JPage<SysParam> page=sysParamService.getSysParams(serviceContext, carCriteriaInVO, 
				new SimplePageRequest(simplePageRequestVO.getPage(), simplePageRequestVO.getSize()));
		List<SysParam> content=page.getContent();
		List<SysParamRecordOutVO> outContent=new ArrayList<SysParamRecordOutVO>();
		for(SysParam sysParam:content){
			SysParamRecordOutVO sysParamRecordOutVO= Copy.simpleCopy(sysParam, SysParamRecordOutVO.class);
			outContent.add(sysParamRecordOutVO);
		}
		JPageUtil.replaceConent(page, outContent);
		return InvokeResult.success(page);
	}
	
	
	
	
}
