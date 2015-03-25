<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-siderbar" >
	<aside class="main-sidebar">
		<section class="sidebar">
				<!-- Sidebar user panel -->
	          <div class="user-panel">
	            <div class="pull-left image">
	              <img src="${pageContext.request.contextPath}/images/glyphicons_001_leaf.png" class="img-circle" alt="User Image" />
	            </div>
	            <div class="pull-left info">
	              <p>YouAPP</p>
	
	              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
	            </div>
	          </div>
	          <!-- search form -->
	          <form action="#" method="get" class="sidebar-form">
	            <div class="input-group">
	              <input type="text" name="q" class="form-control" placeholder="Search..."/>
	              <span class="input-group-btn">
	                <button type='submit' name='search' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
	              </span>
	            </div>
	          </form>
	          <!-- /.search form -->
	
			
			<ul class="sidebar-menu">
				<li class="header">MAIN NAVIGATION</li>
				
				<!--  dashboard  -->
				<c:import url="sidebar-dashboard.jsp"></c:import>
	            <!--  user  -->
	            <c:import url="login/sidebar-user.jsp"></c:import>
	            <!-- weight -->
	            <c:import url="weight/sidebar-weight.jsp"></c:import>
	            <!-- param -->
	            <c:import url="param/sidebar-param.jsp"></c:import>
	            <!-- bill -->
	            <c:import url="bill/sidebar-bill.jsp"></c:import>
	            
			</ul>
	
	
	
		</section>
	</aside>
</div>