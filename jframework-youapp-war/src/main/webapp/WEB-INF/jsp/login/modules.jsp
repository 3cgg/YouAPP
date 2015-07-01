<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<div id="youapp-content">
	<!-- 
	<section class="content-header">
		<h1>
			应用 <small>all</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">app</li>
		</ol>
	</section>
 -->
	<!-- Main content -->
	<section class="content">   
    <!-- Small boxes (Stat box) -->
          <div class="row">
            <div class="col-lg-3 col-xs-3">
              <!-- small box -->
              <div class="small-box bg-aqua">
                <div class="inner">
                  <h3 class="inner-head">账单</h3>
                  <p class="inner-content">账单管理</p>
                </div>
                <div class="icon">
                  <i class="ion ion-bag" ></i>
                </div>
                <a
                	href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toNavigate")'
                  class="small-box-footer"
                
                > More info<i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
            <!-- 用户管理 -->
            <div class="col-lg-3 col-xs-3">
              <!-- small box -->
              <div class="small-box bg-yellow">
                <div class="inner">
                  <h3 class="inner-head">用户</h3>
                  <p class="inner-content">用户管理</p>
                </div>
                <div class="icon">
                  <i class="ion ion-person-add"></i>
                </div>
                <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
            <!--  -->
            <div class="col-lg-3 col-xs-3">
              <!-- small box -->
              <div class="small-box bg-green">
                <div class="inner">
                  <h3 class="inner-head">体重</h3>
                  <p class="inner-content">体重管理</p>
                </div>
                <div class="icon">
                  <i class="ion ion-stats-bars"></i>
                </div>
                <a
                	href="javascript:void(0)" 
	                onclick='GET("/weight.weightaction/toNavigate")'
                	class="small-box-footer"
                >More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
            <div class="col-lg-3 col-xs-3">
              <!-- small box -->
              <div class="small-box bg-red">
                <div class="inner">
                 <h3 class="inner-head">参数</h3>
                  <p class="inner-content">参数管理</p>
                </div>
                <div class="icon">
                  <i class="ion  ion-android-settings"></i>
                </div>
                
                <a
                	href="javascript:void(0)" 
                	onclick='GET("/param.paramaction/toNavigate")'
                	class="small-box-footer"
                	>More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
            
            <div class="col-lg-3 col-xs-3">
              <!-- small box -->
              <div class="small-box bg-red">
                <div class="inner">
                 <h3 class="inner-head">权资</h3>
                  <p class="inner-content">权资管理</p>
                </div>
                <div class="icon">
                  <i class="ion  ion-android-settings"></i>
                </div>
                
                <a
                	href="javascript:void(0)" 
                	onclick='GET("/login.loginaction/toNavigate")'
                	class="small-box-footer"
                	>More info <i class="fa fa-arrow-circle-right"></i></a>
              </div>
            </div><!-- ./col -->
            
          </div><!-- /.row -->
          
          
          </section>
          
          </div>
          
          