<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            体重信息
	            <small>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">体重信息</li>
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