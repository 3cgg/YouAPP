<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://github.com/jinwanmei/YouAPP" prefix="youapp"   %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"   %>
    
       
    <youapp:servletRenderContext className="j.jave.framework.components.tablemanager.ext.TableManagerServletRenderContext">
    </youapp:servletRenderContext>
<li class="treeview">
	              <a href="#">
	                <i class="fa fa-database"></i> <span>Table Manager</span> <i class="fa fa-angle-left pull-right"></i>
	              </a>
	              <ul class="treeview-menu">
	              
	              <c:forEach items="${items }" var="item"  varStatus="ind">
	              
	               <li><a href="javascript:void(0)" 
	                onclick='httpGET("${item.url }","${item.param}")'
	                ><i class="fa fa-circle-o"></i>${item.label }</a></li>
	                
	              </c:forEach>
	              
	              </ul>
</li>
