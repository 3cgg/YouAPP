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
				  httpPOST('/bill.billaction/getBillsWithsCondition', form.id);
		        },
		    	errorPlacement:warningMessageAlert
			    });
		});
		
		$(function () {
	        $('#example2').dataTable({
	          "bPaginate": false,
	          "bLengthChange": false,
	          "bFilter": false,
	          "bSort": true,
	          "bInfo": false,
	          "bAutoWidth": false,
	          "bProcessing":false,
	          "bServerSide":true,
	          "fnServerData":function (sSource, aoData, fnCallback){
	        	  
	        	  alert('kk');
	        	  //fnCallback(new object());
	          }
	        });
	      });