<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            体重趋势图
	            <small>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='GET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">体重趋势图</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
					
					<div class="box box-info">
                <div class="box-header">
                  <h3 class="box-title">趋势图</h3>
                </div>
                <div class="box-body chart-responsive">
                  <div class="chart" id="line-chart" style="height: 300px;"></div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
		
	        </section>
	<script type="text/javascript">

		$(function (){
			// LINE CHART
	        var line = new Morris.Line({
	          element: 'line-chart',
	          resize: true,
	          data: [
					<c:forEach items="${lineCharts }" var="lineChar"  varStatus="ind">
					<c:if test="${! ind.last }">
					{y: '${lineChar.xvalue}', item1: '${lineChar.yvalue}' } ,
					</c:if>
					<c:if test="${ind.last }">
					{y: '${lineChar.xvalue}', item1: '${lineChar.yvalue}' } 
					</c:if>
					</c:forEach>
	          ],
	          xkey: 'y',
	          ykeys: ['item1'],
	          labels: ['体重（KG）'],
	          lineColors: ['#3c8dbc'],
	          hideHover: 'auto',
	          parseTime:false
	        });
		});
		
	</script>

</div>