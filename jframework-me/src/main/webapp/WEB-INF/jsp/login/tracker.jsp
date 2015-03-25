<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
	<section class="content-header">
		<h1>
			我的痕迹 <small>Login</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">我的痕迹</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		
		<!-- row -->
		<div class="row">
			<div class="col-md-12">
				<!-- The time line -->
				<ul class="timeline">
					<!-- timeline time label -->
					<c:forEach items="${timelineGroups}" var="timelineGroup"  varStatus="ind" >
					<li class="time-label"><span class="bg-red"> ${timelineGroup.date} </span></li>
					<!-- /.timeline-label -->
					<c:forEach items="${timelineGroup.timelineViews}" var="timeline" >
					<!-- timeline item -->
					<li><i class="fa fa-user bg-aqua"></i>
						<div class="timeline-item">
							<span class="time"><i class="fa fa-clock-o"></i>  ${ timeline.timeOffset}</span>
							<h3 class="timeline-header no-border">
								<a href="javascript:void(0)" 
								onclick='httpGET("${timeline.highlightPath}")'
								>${timeline.highlightContent}</a> ${timeline.header }
							</h3>
						</div>
						</li>
						</c:forEach>
						</c:forEach>
					<!-- END timeline item -->
					<li><i class="fa fa-clock-o bg-gray"></i></li>
				</ul>
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->



	</section>
	<!-- /.content -->
<script type="text/javascript">

</script>









</div>