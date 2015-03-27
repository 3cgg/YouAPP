<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    <%@ taglib uri="http://github.com/jinwanmei/YouAPP" prefix="youapp"   %>
<li class="treeview">
	              <a href="#">
	                <i class="fa fa-dashboard"></i> <span>User</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	                
	                <!-- 
	                <li class="active"><a href="javascript:void(0)" 
	                	onclick='httpGET("/login.loginaction/toCreateUser")'
	                ><i class="fa fa-circle-o"></i> Create User</a></li> -->
	                <li><a href="javascript:void(0)" 
	                onclick='httpGET("/login.loginaction/profile")'
	                ><i class="fa fa-circle-o"></i> Profile</a></li>
	                <youapp:access  resource="/login.loginaction/toViewAllUser">
	                <li><a href="javascript:void(0)" 
	                onclick='httpGET("/login.loginaction/toViewAllUser")'
	                ><i class="fa fa-circle-o"></i> 用户集</a></li>
	                </youapp:access>
	              </ul>
	            </li>