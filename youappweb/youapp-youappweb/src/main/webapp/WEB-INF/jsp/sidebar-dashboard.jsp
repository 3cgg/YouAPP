<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    
<li class="active treeview">
	              <a href="#">
	                <i class="fa fa-home"></i> <span>Home</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	                <li class="active"><a href="javascript:void(0)" 
	                	onclick='GET("/login.loginaction/tracker")'
	                ><i class="fa fa-circle-o"></i> 我的痕迹</a></li>
	                <li><a href="javascript:void(0)" 
	                onclick='GET("/login.moduleaction/modules")'
	                ><i class="fa fa-circle-o"></i> 应用</a></li>
	              </ul>
	            </li>