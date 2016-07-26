(function(){
	function _Config(){
		this.getEndpoint=function(){
			return "http://localhost:8080/youapp-html";
		}
		
		this.getHtmlEndpoint=function(){
			return "http://localhost:8080/youapp-html/get/gethtml/";
		}
		
		this.getDataEndpoint=function(){
			return "http://localhost:8080/youapp-html/get/getdata/";
		}
		
	}
	
	window.$_youapp.$_config=new _Config();
})(window);
