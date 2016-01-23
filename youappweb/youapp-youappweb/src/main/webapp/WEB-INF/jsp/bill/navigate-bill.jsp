<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        
	         <!-- Main content -->
	        <section class="content">
	        
	        <div class="row">
	        
	        <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
               <a  
					href="javascript:void(0)" 
	                onclick='GET("/login.moduleaction/modules")'
               >
                <span class="info-box-icon bg-red"><i class="fa fa-reply"></i></span>
                </a>
                <div class="info-box-content">
                  <span class="info-box-text">返回</span>
                  <span class="info-box-number"></span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
	        
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
              <a 
					href="javascript:void(0)" 
					onclick='GET("/bill.billaction/toRecordBill")'
              >
                <span class="info-box-icon bg-aqua"><i class="fa ion-plus"></i></span>
                </a>
                <div class="info-box-content">
                  <span class="info-box-text">新增账单</span>
                  <span class="info-box-number"></span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
              <a 
              		href="javascript:void(0)" 
					onclick='GET("/bill.billaction/toViewChart")'
              >
                <span class="info-box-icon bg-green"><i class="fa fa-pie-chart"></i></span>
                </a>
                <div class="info-box-content">
                  <span class="info-box-text">统计账单</span>
                  <span class="info-box-number"></span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
              <a 
              		href="javascript:void(0)" 
					onclick='GET("/bill.billaction/toViewAllBill")'
              >
                <span class="info-box-icon bg-yellow"><i class="fa fa-files-o"></i></span>
               </a>
                <div class="info-box-content">
                  <span class="info-box-text">账单集</span>
                  <span class="info-box-number"></span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            
            
          </div><!-- /.row -->         
					
	        </section>


</div>