<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            参数信息
	            <small>
	            <a href="javascript:void(0)" 
	                onclick='httpGET("/param.paramaction/toViewAllParam")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                 onclick='httpGET("/param.paramaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li >
	            <a href="javascript:void(0)" 
	                onclick='httpGET("/param.paramaction/toViewAllParam")'
	                >参数集</a>
	            </li>
	            <li class="active">View</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        <div class="col-md-6">
		        <div class="box box-solid">
	                <div class="box-header with-border">
	                <a   href="javascript:void(0)"  class="youapp-edit" 
	                	onclick='httpGET("/param.paramaction/toEditParam","id=${youappParam.id}")'
	                >
						<i class="fa fa-edit"></i>
					</a>
	                  <h3 class="box-title">参数信息</h3>
	                  
	                </div><!-- /.box-header -->
	                <div class="box-body">
	                  <dl>
	                    <dt>编码</dt>
	                    <dd>${youappParam.code }</dd>
	                    <dt>名称</dt>
	                    <dd>${youappParam.name} </dd>
	                    <dt>类别</dt>
	                    <dd>${youappParam.functionId} </dd>
	                    <dt>描述</dt>
	                    <dd>${youappParam.description} </dd>
	                  </dl>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div><!-- ./col -->
					
	        </section>


</div>