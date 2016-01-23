<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<c:import url="head.jsp"></c:import>
</head>
<body class="skin-blue">

	<c:import url="message-div.jsp"></c:import>

	<div id="youapp-wrapper">
		<div class="wrapper">
	
			<c:import url="main-header.jsp"></c:import>
	
			<c:import url="sidebar.jsp"></c:import>
			
			<div class="content-wrapper">
				<c:import url="${jspFile }"></c:import>
			</div>
			<c:import url="footer.jsp"></c:import>
	
		</div>
	</div>
</body>
</html>

