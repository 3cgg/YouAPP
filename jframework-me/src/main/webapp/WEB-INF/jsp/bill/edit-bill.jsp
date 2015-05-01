<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            账单修改
	            <small>
	            	<a href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toViewBill","id=${bill.id}")'
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
	            <li class="active">账单修改</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
									
				<form role="form"  id="editBillForm">

	                  <div class="box-body">
	                  
	                  <!-- 金额 -->
	                    <div class="form-group">
	                      <label for="money">金额（￥）</label>
	                      <input type="text" class="form-control" 
	                      	name="bill.money"  value="${ bill.money }"
	                      	id="money" placeholder="金额（￥）">
	                    </div>
	                    
	                    <!-- 商品名称 -->
	                    <div class="form-group">
	                      <label for="goodName">商品名称</label>
	                      <input type="text" class="form-control" 
	                      	name="bill.goodName"  value="${bill.goodName }"
	                      	id="goodName" placeholder="商品名称">
	                    </div>
	                    
	                    <!--  商品类型  -->
	                    <div class="form-group">
	                      <label for="goodType">商品类型</label>
	                      <select id="goodType" class="form-control"   name="bill.goodType" >
	                      <c:forEach items="${goodCodes }"  var="para">
	                      	<option   value="${para.code }"   
	                      <c:if  test="${para.code  == bill.goodType }">
	                      		selected="selected"  
	                      </c:if>
	                      	
	                      	>${para.name }</option>
	                      </c:forEach>
	                      </select>
	                    </div>

						<!--  日期  -->
	                    <div class="form-group">
	                      <label for="billTime">日期</label>
		                    <div class="input-group">
		                      <div class="input-group-addon">
		                        <i class="fa fa-calendar"></i>
		                      </div>
		                      <input id="billTime" type="text" 
		                      name="bill.billTime"   value="${bill.billTime }"
		                      class="form-control" 
		                      />
		                    </div><!-- /.input group -->
	                    </div>
						
						<!--  购物地址 -->
	                    <div class="form-group">
	                      <label for="mallCode">购物地址</label>
	                      <select class="form-control" id="mallCode"   name="bill.mallCode">
	                      <c:forEach items="${mallCodes }"  var="para">
	                      	<option   value="${para.code }"  
	                      	 <c:if  test="${para.code  == bill.mallCode }">
	                      		selected="selected"  
	                      </c:if>
	                      	>${para.name }</option>
	                      </c:forEach>
	                      </select>
	                    </div>
	                    
	                    <!-- 购物地址（补充） -->
	                     <div class="form-group">
	                      <label for="mallName">购物地址（补充）</label>
	                      <textarea  class="form-control" 
	                      	name="bill.mallName" rows="3" 
	                      	id="mallName" placeholder="购物地址（补充" >${bill.mallName }</textarea>
	                    </div>
	                    
	                    <!--  谁买的 -->
	                   <div class="form-group">
	                      <label for="good-userName">购物人</label>
	                      <select id="good-userName" class="form-control"   name="bill.userCode">
	                      <c:forEach items="${userNameCodes }"  var="para">
	                      	<option   value="${para.code }"  
	                      	<c:if  test="${para.code  == bill.userCode }">
	                      		selected="selected"  
	                      </c:if>
	                      	
	                      	>${para.name }</option>
	                      </c:forEach>
	                      </select>
	                    </div>
	                    
	                    <!-- 备注 -->
	                    <div class="form-group">
	                      <label for="desc">备注</label>
	                      <textarea  class="form-control" 
	                      	name="bill.description" rows="3"
	                      	id="desc" placeholder="备注" >${bill.description}</textarea>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary"  >Submit</button>
	                  </div>
	                  
	                  <input type="hidden"  name="bill.id"  value="${bill.id }"/>
	                  <input type="hidden"  name="bill.version"  value="${bill.version }"/>
	                  
                </form>
		
	        </section>

	<script type="text/javascript">
	
	
	
	$(function (){
		$("#billTime").inputmask("yyyy-mm-dd hh:mm:ss", {"placeholder": "yyyy-mm-dd hh:mm:ss"});
	});
	
		$(function (){
			 $("#editBillForm").validate({
			rules: {
			   'bill.money': {
				   required: true,
				   number:true,
				   max:1000000
			   },
			   'bill.goodName': {
				   required: true,
				   maxlength:64
			   },
			   'bill.mallName': {
				   required: function (){
					   return "OTHERS"==$('#mallCode').val();
				   },
				   maxlength:256
			   },
			   "bill.billTime":{
				   required: true,
				   youappdate:{
					   format:"yyyy-mm-dd hh:mm:ss"
				   },
				   date:true
			   },
			   'bill.desc': {
				   required: false,
				   maxlength:512
			   }
			  },
			  submitHandler:function(form){
				  submitPOST('/bill.billaction/editBill', form.id);
		        } 
			    });
		});
		
	</script>

</div>