<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            账单构成
	            <small>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">账单构成</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
					
					<div class="box box-info">
                <div class="box-header">
                  <h3 class="box-title">账单构成</h3>
                </div>
                <div class="box-body chart-responsive">
                  <div class="chart" id="bar-chart" style="height: 300px;"></div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
		
	        </section>
	<script type="text/javascript">

		$(function (){
			 //BAR CHART
	        var bar = new Morris.Bar({
	          element: 'bar-chart',
	          resize: true,
	          data: [
				<c:forEach items="${barChart.datas }" var="data"  varStatus="ind">
				{x: '${data.xvalue}', ${data.barYData} } 
				<c:if test="${! ind.last }">,</c:if>
				</c:forEach>
	          ],
	          barColors: ['#00a65a', '#f56954'],
	          xkey: 'x',
	          ykeys: [${barChart.barYKeys}],
	          labels: [${barChart.barLables}],
	          hideHover: 'auto'
	        });
		});
		
		

/*
		$(function (){
			 //BAR CHART
	        var bar = new Morris.Bar({
	          element: 'bar-chart',
	          resize: true,
	          data: [
				
				{x: '2088-08-08', GOOD01:87.0 } 
				,
				
				{x: '2015-03-26', GOOD01:11.0 } 
				,
				
				{x: '2011-11-01', GOOD01:44.0 } 
				
				
	          ],
	          barColors: ['#00a65a', '#f56954'],
	          xkey: 'x',
	          ykeys: ['GOOD01'],
	          labels: ['零食'],
	          hideHover: 'auto'
	        });
		});
		*/
	
	</script>

</div>