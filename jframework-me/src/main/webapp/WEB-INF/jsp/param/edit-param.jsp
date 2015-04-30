<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            参数修改
	            <small>
	            <a href="javascript:void(0)" 
	            	onclick='httpGET("/param.paramaction/toViewParam","id=${youappParam.id}")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                 onclick='httpGET("/param.paramaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">修改</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
									
				<form role="form"  id="editParamForm">
	                  <div class="box-body">
	                    <div class="form-group">
	                      <label for="functionId">类别</label>
	                      <input type="text" class="form-control" 
	                      	name="param.functionId"  value="${youappParam.functionId }"
	                      	id="functionId" placeholder="类别">
	                    </div>
	                    <div class="form-group">
	                      <label for="code">编码</label>
	                      <input type="text" class="form-control" 
	                      	name="param.code"  value="${youappParam.code }"
	                      	id="code" placeholder="编码">
	                    </div>
	                    <div class="form-group">
	                      <label for="name">名称</label>
	                      <input type="text" class="form-control" 
	                      	name="param.name"  value="${youappParam.name }"
	                      	id="name" placeholder="编码">
	                    </div>
	                    
	                    <div class="form-group">
	                      <label for="desc">备注</label>
	                      <textarea  class="form-control" 
	                      	name="param.description" rows="3"  
	                      	id="desc" placeholder="备注" >${youappParam.description }</textarea>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary"  >Submit</button>
	                  </div>
	                  
	                  <input type="hidden"  name="param.id"  value="${youappParam.id }"/>
	                  <input type="hidden"  name="param.version"  value="${youappParam.version }"/>
                </form>
		
	        </section>

	<script type="text/javascript">
		
		$(function (){
			 $("#editParamForm").validate({
			rules: {
			   'param.functionId': {
				   required: true,
				   maxlength:20
			   },
			   'param.code': {
				   required: true,
				   maxlength:20
			   },
			   'param.name': {
				   required: true,
				   maxlength:128
			   },
			   'param.desc': {
				   required: false,
				   maxlength:512
			   }
			  },
			  submitHandler:function(form){
				  httpPOST('/param.paramaction/editParam', form.id);
		        } 
			    });
		});
		
	</script>

</div>