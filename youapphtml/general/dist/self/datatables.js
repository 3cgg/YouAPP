$.fn.extend({
	
	getSelectedRow:function(){
		return $(this.selector).data("datatables-checked").removeNull();
	}
	,
	
	initDataTable : function(options) {
		options.serverSide =true;
		options.processing=true;
		var _columns=[]
		var checkboxColumns=[{
            "orderable":      false,
            "data":           null,
            "width":"1%",
            "title":'<input class="minimal" name="all" value="0" type="checkbox" />',
            "render": function (data, type, row, meta) {
                return '<input class="minimal" name="sub" value="' + row.id + '" type="checkbox" />';
            }
        }];
		if(options.checkbox){
			_columns=_columns.concat(checkboxColumns).concat(options.columns);
		}
		else{
			_columns=_columns.concat(options.columns);
		}
//		$wrap=$('#editable');
		var $wrap=$(this.selector);
		$(this.selector).dataTable({
			processing : options.processing,
			serverSide : options.serverSide,
			ajax : function (data, callback, settings) {
				//debugger;
				var da;
				var HtmlMenuOpt ={endpoint:options.url,
				  		data:options.urlData,
				  		success:function(data){
				  			da=data;
				  			callback(data);
				  			
				  			if(options.checkbox){
					  			
					  			$chk=$wrap.find('input[type="checkbox"].minimal');
					  			$allChk=$chk.filter('[name="all"]');
					  			$subChks=$chk.filter('[name="sub"]');
					  		//check-box
			  				  	//iCheck for checkbox and radio inputs
			  				    $chk.iCheck({
			  				      checkboxClass: 'icheckbox_minimal-blue',
			  				      radioClass: 'iradio_minimal-blue'
			  				    });
					  		
					  		
			  				  $allChk
			  				  .on('ifChecked', function(event){
			  					$subChks.iCheck('check');
			  				  }
			  				  );
			  				  
			  				 $allChk
			  				  .on('ifUnchecked', function(event){
			  					var list=$wrap.data("datatables-checked");
			  					if(list.size()==$subChks.length){
			  						$subChks.iCheck('uncheck');
			  					}
			  				  }
			  				  );
			  				  
					  		
			  				$subChks
			  				  .on('ifChecked', function(event){ 
			  					//debugger;
			  					var id=$(event.target).val();
			  					var list=$wrap.data("datatables-checked");
			  					if(list){
			  						if(!list.exists(id)){
				  						list.add(id);
				  					}
			  					}
			  					else{
			  						list=new List();
			  						list.add(id);
			  						$wrap.data("datatables-checked",list);
			  					}
			  					
			  					if(list.size()==$subChks.length){
			  						$allChk.iCheck('check');
			  					}
			  					
			  				  }
			  				  );
			  				 
			  				$subChks
			  				  .on('ifUnchecked', function(event){ 
			  					var id=$(event.target).val();
			  					var list=$wrap.data("datatables-checked");
			  					if(list){
			  						if(list.exists(id)){
				  						list.remove(id);
				  					}
			  					}
			  					$allChk.iCheck('uncheck');
			  					}
			  				  );
				  			}
				  		
				  			},
				  		page:0,
				  		size:10
				  		};
				
				
		    	kjcs.ajaxEnt.getRequest(HtmlMenuOpt);
				//debugger;
			},
			columns : _columns,
			sPaginationType: "full_numbers",
			oLanguage: {  
				"sLengthMenu": "每页显示 _MENU_ 条记录",  
				"sZeroRecords": "抱歉， 没有找到",  
				"sInfo": "显示  _START_  到  _END_ 条记录, 共  _TOTAL_  条记录",  
				"sInfoEmpty": "没有数据",  
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)", 
				"oPaginate": {  
				"sFirst": "首页",  
				"sPrevious": "前一页",  
				"sNext": "后一页",  
				"sLast": "尾页"  
				},
			sZeroRecords: "没有检索到数据"
			},
			bSort:false,
			searching:false
		});
	}
});