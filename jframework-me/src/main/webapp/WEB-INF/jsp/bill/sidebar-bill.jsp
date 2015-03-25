<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<li class="treeview">
	              <a href="#">
	                <i class="fa fa-dashboard"></i> <span>账单管理</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	                <li class="active"><a href="javascript:void(0)" 
	                	onclick='httpGET("/bill.billaction/toRecordBill")'
	                ><i class="fa fa-circle-o"></i> 录入</a></li>
	                
	                <li><a href="javascript:void(0)" 
	                onclick='httpGET("/bill.billaction/toViewAllBill")'
	                ><i class="fa fa-circle-o"></i> 记录集</a></li>
	              </ul>
	            </li>