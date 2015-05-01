
// add additional property "sortColumn" for order by function.

(function (){
	
	var YouAPPDataTable=function (initsettings){
		
		var settings=$.extend({},YouAPPDataTable.settings,initsettings);
		var columns=settings.columns;
		function _yinit(){
			$('#'+settings.id).dataTable({
		     "bPaginate": true,
		     "bLengthChange" : false,// 显示每行记录数
		     "bFilter" : true,// 搜索栏
		     "iDisplayLength" :settings.pageSize,// 每页显示行数
		     "bFilter": false,
		     "bSort": true,
		     "bInfo": true,
		     "bAutoWidth": false,
		     "bProcessing":false,
		     "bServerSide":true,
		     "aoColumns":columns,
		     "sAjaxSource":settings.url,
		     "fnServerParams": function ( aoData ) { 
		    	 
		    	 var extfnServerParams=settings.serverParams;
		    	 if(typeof(extfnServerParams)!="undefined"){
		    		 var params=[];
		    		 if(typeof(extfnServerParams)=='function'){
		    			 params=extfnServerParams();
		    		 }
		    		 
		    		 $.each(params,function(index,val){
		    			 aoData.push(val);
		    		 });
		    	 }
		    	 
		    	 // add sort params. 
		    	 var sortColumn="";
		    	 var sortType="";
		    	 for(var i=0;i< aoData.length;i++){
		    		 var obj=aoData[i];
		    		 if("iSortCol_0"==obj.name){
		    			 
		    			 if(columns[obj.value].sortColumn!==undefined){
		    				 sortColumn=columns[obj.value].sortColumn ;
		    			 }
		    			 else{
		    			 sortColumn=columns[obj.value].mData ;
		    			 }
		    		 }
		    		 if("sSortDir_0"==obj.name){
		    			 sortType=obj.value ;
		    		 }
		    	 }
		    	 aoData.push({"name":"sortColumn","value": sortColumn });
		    	 aoData.push({"name":"sortType","value": sortType });
		    	 },
		     "fnServerData":function (sUrl, aoData, fnCallback, oSettings ){
		   	  	
		    	 GET(sUrl, aoData, 
						function(json){
		    		 		// the value passed is already JSON .
							fnCallback(json);
							
						});
		     },
		     "fnCreatedRow": function( nRow, aData, iDataIndex ) {
		    	 // check whether need popover on the table. 
		    	 var isPopover=settings.isPopover;
		    	 if(isPopover){
		    		 
	 		    	 $(nRow).addClass("youapp-popover-mark");
	 		    	 $(nRow).addClass("youapp-hand");
	 		    	 
	 		    	 var deleteUrl=settings.youappDeleteUrl;
	 		    	 if(typeof(deleteUrl)!="undefined"&&deleteUrl!=null&&deleteUrl!=""){
	 		    		 $(nRow).attr("data-youappDelete",deleteUrl);
	 		    	 }
	 		    	var viewUrl=settings.youappViewUrl;
		 		  	 if(typeof(viewUrl)!="undefined"&&viewUrl!=null&&viewUrl!=""){
		 		  		$(nRow).attr("data-youappView",viewUrl);
	 		    	 }
		 		  	 
		 		  	var youappParamExt=settings.youappParamExt;
		 		  	 if(typeof(youappParamExt)!="undefined"&&youappParamExt!=null&&youappParamExt!=""){
		 		  		$(nRow).attr("data-youappParamExt",youappParamExt);
	 		    	 }
		 		  	 
		    	 }
 		    	 $(nRow).attr("id",aData.ID);
 		    	 
 		    	 //callback
 		    	 var youappfnCreatedRow=settings.youappfnCreatedRow;
 		    	 if(typeof(youappfnCreatedRow)!="undefined"&&typeof(youappfnCreatedRow)=="function"){
 		    		 try{
 		    			youappfnCreatedRow( nRow, aData, iDataIndex ) ;
 		    		 }catch(e){
 		    			 
 		    		 }
 		    	 }
 		    	},
		    	  "fnDrawCallback": function( oSettings ) {  
		    		  
		    		  // check whether need popover on the table. 
		    		  	var isPopover=settings.isPopover;
				    	 if(isPopover){
	    		          // render popover mark
							renderPopoverMark();
				    	 }
				    	 
				    	 //callback 
				    	 var youappfnDrawCallback=settings.youappfnDrawCallback;
		 		    	 if(typeof(youappfnDrawCallback)!="undefined"&&typeof(youappfnDrawCallback)=="function"){
		 		    		 try{
		 		    			youappfnDrawCallback( oSettings );
		 		    		 }catch(e){
		 		    			 
		 		    		 }
		 		    	 }	 
		           }
		   });
		}
		this.yapi={
				"yinit":_yinit
		}
		
		_yinit();
	}

	YouAPPDataTable.settings={
	                          
								/**
								 * @example 
								 * "columns":[
							             { "mData": "USERNAME","sTitle":"用户名"}
							             ,{ "mData": "PASSWORD","sTitle":"密码"}
							  	]
								 */
								"columns":[],
								
								/**
								 * default @see _GLOBAL_pageSize 
								 */
	                          "pageSize":_GLOBAL_pageSize,
	                          
	                          /**
	                           * @example 
	                           * function getServerParams(){
									var serverParams=[
													{ "name":"tableSearch.modelName","value":"j.jave.framework.components.login.model.User"}
													,{"name":"tableSearch.tableName","value": "USERS" }
									                  ];
									return serverParams;
								}
	                           */
	                          "serverParams":[{},{}],
	                          
	                          
	                          /**
	                           * request via the url .
	                           * @example  "url":"/tablemanager.tablemanageraction/getRecords"
	                           */
	                          "url":"",
	                          
	                          
	                          
	                          /**
	                           * trigger popover function if true .
	                           */
	                          "isPopover":true,
	                          
	                          
	                          /**
	                           * delete url in the popover.
	                           * @example "youappDeleteUrl":"/tablemanager.tablemanageraction/getRecord"
	                           */
	                          "youappDeleteUrl":"",
	                          
	                          /**
	                           * view url in the popover
	                           * @example  "youappViewUrl":"/tablemanager.tablemanageraction/getRecord"
	                           */
	                          "youappViewUrl":"",
	                          
	                          /**
	                           * extend parameter in the popover
	                           * @example 
	                           * "youappParamExt":function (){
						       *     	return "modelName=j.jave.framework.components.login.model.User";
						       *    }
	                           */
	                          "youappParamExt":"",
	                          
	                          /**
	                           * callback when row created.
	                           */
	                          "youappfnCreatedRow":function( nRow, aData, iDataIndex ){},
	                          
	                          /**
	                           * callback when table drawed. 
	                           */
							  "youappfnDrawCallback":function( oSettings ){}
						};

	$.fn.youappDataTable = YouAPPDataTable;
	$.fn.youappDdataTableSettings = YouAPPDataTable.settings;
}()
);