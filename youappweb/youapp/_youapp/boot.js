$_youapp.ready(function(){
	$_youapp.$_codeTable.codeTable({
		getType:function(data){
			return data.type;
		},
		getCode:function(data){
			return data.code;
		},
		getName:function(data){
			return data.codeName;
		},
		getDesc:function(data){
			return data.description;
		},
		fnDatas:function(callback){
			
			$_youapp.$_data.ajaxGet({
				url:'/codetablemanager/getParamCodesByPage',
				formData:{},
				paginationData:$.extend({},{
			  		pageNumber:0,
					pageSize:100000,
					orders:[]
			  		}),
		  		success:function(data){
		  			callback(data.data);
		  		}
			});
		},
		fnData:function(type,callback){
			var data=[{
				code:'test-'+type+'-code-1',
				codeName:'test-'+type+'-name-1',
				desc:type
			},
			{
				code:'test-'+type+'-code-2',
				codeName:'test-'+type+'-name-2',
				desc:type
			}
			];
			callback(data);
		}
	});
	
	
	$(document).on('click','.box > .box-header > .box-tools > [data-widget="collapse"]',function(event){
		$(this).closest('.box-header').next('.box-body').toggle('slow');
		var tag=$(this).find('i');
		if(tag.hasClass('fa-minus')){
			tag.removeClass('fa-minus');
			tag.addClass('fa-plus');
		}
		else if(tag.hasClass('fa-plus')){
			tag.removeClass('fa-plus');
			tag.addClass('fa-minus');
		}
	});
});