package com.youappcorp.project.unittest.controller;

import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youappcorp.project.unittest.model.TestModel;

@Controller
@RequestMapping(value="/testmanager")
public class TestController extends ControllerSupport {
	
	
	
	@RequestMapping(value="/show")
	public void show(){
		System.out.println("---------show method-----------");
	}
	@RequestMapping(value="/getCode")
	public ResponseModel getCode(@RequestParam(value="code") String code){
		ResponseModel responseModel=ResponseModel.newSuccess();
		TestModel testModel=new TestModel();
		testModel.setCode(code);
		responseModel.setData(testModel);
		return responseModel;
	}
	
	
	@RequestMapping(value="/test")
	public void test(User user,
			@RequestParam(value="userd") User userd,String key,TestModel testModel,
			String[] alist,
			Integer ss,
			Double[] ssdd,
			Map<String, Object> maps,Map<Integer, Object> mapsss){
		System.out.println(user.getId()+"[--]"+key);
	}
	
}
