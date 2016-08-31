package com.youappcorp.project.extapp.extappsample.controller;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youappcorp.project.extapp.extappsample.vo.SampleRecordVO;

@Controller
@RequestMapping(value="/extapp/samplemanager")
public class ExtAppSampleController extends SimpleControllerSupport{
	
	private Map<String, SampleRecordVO> sampleRecords=new HashMap<String, SampleRecordVO>();

	
	@RequestMapping(value="/saveSampleRecord")
	public ResponseModel saveSampleRecord(SampleRecordVO sampleRecordVO) throws Exception {
		sampleRecordVO.setId(JUniqueUtils.unique());
		sampleRecords.put(sampleRecordVO.getId(), sampleRecordVO);
		return ResponseModel.newSuccess(sampleRecordVO.getId());
	}
	
	@RequestMapping(value="/getSampleRecordById")
	public ResponseModel getSampleRecordById(String id) throws Exception {
		return ResponseModel.newSuccess().setData(sampleRecords.get(id));
	}

	@RequestMapping(value="/getSampleRecordsByPage")
	public ResponseModel getSampleRecordsByPage(JSimplePageable simplePageable){
		List<SampleRecordVO> sampleRecordVOs=new ArrayList<SampleRecordVO>();
		for(SampleRecordVO sampleRecordVO:sampleRecords.values()){
			sampleRecordVOs.add(sampleRecordVO);
		}
		JPageImpl<SampleRecordVO> page=new JPageImpl<SampleRecordVO>();
		page.setTotalPageNumber(sampleRecordVOs.size()/simplePageable.getPageSize());
		page.setTotalRecordNumber(sampleRecordVOs.size());
		page.setContent(sampleRecordVOs);
		page.setPageable(simplePageable);
		return ResponseModel.newSuccess().setData(page);
	}
	
	@RequestMapping(value="/deleteSampleRecordById")
	public ResponseModel deleteSampleRecordById(String id){
		sampleRecords.remove(id);
		return ResponseModel.newSuccess();
	}

	@RequestMapping(value="/updateSampleRecord")
	public ResponseModel updateSampleRecord(SampleRecordVO sampleRecordVO) throws JServiceException{
		sampleRecords.put(sampleRecordVO.getId(), sampleRecordVO);
		return ResponseModel.newSuccess();
	}
	
	
}
