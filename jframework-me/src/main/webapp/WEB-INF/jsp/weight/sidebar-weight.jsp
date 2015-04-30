<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<li class="treeview">
	              <a href="#">
	                <i class="fa fa-heart"></i> <span>体重管理</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	                <li class="active"><a href="javascript:void(0)" 
	                	onclick='httpGET("/weight.weightaction/toRecordWeight")'
	                ><i class="fa fa-circle-o"></i> 录入</a></li>
	                <li><a href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toViewChart")'
	                ><i class="fa fa-circle-o"></i> 趋势</a></li>
	                <li><a href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toViewAllWeight")'
	                ><i class="fa fa-circle-o"></i> 历史记录</a></li>
	              </ul>
	            </li>