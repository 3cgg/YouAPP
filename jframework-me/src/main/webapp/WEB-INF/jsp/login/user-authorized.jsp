<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            用户授权
	            <small>
	             <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <!-- 
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">历史账单</li>
	          </ol>
	           -->
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        
	        <div class="col-md-4">
		        <div class="box box-solid">
	                <div class="box-header with-border">
	                <!-- 
	                  <form id="weightSearchForm">
	                    <div class="input-group">
	                      <input type="text" name="lastetMonth" class="form-control input-sm pull-right" style="width: 120px;" 
	                      placeholder="用户名"  />
	                      <div class="input-group-btn">
	                        <button type="submit" class="btn btn-sm btn-default"  style="font-size: 18px;"><i class="fa fa-search"></i></button>
	                      </div>
	                    </div>
	                    </form>
	                     -->
	                </div><!-- /.box-header -->
	                <div class="box-body">
	                	<table id="userinfo" class="table table-bordered table-hover">
	                	</table>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div><!-- ./col -->
	            
	            
	            <div class="col-md-8">
	            
	            <div class="col-md-12  youapp-box "  >
	            <div class="col-md-12   "  >
	            	<span id="userName"></span>
	            	<input  type="hidden"  id="userId"  />
	            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>用户所属角色</label>
                      <select id="userRoles"  class="form-control"  size="8"  onclick="onOff('roleDeleteIcon','roleAddIcon')">
                        
                      </select>
                    </div>
		            </div>
		            <div class="col-md-2" style=" text-align: center;  margin-top: 30px">
		            
			            <ul class="nav nav-list">
			            <li  >
			            <a id="roleDeleteIcon"   
			            	href="javascript:void(0)" 
	               			 onclick='removeUserRole(this)'
			            class="ion-close-circled" style="font-size: 32px;" >
			            </a>	
			            </li>
			            <li  >
			            <a  id="roleAddIcon" 
			            	href="javascript:void(0)" 
	               			 onclick='addUserRole(this)'
			            	class=" ion-arrow-left-a" style="font-size: 32px;" >
			            </a>	
			            </li>
			            </ul>
		            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>角色集</label>
                      <select id="allRoles"  class="form-control"  size="8"  onclick="onOff('roleAddIcon','roleDeleteIcon')"  >
                        <c:forEach items="${roles }" var="role">
                        	<option label="${role.roleName }"   value=" ${role.id }"   >
                        </c:forEach>
                      </select>
                    </div>
		            </div>
	            </div>
	            
	            <div class="col-md-12">
	            
	            </div>
	            
	            
	            <!--  
	            <div class="col-md-12">
	            <div class="box box-solid">
		            <div class="box-header with-border">
							用户（用户名）组	            
		            </div>
		            <div class="box-body ">
		            	
		            	
		            
		            </div>
	            </div>
	            </div>
	             -->
		        
	            </div><!-- ./col -->
	            
	            
	            
	            
	            
	            
	        </section>

<script type="text/javascript">
$(
	function (){
		
		$('#tableinfo').youappDataTable({
				id:"userinfo",
				columns:[
						{ "mData": "userName","sTitle":"用户名称","sortColumn":"USERNAME"}
						,{ "mData": "createTime","sTitle":"注册时间","sortColumn":"CREATETIME"}
				   ],
				serverParams:function(){
					return [
							{ "name":"userSearchCriteria.userName","value":""}
			                  ];
				},
			  	url:"/login.loginaction/getAllUsers",
			  	isPopover:false,
			  	pageSize:6,
			  	youappfnCreatedRow:function( nRow, aData, iDataIndex ){
			  		$(nRow).on("click",function (){
			  			GET("/login.loginaction/getUserRole", "userId="+aData.id, 
			  					function(json){
			  						loadUserRoles(json);
			  						$('#userName').text(aData.userName);
			  						$('#userId').val(aData.id);
			  						$('#roleDeleteIcon, #roleAddIcon').css("cursor","not-allowed").attr("disabled",true);
			  						//$('#roleAddIcon').css("cursor","not-allowed").attr("disabled",true);
			  					});
			  			
			  		}).addClass("youapp-hand");
	            },
		});
		
	}		
		
);


function onOff(onId, offId){
	$('#'+onId).css("cursor","pointer").attr("disabled",false);
	$('#'+offId).css("cursor","not-allowed").attr("disabled",true);
}


function loadUserRoles(userRoles){
	
	var $userRoles=$('#userRoles');
	var options="";
	for(var i=0;i<userRoles.length;i++){
		var userRole=userRoles[i];
		//user role primary key +"|"+role id
		options=options+"<option value='"+userRole.id+"|"+userRole.roleId+"' label='"+userRole.role.roleName+"' />"
	}
	$userRoles.html(options);
	
}

function addUserRole(obj){
	
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $allRoles=$('#allRoles');
	var $userId=$('#userId');
	if($allRoles.val()!=null&&$userId.val()!=null&&$userId.val()!=""){
	GET("/login.loginaction/bingUserOnRole", "roleId="+$allRoles.val()+"&userId="+$userId.val(), 
				function(json){
					loadUserRoles(json);
				});
	}
}

function removeUserRole(obj){
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $userRoles=$('#userRoles');
	
	if($userRoles.val()!=null){
		var $userId=$('#userId');
		GET("/login.loginaction/unbingUserOnRole", "roleId="+$userRoles.val().split("|")[1]+"&userId="+$userId.val(), 
					function(json){
						loadUserRoles(json);
					});
	}
	
}



</script>

</div>
