<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            参数集
	            <small>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/param.paramaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/param.paramaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">参数集</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
					<c:import url="../message-alert.jsp"></c:import>
					<c:import url="../message-success.jsp"></c:import>
					<c:import url="../popover-action-on-table.jsp"></c:import>
					<div class="row">
	            <div class="col-xs-12">
	              <div class="box">
	                <div class="box-header">
	                  <h3 class="box-title">列表</h3>
	                  <div class="box-tools">
	                  <form id="paramSearchForm">
	                    <div class="input-group">
	                      <input type="text" name="paramSearchCriteria.name" class="form-control input-sm pull-right" style="width: 120px;" 
	                      placeholder="参数名称"  />
	                      <div class="input-group-btn">
	                        <button type="submit" class="btn btn-sm btn-default"  style="font-size: 18px;"><i class="fa fa-search"></i></button>
	                      </div>
	                    </div>
	                    </form>
	                  </div>
	                </div><!-- /.box-header -->
	                <div class="box-body table-responsive no-padding">
	                  <table class="table table-hover">
	                  <tr>
	                      <th>类别</th>
	                      <th>编码</th>
	                      <th>名称</th>
	                      <th class="hidden-xs-max-480px">描述</th>
	                    </tr>
	                  <c:forEach items="${params }" var="para">
	                  <tr id="${para.id }"  style="cursor: pointer;" 
	                  class="youapp-popover-mark" 
	                  data-youappDelete="/param.paramaction/deleteParam"
	                  data-youappView="/param.paramaction/toViewParam"
	                  >
	                      <td>${para.functionId }</td>
	                      <td>${para.code }</td>
	                      <td>${para.name }</td>
	                      <td class="hidden-xs-max-480px">${para.description}</td>
	                    </tr>
	                  </c:forEach>
	                  
	                  </table>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div>
          </div>
				
		
	        </section>

	<script type="text/javascript">
		
		$(function (){
			 $("#paramSearchForm").validate({
				 onfocusout:false,
				 onkeyup:false,
			rules: {
			   'paramSearchCriteria.name': {
				   required: true
			   }
			  },
			  submitHandler:function(form){
				  submitPOST('/param.paramaction/getParamsWithsCondition', form.id);
		        },
		    	errorPlacement:warningMessageAlert
			    });
		});
		
	</script>

</div>