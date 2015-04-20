<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            Weight
	            <small>View</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
	            <li class="active">Weight</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        <div class="col-md-6">
		        <div class="box box-solid">
	                <div class="box-header with-border">
	                  <h3 class="box-title">Weight</h3>
	                </div><!-- /.box-header -->
	                <div class="box-body">
	                  <dl>
	                    <dt>体重</dt>
	                    <dd>${weight.weight }</dd>
	                    <dt>描述</dt>
	                    <dd>在 ${weight.recordTime} 时录入的，</dd>
	                    <dd> </dd>
	                    <dd>${weight.description}</dd>
	                  </dl>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div><!-- ./col -->
					
	        </section>


</div>