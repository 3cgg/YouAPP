(function(){
	function _Config(){
		function getWebPath(){
			return window.location.protocol+"//"+window.location.host+"/"+window.location.pathname.split('/')[1];
		}
		
		this.getEndpoint=function(){
			return getWebPath();
		}
		
		this.getHtmlEndpoint=function(){
			return getWebPath()+ "/get/gethtml/";
		}
		
		this.getDataEndpoint=function(){
			return getWebPath()+"/get/getdata/";
		}
		
		this.getFileUploaderEndpoint=function(){
			return getWebPath()+"/get/fileupload/";
		}
	}
	
	window.$_youapp.$_config=new _Config();
})(window);
