<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            角色分配
	            <small>
	             <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active"> 角色分配</li>
	          </ol>
	           
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        
	        	<div class="col-md-4 ">
	        		<div  class="col-md-12  youapp-box">
		                <div class="form-group">
	                      <label>角色集</label>
	                      
	                      <div>
	                      <ul class="nav nav-tabs">
	                      	<li id="roleAdd">
				            	<a 
				            	href="javascript:void(0)" 
				                onclick='GET("/login.loginaction/toAddNewRole")'
				                ><i class=" fa ion-android-add"></i> 新增</a>
				            </li>
				            <li id="roleEdit">
				            	<a 
				            	href="javascript:void(0)"   
				                onclick='toEditRole()'
				                ><i class=" fa ion-edit"></i> 更新</a>
				            </li>
				            <li id="roleDelete">
				            	<a 
				            	href="javascript:void(0)" 
				                onclick='deleteRole()'
				                ><i class=" fa ion-android-delete"></i> 删除</a>
				            </li>
				            
	                      </ul>
	                      
	                      </div>
	                      
	                      <select id="allRoles"  class="form-control"  size="21"  >
	                        <c:forEach items="${roles }" var="role">
	                        	<option  value=" ${role.id }"   >${role.roleName }</option>
	                        </c:forEach>
	                      </select>
	                    </div>
                   </div> 
	            </div><!-- ./col -->
	            
	            <div class="col-md-8">
	            
	            <div class="col-md-12  youapp-box "  >
		            <div class="col-md-12   "  >
		            	<span name="roleName"></span>
		            	<input  type="hidden"  id="roleId"  />
		            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>角色所属组</label>
                      <select id="roleGroups"  class="form-control"  size="20"  onclick="onOff('groupDeleteIcon','groupAddIcon')">
                        
                      </select>
                    </div>
		            </div>
		            <div class="col-md-2" style=" text-align: center;  margin-top: 30px">
		            
			            <ul class="nav nav-list">
			            <li  >
			            <a id="groupDeleteIcon"   
			            	href="javascript:void(0)" 
	               			 onclick='removeGroupOnRole(this)'
			            class="ion-close-circled" style="font-size: 32px;" >
			            </a>	
			            </li>
			            <li  >
			            <a  id="groupAddIcon" 
			            	href="javascript:void(0)" 
	               			 onclick='addGroupOnRole(this)'
			            	class=" ion-arrow-left-a" style="font-size: 32px;" >
			            </a>	
			            </li>
			            </ul>
		            </div>
		            <div class="col-md-5">
		            	<div class="form-group">
	                      <label>所有角色组</label>
	                      <select id="allGroups"  class="form-control"  size="20"  onclick="onOff('groupAddIcon','groupDeleteIcon')"  >
	                        <c:forEach items="${groups }" var="group">
	                        	<option   value=" ${group.id }"   >${group.groupName }</option>
	                        </c:forEach>
	                      </select>
	                    </div>
		            </div>
	            </div>
		        
	            </div><!-- ./col -->
	            
	        </section>


<script type="text/javascript">

function reset(){
	$("span[name='roleName']").text("");
	$("#roleId").val("");
	$('#groupDeleteIcon').css("cursor","not-allowed").attr("disabled",true);
	$('#groupAddIcon').css("cursor","not-allowed").attr("disabled",true);
	$('#roleGroups').empty();
}

$( function (){
	
	$("#allRoles").on("change",function (obj){
		GET("/login.loginaction/getRoleGroupByRole", "roleId="+$(this).val(), 
				function(json){
					loadGroupsOnRole(json);
				});
		
		Console(this);
		var $this=$(this);
		$("span[name='roleName']").text($this.find("option:selected").text());
		$("#roleId").val($this.val());
	});
});



	function onOff(onId, offId){
		$('#'+onId).css("cursor","pointer").attr("disabled",false);
		$('#'+offId).css("cursor","not-allowed").attr("disabled",true);
	}

</script>


<!--  Goup below  -->

<script type="text/javascript">

function loadGroupsOnRole(roleGroups){
	
	var $roleGroups=$('#roleGroups');
	var options="";
	for(var i=0;i<roleGroups.length;i++){
		var roleGroup=roleGroups[i];
		//user role primary key +"|"+role id
		options=options+"<option value='"+roleGroup.id+"|"+roleGroup.groupId+"' >"+roleGroup.group.groupName+"</option>"
	}
	$roleGroups.html(options);
	
}

function addGroupOnRole(obj){
	
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $allGroups=$('#allGroups');
	var $roleId=$('#roleId');
	if($allGroups.val()!=null&&$roleId.val()!=null&&$roleId.val()!=""){
	GET("/login.loginaction/bingRoleOnGroup", "groupId="+$allGroups.val()+"&roleId="+$roleId.val(), 
				function(json){
					loadGroupsOnRole(json);
				});
	}
}

function removeGroupOnRole(obj){
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $roleGroups=$('#roleGroups');
	
	if($roleGroups.val()!=null){
		var $roleId=$('#roleId');
		GET("/login.loginaction/unbingRoleOnGroup", "groupId="+$roleGroups.val().split("|")[1]+"&roleId="+$roleId.val(), 
					function(json){
						loadGroupsOnRole(json);
					});
	}
	
}

function toEditRole(){
	var roleSelectedId=$("#allRoles").find("option:selected").val();
	if(roleSelectedId!=null&&roleSelectedId!=""){
		GET("/login.loginaction/toEditRole", "roleId="+roleSelectedId);
	}
}


function deleteRole(){
	
	var roleSelectedId=$("#allRoles").find("option:selected").val();
	if(roleSelectedId!=null&&roleSelectedId!=""){
		deleteRecordWithConfirmOnHTTPGET("/login.loginaction/deleteRole", "roleId="+roleSelectedId,
		function (roles){
			reset();
			var $allRoles=$("#allRoles");
			var options="";
			for(var i=0;i<roles.length;i++){
				var role=roles[i];
				//user role primary key +"|"+role id
				options=options+"<option value='"+role.id+"' >"+role.roleName+"</option>"
			}
			$allRoles.empty();
			$allRoles.html(options);
		}		
		);
	}
	
}



</script>


</div>
