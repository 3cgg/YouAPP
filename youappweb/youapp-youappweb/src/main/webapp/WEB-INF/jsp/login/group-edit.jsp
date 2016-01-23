<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            角色组编辑
	            <a href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toGroupAllocationOnRole")'
	                ><i class=" fa ion-ios-undo"></i></a>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">角色组编辑</li>
	          </ol>
	        </section>
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
									
				<form role="form"  id="editGroupForm">

	                  <div class="box-body">
	                  
	                  <!-- 角色组编码 -->
	                    <div class="form-group">
	                      <label for="groupCode">角色组编码</label>
	                      <input type="text" class="form-control" 
	                      	name="group.groupCode" value="${ group.groupCode}"
	                      	id="groupCode" placeholder="角色组编码">
	                    </div>
	                    
	                    <!-- 角色组名称  -->
	                    <div class="form-group">
	                      <label for="groupName">角色组名称</label>
	                      <input type="text" class="form-control" 
	                      	name="group.groupName" value="${group.groupName }"
	                      	id="groupName" placeholder="角色组名称">
	                    </div>

	                    <!-- 备注 -->
	                    <div class="form-group">
	                      <label for="desc">备注</label>
	                      <textarea  class="form-control" 
	                      	name="group.description" rows="3"
	                      	id="desc" placeholder="备注" >${group.description }</textarea>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary"  >Submit</button>
	                  </div>
	                  
	                  <input type="hidden"  name="group.id" value="${group.id }">
					  <input type="hidden"  name="group.version"  value="${group.version}">
					  
                </form>
		
	        </section>

	<script type="text/javascript">
	
		$(function (){
			 $("#editGroupForm").validate({
			rules: {
			   'group.groupCode': {
				   required: true,
				   maxlength:32
			   },
			   'group.groupName': {
				   required: true,
				   maxlength:64
			   },
			   'group.description': {
				   required: false,
				   maxlength:256
			   }
			  },
			  submitHandler:function(form){
				  submitPOST('/login.loginaction/editGroup', form.id);
		        } 
			    });
		});
		
	</script>

</div>