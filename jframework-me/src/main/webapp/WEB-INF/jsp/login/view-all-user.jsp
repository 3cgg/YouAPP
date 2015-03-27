<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            用户管理
	            <small>用户集</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> 应用</a></li>
	            <li class="active">用户集</li>
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
	                  <form id="userSearchForm">
	                    <div class="input-group">
	                      <input type="text" class="form-control input-sm pull-right" style="width: 120px;" 
	                      name="userSearchCriteria.userName"   value="${ userSearchCriteria.userName}"
	                      placeholder="用户名称"  />
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
	                      <th>用户名称</th>
	                      <th>创建时间</th>
	                      <th>密码</th>
	                    </tr>
	                  <c:forEach items="${users }" var="user">
	                  <tr id="${user.id }"  style="cursor: pointer;" 
	                  class="youapp-popover-mark" 
	                  data-youappDelete="/login.loginaction/deleteUser"
	                  data-youappView="/login.loginaction/toViewUser"
	                  >
	                      <td>${user.userName }</td>
	                      <td>${user.createTime }</td>
	                      <td>${user.password }</td>
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
			 $("#userSearchForm").validate({
				 onfocusout:false,
				 onkeyup:false,
			rules: {
			   'userSearchCriteria.userName': {
				   required: false
			   }
			  },
			  submitHandler:function(form){
				  httpPOST('/login.loginaction/getUsersWithsCondition', form.id);
		        },
		    	errorPlacement:warningMessageAlert
			    });
		});
		
	</script>

</div>