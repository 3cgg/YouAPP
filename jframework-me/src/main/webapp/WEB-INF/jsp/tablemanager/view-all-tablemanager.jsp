<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            账单管理
	            <small>历史账单</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> 应用</a></li>
	            <li class="active">历史账单</li>
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
	                  <h3 class="box-title">列表</h3>
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
	                  <table class="table table-hover">
	                  <tr>
	                      <th>金额（￥）</th>
	                      <th>商品名称</th>
	                      <th>商品类型</th>
	                      <th>购物时间</th>
	                      <th>购物地址</th>
	                      <th>购物人</th>
	                      <th>备注</th>
	                    </tr>
	                  <c:forEach items="${ bills }" var="bill">
	                  <tr id="${bill.id }"  style="cursor: pointer;" 
	                  class="youapp-popover-mark" 
	                  data-youappDelete="/bill.billaction/deleteBill"
	                  data-youappView="/bill.billaction/toViewBill"
	                  >
	                      <td>${bill.money }</td>
	                      <td>${bill.goodName }</td>
	                      <td>${bill.goodTypeName }</td>
	                      <td>${bill.billTime }</td>
	                      <td>
	                      	<c:if test="${bill.mallCode=='OTHERS' }">
	                      		${bill.mallName}
	                      	</c:if>
	                      <c:if test="${bill.mallCode!='OTHERS' }">
	                      		${bill.mallCodeName}
	                      	</c:if>
	                      </td>
	                      <td>${bill.userCodeName}</td>
	                      <td>${bill.description}</td>
	                    </tr>
	                  </c:forEach>
	                  
	                  </table>
	                  
	                   <table id="example2" class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                          Explorer 4.0</td>
                        <td>Win 95+</td>
                        <td> 4</td>
                        <td>X</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                          Explorer 5.0</td>
                        <td>Win 95+</td>
                        <td>5</td>
                        <td>C</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                          Explorer 5.5</td>
                        <td>Win 95+</td>
                        <td>5.5</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet
                          Explorer 6</td>
                        <td>Win 98+</td>
                        <td>6</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>Internet Explorer 7</td>
                        <td>Win XP SP2+</td>
                        <td>7</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Trident</td>
                        <td>AOL browser (AOL desktop)</td>
                        <td>Win XP</td>
                        <td>6</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Gecko</td>
                        <td>Firefox 1.0</td>
                        <td>Win 98+ / OSX.2+</td>
                        <td>1.7</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Gecko</td>
                        <td>Firefox 1.5</td>
                        <td>Win 98+ / OSX.2+</td>
                        <td>1.8</td>
                        <td>A</td>
                      </tr>
                      <tr>
                        <td>Gecko</td>
                        <td>Firefox 2.0</td>
                        <td>Win 98+ / OSX.2+</td>
                        <td>1.8</td>
                        <td>A</td>
                      </tr>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                      </tr>
                    </tfoot>
                  </table>
                  <div class="row">
                  	<div class="col-xs-12"  >
                  	<nav style="float: right;" >
					  <ul class="pagination">
					    <li>
					      <a href="#" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					    <li><a href="#">1</a></li>
					    <li><a href="#">2</a></li>
					    <li><a href="#">3</a></li>
					    <li><a href="#">4</a></li>
					    <li><a href="#">5</a></li>
					    <li>
					      <a href="#" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					  </ul>
					</nav>
                  	</div>
                  </div>
	              
	                  
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div>
          </div>
				
		
	        </section>
</div>


<script src="${pageContext.request.contextPath}/js/tablemanager/view-all-tablemanager.jsp.js" type="text/javascript"></script>