<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            用户
	            <small>
	            <!-- <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/login.loginaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	                 -->
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
	        <div class="col-md-6">
		        <div class="box box-solid">
	                <div class="box-header with-border">
	                  <h3 class="box-title">基础信息</h3>
	                </div><!-- /.box-header -->
	                <div class="box-body">
	                  <dl>
	                    <dt>用户名</dt>
	                    <dd>${user.userName }</dd>
	                    <dt>描述</dt>
	                    <dd>注册时间- ${user.createTime} </dd>
	                    <dd>用户权限- a general user.</dd>
	                  </dl>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div><!-- ./col -->
					
	        </section>


</div>