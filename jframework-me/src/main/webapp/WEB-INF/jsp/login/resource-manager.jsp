<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            资源授权
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
	                onclick='GET("/resource.resourceaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">历史账单</li>
	          </ol>
	           -->
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        
	        <div class="col-md-4 ">
	        	<div  class="col-md-12  youapp-box">
		        	<div class="form-group">
                      <label>模块</label>
	                <select id="moduleNames"  class="form-control"  size="1" >
                        <c:forEach items="${moduleNames }" var="moduleName">
                        	<option   value=" ${moduleName}"   >${moduleName }</option>
                        </c:forEach>
                      </select>
                      </div>
	                <div class="form-group">
	                      <label>资源路径</label>
	                      <select id="modulePaths"  class="form-control"  size="18"  >
	                        
	                      </select>
                    </div>
                   </div> 
	            </div><!-- ./col -->
	            
	            <div class="col-md-2  "  >
	            
	            	<div  class="col-md-12  youapp-box">
	            		<div class="form-group">
	                      	<label>是否使用页面缓存</label>
		            		<input id="cached"  name="cached"  type="checkbox"  data-off-color="warning"  />
		            		<input type="checkbox"  name="cachedIndicater"  id="cachedIndicater"  /> 
	            		</div>
	            	</div>
	            	
	            
	            </div>
	            
	            <div class="col-md-6">
	            
	            <div class="col-md-12  youapp-box "  >
	            <div class="col-md-12   "  >
	            	<span name="pathName"></span>
	            	<input  type="hidden"  id="path"  />
	            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>资源所属角色</label>
                      <select id="resourceRoles"  class="form-control"  size="8"  onclick="onOff('roleDeleteIcon','roleAddIcon')">
                        
                      </select>
                    </div>
		            </div>
		            <div class="col-md-2" style=" text-align: center;  margin-top: 30px">
		            
			            <ul class="nav nav-list">
			            <li  >
			            <a id="roleDeleteIcon"   
			            	href="javascript:void(0)" 
	               			 onclick='removeResourceRole(this)'
			            class="ion-close-circled" style="font-size: 32px;" >
			            </a>	
			            </li>
			            <li  >
			            <a  id="roleAddIcon" 
			            	href="javascript:void(0)" 
	               			 onclick='addResourceRole(this)'
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
                        	<option   value=" ${role.id }"   >${role.roleName }</option>
                        </c:forEach>
                      </select>
                    </div>
		            </div>
	            </div>
	            
	            
	            <div class="col-md-12  youapp-box "  >
	            <div class="col-md-12   "  >
	            	<span name="pathName"></span>
	            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>资源所属组</label>
                      <select id="resourceGroups"  class="form-control"  size="8"  onclick="onOff('groupDeleteIcon','groupAddIcon')">
                      </select>
                    </div>
		            </div>
		            <div class="col-md-2" style=" text-align: center;  margin-top: 30px">
		            
			            <ul class="nav nav-list">
			            <li  >
			            <a id="groupDeleteIcon"   
			            	href="javascript:void(0)" 
	               			 onclick='removeResourceGroup(this)'
			            class="ion-close-circled" style="font-size: 32px;" >
			            </a>	
			            </li>
			            <li  >
			            <a  id="groupAddIcon" 
			            	href="javascript:void(0)" 
	               			 onclick='addResourceGroup(this)'
			            	class=" ion-arrow-left-a" style="font-size: 32px;" >
			            </a>	
			            </li>
			            </ul>
		            </div>
		            <div class="col-md-5">
		            <div class="form-group">
                      <label>所有组</label>
                      <select id="allGroups"  class="form-control"  size="8"  onclick="onOff('groupAddIcon','groupDeleteIcon')"  >
                        <c:forEach items="${groups }" var="group">
                        	<option  value=" ${group.id }"   >${group.groupName }</option>
                        </c:forEach>
                      </select>
                    </div>
		            </div>
	            </div>
		        
	            </div><!-- ./col -->

	        </section>

<script type="text/javascript">

$(
		function bingEvent(){
			
			$('#moduleNames').on("change",function (obj){
				reset();	
				GET("/resource.resourceaction/getAllPathsOnModule", "moduleName="+$(this).val(), 
						function(json){
						loadModulePaths(json);
						});
			});
			
			$('#modulePaths').on("change",function (obj){
				$('span[name="pathName"]').text($(this).val());
					$('#path').val($(this).val());
				
				GET("/resource.resourceaction/getResourceRole", "path="+$(this).val(), 
						function(json){
							loadResourceRoles(json);
						});
				
				GET("/resource.resourceaction/getResourceGroup", "path="+$(this).val(), 
						function(json){
							loadResourceGroups(json);
						});
				
				GET("/resource.resourceaction/getExtensionOnResource", "path="+$(this).val(), 
						function(json){
							console.log(this); 
							if(json!=null){
								$('#cachedIndicater').iCheck("check").iCheck("disable");
								$("#cached").bootstrapSwitch("disabled",false);
					  			setCached(json.cached);
							}
							else{
								$('#cachedIndicater').iCheck("uncheck").iCheck("enable");
								$("#cached").bootstrapSwitch("disabled",true);
							}
						});
			});
			
			
		}
		
		);


	function onOff(onId, offId){
		$('#'+onId).css("cursor","pointer").attr("disabled",false);
		$('#'+offId).css("cursor","not-allowed").attr("disabled",true);
	}
	
	function reset(){
		$('#cachedIndicater').iCheck("uncheck").iCheck("disable");
		$("#cached").bootstrapSwitch("disabled",true);
		$('#resourceRoles').empty();
		$('#resourceGroups').empty();
		$('#path').empty();
		$('span[name="pathName"]').empty();
	}


</script>

<!--  Role below -->
<script type="text/javascript">


function loadModulePaths(resourcePaths){
	var $modulePaths=$('#modulePaths');
	var options="";
	for(var i=0;i<resourcePaths.length;i++){
		var path=resourcePaths[i];
		options=options+"<option value='"+path+"' >"+path+"</option>"
	}
	$modulePaths.html(options);
	
}

function loadResourceRoles(resourceRoles){
	
	var $resourceRoles=$('#resourceRoles');
	var options="";
	for(var i=0;i<resourceRoles.length;i++){
		var resourceRole=resourceRoles[i];
		//user role primary key +"|"+role id
		options=options+"<option value='"+resourceRole.id+"|"+resourceRole.roleId+"' >"+resourceRole.role.roleName+"</option>"
	}
	$resourceRoles.html(options);
	
}

function addResourceRole(obj){
	
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $allRoles=$('#allRoles');
	var $path=$('#path');
	if($allRoles.val()!=null&&$path.val()!=null&&$path.val()!=""){
	GET("/resource.resourceaction/bingResourceOnRole", "roleId="+$allRoles.val()+"&path="+$path.val(), 
				function(json){
					loadResourceRoles(json);
				});
	}
}

function removeResourceRole(obj){
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $resourceRoles=$('#resourceRoles');
	
	if($resourceRoles.val()!=null){
		var $path=$('#path');
		GET("/resource.resourceaction/unbingResourceOnRole", "roleId="+$resourceRoles.val().split("|")[1]+"&path="+$path.val(), 
					function(json){
						loadResourceRoles(json);
					});
	}
	
}



</script>


<!--  Goup below  -->

<script type="text/javascript">

function loadResourceGroups(resourceGroups){
	
	var $resourceGroups=$('#resourceGroups');
	var options="";
	for(var i=0;i<resourceGroups.length;i++){
		var resourceGroup=resourceGroups[i];
		//user user-group primary key +"|"+group id
		options=options+"<option value='"+resourceGroup.id+"|"+resourceGroup.groupId+"' >"+resourceGroup.group.groupName+"</option>"
	}
	$resourceGroups.html(options);
}

function addResourceGroup(obj){
	
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $allGroups=$('#allGroups');
	var $path=$('#path');
	if($allGroups.val()!=null&&$path.val()!=null&&$path.val()!=""){
	GET("/resource.resourceaction/bingResourceOnGroup", "groupId="+$allGroups.val()+"&path="+$path.val(), 
				function(json){
					loadResourceGroups(json);
				});
	}
}

function removeResourceGroup(obj){
	if($(obj).attr('disabled')!==undefined &&$(obj).attr('disabled')=="disabled"){
		return ;
	}
	var $resourceGroups=$('#resourceGroups');
	
	if($resourceGroups.val()!=null){
		var $path=$('#path');
		GET("/resource.resourceaction/unbingResourceOnGroup", "groupId="+$resourceGroups.val().split("|")[1]+"&path="+$path.val(), 
					function(json){
						loadResourceGroups(json);
					});
	}
	
}

</script>

<script type="text/javascript">

function setResourceExtend(resourceExtend){
	
	
}

// Y =TRUE , N =FALSE
function setCached(cached){
	$("#cached").bootstrapSwitch("state","Y"==cached,true);
}

</script>

<script type="text/javascript">

$(function init(){
	
	//iCheck for checkbox and radio inputs
  //Flat red color scheme for iCheck
         $('#cachedIndicater').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        }).iCheck('disable').on("ifClicked",function (event){
        	// the event executed only one time, to init the extension of the resource. 
        	$cachedIndicater=$(this);
        	var $path=$('#path');
			  if($path.val()!=null&&$path.val()!=""){
				  GET("/resource.resourceaction/enhanceResource", "cached=Y&path="+$path.val(), 
							function(json){
					  			$cachedIndicater.iCheck('disable');
					  			$("#cached").bootstrapSwitch("toggleDisabled");
					  			setCached(json.cached);
							});
			  }
        	
        });
	
	
	$("#cached").bootstrapSwitch(
		{size:"small",disabled:true}		
	);
	
	$('#cached').on('switchChange.bootstrapSwitch', function(event, state) {
		  console.log(this); // DOM element
		  console.log(event); // jQuery event
		  console.log(state); // true | false
		  
		  var $path=$('#path');
		  if($path.val()!=null&&$path.val()!=""){
			  GET("/resource.resourceaction/enhanceResource", "cached="+(state?"Y":"N")+"&path="+$path.val(), 
						function(json){
							
						});
		  }
		  
		});
	
}
		);


</script>

</div>
