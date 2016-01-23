<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            角色编辑
	            <a href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toRoleAllocationOnGroup")'
	                ><i class=" fa ion-ios-undo"></i></a>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">角色编辑</li>
	          </ol>
	        </section>
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
									
				<form role="form"  id="editRoleForm">
					
	                  <div class="box-body">
	                  
	                  <!-- 角色编码 -->
	                    <div class="form-group">
	                      <label for="roleCode">角色编码</label>
	                      <input type="text" class="form-control" 
	                      	name="role.roleCode" value="${role.roleCode }"
	                      	id="roleCode" placeholder="角色编码">
	                    </div>
	                    
	                    <!-- 角色名称  -->
	                    <div class="form-group">
	                      <label for="roleName">角色名称</label>
	                      <input type="text" class="form-control" 
	                      	name="role.roleName"  value="${ role.roleName}"
	                      	id="roleName" placeholder="角色名称">
	                    </div>

	                    <!-- 备注 -->
	                    <div class="form-group">
	                      <label for="desc">备注</label>
	                      <textarea  class="form-control" 
	                      	name="role.description" rows="3"  
	                      	id="desc" placeholder="备注" >${ role.description}</textarea>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary"  >Submit</button>
	                  </div>
	                  
	                  <input type="hidden"  name="role.id" value="${role.id }">
					  <input type="hidden"  name="role.version"  value="${role.version }">
					  
                </form>
		
	        </section>

	<script type="text/javascript">
	
		$(function (){
			 $("#editRoleForm").validate({
			rules: {
			   'role.roleCode': {
				   required: true,
				   maxlength:32
			   },
			   'role.roleName': {
				   required: true,
				   maxlength:64
			   },
			   'role.description': {
				   required: false,
				   maxlength:256
			   }
			  },
			  submitHandler:function(form){
				  submitPOST('/login.loginaction/editRole', form.id);
		        } 
			    });
		});
		
	</script>

</div>