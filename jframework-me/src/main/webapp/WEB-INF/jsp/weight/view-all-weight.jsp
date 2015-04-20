<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            体重
	            <small>历史记录</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> 应用</a></li>
	            <li class="active">体重历史记录</li>
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
	                  <form id="weightSearchForm">
	                    <div class="input-group">
	                      <input type="text" name="lastetMonth" class="form-control input-sm pull-right" style="width: 120px;" 
	                      placeholder="最近几个月的记录"  />
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
	                      <th>体重（KG）</th>
	                      <th>记录时间</th>
	                      <th>描述</th>
	                    </tr>
	                  <c:forEach items="${weights }" var="weight">
	                  <tr id="${weight.id }"  style="cursor: pointer;" 
	                  class="youapp-popover-mark" 
	                  data-youappDelete="/weight.weightaction/deleteWeight"
	                  data-youappView="/weight.weightaction/toViewWeight"
	                  >
	                      <td>${weight.weight }</td>
	                      <td>${weight.recordTime }</td>
	                      <td>${weight.description}</td>
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
			 $("#weightSearchForm").validate({
				 onfocusout:false,
				 onkeyup:false,
			rules: {
			   'lastetMonth': {
				   required: true,
				   number:true
			   }
			  },
			  submitHandler:function(form){
				  httpPOST('/weight.weightaction/getWeightsWithsCondition', form.id);
		        },
		    	errorPlacement:warningMessageAlert
			    });
		});
		
	</script>

</div>