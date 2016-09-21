	 <!-- Main content -->
    <section class="content"   id="search${modelModel.simpleClassName}Section"   ms-controller="${modelModel.variableName}_view">
      
      <div class="box box-info">
      	<div class="box-header with-border">
          <h3 class="box-title"></h3>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
          </div>
        </div>
              <div class="box-body">
              <form class="form-horizontal"  id="search${modelModel.simpleClassName}Form">
              	
              	<#list criteriaModel.modelFields as modelField>
					private String ${modelField.property};
					
				</#list>
              
                <div class="form-group">
                  <label for="code" class="col-sm-1 control-label">编码</label>

                  <div class="col-sm-5">
                    <input type="text"  name="code" class="form-control" id="code" placeholder="编码">
                  </div>
                  <label for="value" class="col-sm-1 control-label">参数值</label>

                  <div class="col-sm-5">
                    <input type="text"  name="value" class="form-control" id="value" placeholder="参数值">
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="desc" class="col-sm-1 control-label">详细描述</label>

                  <div class="col-sm-11">
                    <input type="text"  name="desc" class="form-control" id="desc" placeholder="描述">
                  </div>
                </div>
                
                <div class="form-group">
                	<label for="desc" class="col-sm-1 control-label"></label>
                	<div class="col-sm-2 col-lg-1">
	                	<input id="search${modelModel.simpleClassName}Btn" type="button" class="form-control btn-primary"  value="查询"   >
	                </div>
                </div>
                </form>
              </div>
          </div>
          <!-- /.box -->
      
    	
    	<div class="box box-info">
	    	<!--   .box-header   -->
	        <div class="box-header with-border">
	          <button type="submit" class="btn btn-primary btn-sm"
	          onclick='$(this).goView("${uiListModel.uiContext.addFilePath}");'
	          >新增</button>
	          <div class="box-tools pull-right">
	            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	          </div>
	          
	        </div>
	        <!-- /.box-header -->
	        <div class="box-body" >
	          <table id="${modelModel.variableName}ListTable" 
	          		class="" 
	          			cellspacing="0" width="100%">
	            <thead>
					<tr>
						<th></th>
						<th>主键</th>
						<th>编码</th>
						<th>参数值</th>
						<th>描述</th>
						<th>操作</th>
					</tr>
				</thead>
            </table>
	          
	          
	          <!-- /.row -->
	        </div>
      </div>
    
    </section>
    <script type="text/javascript">
    $_youapp.ready(function() {
    	
    	var page=$.extend({
    		root:$("#search${modelModel.simpleClassName}Section"),
    		model:{
    			vm:avalon.define({
    		        $id: "${modelModel.variableName}_view",
    		        data: {}
    		    })
    		}
    	},$_youapp.pageTemplate);
    	
    	page.listTable=page.root.find('#${modelModel.variableName}ListTable').initDataTable({
			url:"${uiListModel.uiContext.pageMethodUrl}",
			urlDataFn:function(){
				return page.root.find("#search${modelModel.simpleClassName}Form").serializeObj();
			},
			lengthChange:false,
			checkbox:true,
			ops:{
				view:function(id,rowData){
                    page.root.find('#${modelModel.variableName}ListTable').goView('${uiListModel.uiContext.viewFilePath}',{"id":id});
				},
				edit:function(id,rowData){
					page.root.find('#${modelModel.variableName}ListTable').goView('${uiListModel.uiContext.editFilePath}',{"id":id});
				},
				del:function(id,rowData){
					page.ajaxGet({
						  url:'${uiListModel.uiContext.deleteByIdMethodUrl}',
						  formData:{'id':id},
						  success:function(data){
							  page.listTable.ajax.reload();
						  }
						});
				}
			},
			columns : [ 
			{
				"data" : "id",
				"orderable" : false,
				 "width": "10%"
			}, 
			{
				"data" : "code",
				"width": "10%"
			},
			{
				"data" : "value",
				"width": "10%"
			}, 
			{
				"data" : "desc",
				"width": "20%"
			}
			
			]
		});
		
		page.root.find("#search${modelModel.simpleClassName}Btn").on("click",function(){
			page.listTable.ajax.reload();
		})
		
	});
	</script>

