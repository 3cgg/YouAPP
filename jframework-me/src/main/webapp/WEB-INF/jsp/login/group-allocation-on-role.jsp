<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            角色组管理
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
	            <li class="active">角色组管理</li>
	          </ol>
	           
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        
	        	<div class="col-md-4 ">
	        		<div  class="col-md-12  youapp-box">
		                <div class="form-group">
	                      <label>角色组集</label>
	                      <div>
	                      <ul class="nav nav-tabs">
	                      	<li id="groupAdd">
				            	<a 
				            	href="javascript:void(0)" 
				                onclick='GET("/login.loginaction/toAddNewGroup")'
				                ><i class=" fa ion-android-add"></i> 新增</a>
				            </li>
				            <li id="groupEdit">
				            	<a 
				            	href="javascript:void(0)"   
				                onclick='toEditGroup()'
				                ><i class=" fa ion-edit"></i> 更新</a>
				            </li>
				            <li id="groupDelete">
				            	<a 
				            	href="javascript:void(0)" 
				                onclick='deleteGroup()'
				                ><i class=" fa ion-android-delete"></i> 删除</a>
				            </li>
				            
	                      </ul>
	                      
	                      </div>
	                      
	                      <select id="allGroups"  class="form-control"  size="20"  onclick="onOff('roleAddIcon','roleDeleteIcon')"  >
	                        <c:forEach items="${groups }" var="group">
	                        	<option   value=" ${group.id }"   >${group.groupName }</option>
	                        </c:forEach>
	                      </select>
	                    </div>
                   </div> 
	            </div><!-- ./col -->
	            
	            <div class="col-md-8">
	            
	            <div class="col-md-12  youapp-box "  >
		            <div class="col-md-12   "  >
		            	<span name="groupName"></span>
		            	<input  type="hidden"  id="groupId"  />
		            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>包含的角色</label>
                      <select id="roleGroups"  class="form-control"  size="20"  onclick="onOff('roleDeleteIcon','roleAddIcon')">
                        
                      </select>
                    </div>
		            </div>
		            <div class="col-md-2" style=" text-align: center;  margin-top: 30px">
		            
			            <ul class="nav nav-list">
			            <li  >
			            <a id="roleDeleteIcon"   
			            	href="javascript:void(0)" 
	               			 onclick='removeRoleOnGroup(this)'
			            class="ion-close-circled" style="font-size: 32px;" >
			            </a>	
			            </li>
			            <li  >
			            <a  id="roleAddIcon" 
			            	href="javascript:void(0)" 
	               			 onclick='addRoleOnGroup(this)'
			            	class=" ion-arrow-left-a" style="font-size: 32px;" >
			            </a>	
			            </li>
			            </ul>
		            </div>
		            <div class="col-md-5">
		            	<div class="form-group">
	                      <label>所有角色</label>
	                      <select id="allRoles"  class="form-control"  size="21"   onclick="onOff('roleAddIcon','roleDeleteIcon')">
	                        <c:forEach items="${roles }" var="role">
	                        	<option  value=" ${role.id }"   >${role.roleName }</option>
	                        </c:forEach>
	                      </select>
	                    </div>
		            </div>
	            </div>
		        
	            </div><!-- ./col -->
	            
	        </section>


<script type="text/javascript">

function reset(){
	$("span[name='groupName']").text("");
	$("#groupId").val("");
	$('#roleAddIcon').css("cursor","not-allowed").attr("disabled",true);
	$('#roleDeleteIcon').css("cursor","not-allowed").attr("disabled",true);
	$('#roleGroups').empty();
}

$( function (){
	
	$("#allGroups").on("change",function (obj){
		GET("/login.loginaction/getGroupRoleByGroup", "groupId="+$(this).val(), 
				function(json){
					loadRolesOnGroup(json);
				});
		
		Console(this);
		var $this=$(this);
		$("span[name='groupName']").text($this.find("option:selected").text());
		$("#groupId").val($this.val());
	});
});



	function onOff(onId, offId){
		$('#'+onId).css("cursor","pointer").attr("disabled",false);
		$('#'+offId).css("cursor","not-allowed").attr("disabled",true);
	}

</script>


<!--  Goup below  -->

<script type="text/javascript">

function loadRolesOnGroup(roleGroups){
	
	var $roleGroups=$('#roleGroups');
	var options="";
	for(var i=0;i<roleGroups.length;i++){
		var roleGroup=roleGroups[i];
		//user role primary key +"|"+role id
		options=options+"<option value='"+roleGroup.id+"|"+roleGroup.roleId+"' >"+roleGroup.role.roleName+"</option>"
	}
	$roleGroups.html(options);
	
}

function addRoleOnGroup(obj){
	
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $allRoles=$('#allRoles');
	var $groupId=$('#groupId');
	if($allRoles.val()!=null&&$groupId.val()!=null&&$groupId.val()!=""){
	GET("/login.loginaction/bingGroupOnRole", "roleId="+$allRoles.val()+"&groupId="+$groupId.val(), 
				function(json){
					loadRolesOnGroup(json);
				});
	}
}

function removeRoleOnGroup(obj){
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $roleGroups=$('#roleGroups');
	
	if($roleGroups.val()!=null){
		var $groupId=$('#groupId');
		GET("/login.loginaction/unbingGroupOnRole", "roleId="+$roleGroups.val().split("|")[1]+"&groupId="+$groupId.val(), 
					function(json){
						loadRolesOnGroup(json);
					});
	}
	
}


function toEditGroup(){
	var groupSelectedId=$("#allGroups").find("option:selected").val();
	if(groupSelectedId!=null&&groupSelectedId!=""){
		GET("/login.loginaction/toEditGroup", "groupId="+groupSelectedId);
	}
}


function deleteGroup(){
	
	var groupSelectedId=$("#allGroups").find("option:selected").val();
	if(groupSelectedId!=null&&groupSelectedId!=""){
		deleteRecordWithConfirmOnHTTPGET("/login.loginaction/deleteGroup", "groupId="+groupSelectedId,
		function (groups){
			reset();
			var $allGroups=$("#allGroups");
			var options="";
			for(var i=0;i<groups.length;i++){
				var group=groups[i];
				//user role primary key +"|"+role id
				options=options+"<option value='"+group.id+"' >"+group.groupName+"</option>"
			}
			$allGroups.empty();
			$allGroups.html(options);
		}		
		);
	}
	
}


</script>


</div>
