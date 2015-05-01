<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<li class="treeview">
	              <a href="#">
	                <i class="fa fa-shopping-cart"></i> <span>账单管理</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	                <li class="active"><a href="javascript:void(0)" 
	                	onclick='GET("/bill.billaction/toRecordBill")'
	                ><i class="fa fa-circle-o"></i> 录入</a></li>
	                <li><a href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toViewChart")'
	                ><i class="fa fa-circle-o"></i> 账单构成</a></li>
	                <li><a href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toViewAllBill")'
	                ><i class="fa fa-circle-o"></i> 记录集</a></li>
	              </ul>
	            </li>