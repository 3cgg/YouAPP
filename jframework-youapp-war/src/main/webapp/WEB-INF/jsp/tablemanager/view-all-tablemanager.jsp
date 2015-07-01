<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            Table Manager
	            <small>Table</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> APP</a></li>
	            <li class="active">Table</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
					<c:import url="../message-alert.jsp"></c:import>
					<c:import url="../message-success.jsp"></c:import>
					<c:import url="../popover-action-on-table.jsp"></c:import>
					<div class="row">
	            <div class="col-xs-12">
	              <div class="box">
	                <div class="box-header">
	                  <h3 class="box-title">${table.tableName }(${table.modelName })</h3>
	                  <div class="box-tools">
	                  <form id="billSearchForm">
	                    <div class="input-group">
	                      <input type="text" name="billSearchCriteria.latestMonth" class="form-control input-sm pull-right" style="width: 120px;" 
	                      placeholder="最近几个月的记录"  />
	                      <div class="input-group-btn">
	                        <button type="submit" class="btn btn-sm btn-default"  style="font-size: 18px;"><i class="fa fa-search"></i></button>
	                      </div>
	                    </div>
	                    </form>
	                  </div>
	                </div><!-- /.box-header -->
	                <div class="box-body table-responsive no-padding">
	                  
	                   <table id="tableinfo" class="table table-bordered table-hover">
                   
                  
                  </table>

	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div>
          </div>
				
		
	        </section>
</div>
<script type="text/javascript">
$(function (){
	 $("#billSearchForm").validate({
		 onfocusout:false,
		 onkeyup:false,
	rules: {
	   'billSearchCriteria.latestMonth': {
		   required: true,
		   number:true,
		   max:12
	   }
	  },
	  submitHandler:function(form){
		  submitPOST('/bill.billaction/getBillsWithsCondition', form.id);
       },
   	errorPlacement:warningMessageAlert
	    });
});



$('#tableinfo').youappDataTable(
		{
			"id":"tableinfo",
			"columns":[
			           
<c:forEach items="${ columns }" var="column" varStatus="ind">
{ "mData": "${column.columnName}","sTitle":"${column.columnName}"}
<c:if test="${! ind.last }">
,
</c:if>
</c:forEach>
		  	],
		  	"serverParams":getServerParams,
		  	"url":"/tablemanager.tablemanageraction/getRecords",
		  	
		  	"isPopover":false,
		  	"pageSize":6,
		  	//"youappDeleteUrl":"/tablemanager.tablemanageraction/getRecord",
            //"youappViewUrl":"/tablemanager.tablemanageraction/getRecord",
            /*
            "youappParamExt":function (){
            	return "modelName=j.jave.framework.components.login.model.User";
            },
            */
            "fnCreatedRow":function( nRow, aData, iDataIndex ){
            	
            },
			  "fnDrawCallback":function( oSettings ){
				  
			  }
		}
		);

function getServerParams(){
	var serverParams=[
					{ "name":"tableSearch.modelName","value":"${table.modelName}"}
					,{"name":"tableSearch.tableName","value": "${table.tableName}" }
	                  ];
	return serverParams;
}
</script>

<!-- <script src="${pageContext.request.contextPath}/js/tablemanager/view-all-tablemanager.jsp.js" type="text/javascript"></script>-->

