package com.youappcorp.project.unittest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.youappcorp.project.unittest.model.TestModel;
import com.youappcorp.project.usermanager.model.User;

import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.app._c._web.web.youappmvc.controller.SkipMappingCheck;

@Controller
@RequestMapping(value="/testmanager")
public class TestController extends SimpleControllerSupport {
	
	
	
	@RequestMapping(value="/show")
	@SkipMappingCheck
	public void show(){
		System.out.println("---------show method-----------");
	}
	@RequestMapping(value="/getCode")
	@SkipMappingCheck
	public ResponseModel getCode(@RequestParam(value="code") String code){
		ResponseModel responseModel=ResponseModel.newSuccess();
		TestModel testModel=new TestModel();
		testModel.setCode(code);
		
		testModel.getTestModelList().add(new TestModel(code));
		testModel.getTestModelList().add(new TestModel(code));
		
		testModel.getTestModelSet().add(new TestModel(code));
		testModel.getTestModelSet().add(new TestModel(code));
		
		testModel.getMap().put("A", new TestModel(code));
		testModel.getMap().put("B", new TestModel(code));
		
		testModel.getMapList().put("ALIST", testModel.getTestModelList());
		testModel.getMapList().put("BSET", testModel.getTestModelSet());
		
		Map<String, Object> objs=new HashMap<String, Object>();
		objs.put("FIRST", testModel);
		
		Map<String, Object> maps=new HashMap<String, Object>();
		maps.put("SE-LEV", "SE-LEV-VAL");
		objs.put("SECOND", maps);
		
		responseModel.setData(objs);
		return responseModel;
	}
	
	
	@RequestMapping(value="/test")
	@SkipMappingCheck
	public void test(User user,
			@RequestParam(value="userd") User userd,String key,TestModel testModel,
			String[] alist,
			Integer ss,
			Double[] ssdd,
			Map<String, Object> maps,Map<Integer, Object> mapsss){
		System.out.println(user.getId()+"[--]"+key);
	}
	
}
