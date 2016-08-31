package com.youappcorp.project.codetable.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.codetable.service.CodeTableService;
import com.youappcorp.project.codetable.vo.ParamCriteriaInVO;
import com.youappcorp.project.codetable.vo.ParamRecordVO;

@Controller
@RequestMapping(value="/codetablemanager")
public class CodeTableManagerController extends SimpleControllerSupport {
	
	@Autowired
	private CodeTableService codeTableService;
	
	private ParamRecordVO genParamRecordOutVO(ParamType paramType){
		ParamRecordVO paramRecordOutVO=new ParamRecordVO();
		paramRecordOutVO.setCode(paramType.getCode());
		paramRecordOutVO.setCodeName(paramType.getName());
		paramRecordOutVO.setDescription(paramType.getDescription());
		paramRecordOutVO.setId(paramType.getId());
		return paramRecordOutVO;
	}
	
	private ParamRecordVO genParamRecordOutVO(ParamCode paramCode){
		ParamRecordVO paramRecordOutVO=new ParamRecordVO();
		paramRecordOutVO.setType(paramCode.getType());
		paramRecordOutVO.setCode(paramCode.getCode());
		paramRecordOutVO.setCodeName(paramCode.getName());
		paramRecordOutVO.setDescription(paramCode.getDescription());
		paramRecordOutVO.setId(paramCode.getId());
		return paramRecordOutVO;
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamTypesByPage")
	public ResponseModel getParamTypesByPage(ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable){
		JPage<ParamType> paramTypesPage= codeTableService.getAllParamTypesByPage(paramCriteria,simplePageable);
		toTypeViewPage(paramTypesPage);
		return ResponseModel.newSuccess().setData(paramTypesPage);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamTypes")
	public ResponseModel getParamTypes(ParamCriteriaInVO paramCriteria){
		List<ParamType> paramTypes= codeTableService.getAllParamTypes(paramCriteria);
		return ResponseModel.newSuccess().setData(toTypeViewPage(paramTypes));
	}

	private void toTypeViewPage(JPage<ParamType> paramTypesPage) {
		List<ParamType> paramTypes=paramTypesPage.getContent();
		List<ParamRecordVO> paramRecordOutVOs = toTypeViewPage(paramTypes);
		paramTypesPage.setContent(paramRecordOutVOs);
	}

	private List<ParamRecordVO> toTypeViewPage(List<ParamType> paramTypes) {
		List<ParamRecordVO> paramRecordOutVOs=new ArrayList<ParamRecordVO>();
		for(ParamType paramType:paramTypes){
			paramRecordOutVOs.add(genParamRecordOutVO(paramType));
		}
		return paramRecordOutVOs;
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByPage")
	public ResponseModel getParamCodesByPage(ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= codeTableService.getAllParamCodesByPage(paramCriteria,simplePageable);
		toCodeViewPage(paramCodesPage);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}

	private void toCodeViewPage(JPage<ParamCode> paramCodesPage) {
		List<ParamRecordVO> paramRecordOutVOs=new ArrayList<ParamRecordVO>();
		for(ParamCode paramCode:paramCodesPage.getContent()){
			paramRecordOutVOs.add(genParamRecordOutVO(paramCode));
		}
		paramCodesPage.setContent(paramRecordOutVOs);
	}
	
	@ResponseBody
	@RequestMapping(value="/getParamCodesByTypePage")
	public ResponseModel getParamCodesByTypePage(String type,JSimplePageable simplePageable){
		JPage<ParamCode> paramCodesPage= codeTableService.getAllParamCodesByTypeByPage(type,simplePageable);
		toCodeViewPage(paramCodesPage);
		return ResponseModel.newSuccess().setData(paramCodesPage);
	}
	
	@RequestMapping("/getParamTypeById")
	public ResponseModel getParamTypeById( String id){
		ParamType paramType=codeTableService.getParamTypeById( id);
		return ResponseModel.newSuccess(genParamRecordOutVO(paramType));
	}
	
	@RequestMapping("/getParamCodeById")
	public ResponseModel getParamCodeById( String id){
		ParamCode paramCode=codeTableService.getParamCodeById( id);
		return ResponseModel.newSuccess(genParamRecordOutVO(paramCode));
	}

	@RequestMapping("/deleteParamTypeById")
	public ResponseModel deleteParamTypeById( String id){
		codeTableService.deleteParamTypeById( id);
		return ResponseModel.newSuccess(true);
	}
	
	@RequestMapping("/deleteParamCodeById")
	public ResponseModel deleteParamCodeById( String id){
		codeTableService.deleteParamCodeById( id);
		return ResponseModel.newSuccess(true);
	}
	
	@RequestMapping("/updateParamCode")
	public ResponseModel updateParamCode( ParamRecordVO paramCodeRecord){
		ParamCode paramCode = toParamCode(paramCodeRecord);
		codeTableService.updateParamCode( paramCode);
		return ResponseModel.newSuccess(true);
	}

	private ParamCode toParamCode(ParamRecordVO paramCodeRecord) {
		ParamCode paramCode=new  ParamCode();
		paramCode.setId(paramCodeRecord.getId());
		paramCode.setCode(paramCodeRecord.getCode());
		paramCode.setName(paramCodeRecord.getCodeName());
		paramCode.setDescription(paramCodeRecord.getDescription());
		paramCode.setType(paramCodeRecord.getType());
		return paramCode;
	}
	
	@RequestMapping("/updateParamType")
	public ResponseModel updateParamType( ParamRecordVO paramCodeRecord) {
		ParamType paramType = toParamType(paramCodeRecord);
		codeTableService.updateParamType( paramType);
		return ResponseModel.newSuccess(true);
	}

	private ParamType toParamType(ParamRecordVO paramCodeRecord) {
		ParamType paramType=new  ParamType();
		paramType.setId(paramCodeRecord.getId());
		paramType.setCode(paramCodeRecord.getCode());
		paramType.setName(paramCodeRecord.getCodeName());
		paramType.setDescription(paramCodeRecord.getDescription());
		return paramType;
	}
	
	@RequestMapping("/saveParamCode")
	public ResponseModel saveParamCode( ParamRecordVO paramRecordVO){
		ParamCode paramCode=toParamCode(paramRecordVO);
		codeTableService.saveParamCode( paramCode);
		return ResponseModel.newSuccess(paramCode.getId());
	}
	
	@RequestMapping("/saveParamType")
	public ResponseModel saveParamType( ParamRecordVO paramRecordVO){
		ParamType paramType=toParamType(paramRecordVO);
		codeTableService.saveParamType( paramType);
		return ResponseModel.newSuccess(paramType.getId());
	}
	
	
}
