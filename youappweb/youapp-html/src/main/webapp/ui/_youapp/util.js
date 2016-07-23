(function(){
	function _Util(){
		
		function List() {
			this.arrys = [];
			this.position = -1;
			this.size = function() {
				return this.position + 1;
			};

			this.length = function() {
				return this.arrys.length;
			};

			this.resize = function() {
				tempArr = [ this.size() * 1.25 + 5 ];
				for (var i = 0; i < this.size(); i++) {
					tempArr[i] = this.arrys[i];
				}
				this.arrys = tempArr;
			};

			this.exists = function(id) {
				exists = false;
				var i = 0;
				for (; i < this.size(); i++) {
					if (this.arrys[i] == id) {
						exists = true;
						break;
					}
				}
				return exists;
			};

			this.indexOf = function(id) {
				exists = false;
				var i = 0;
				for (; i < this.size(); i++) {
					if (this.arrys[i] == id) {
						exists = true;
						break;
					}
				}
				return exists ? i : -1;
			};

			this.add = function(id) {
				if (this.size() == this.length()) {
					this.resize();
				}
				this.position++;
				this.arrys[this.position] = id;
			};

			this.compress = function() {
				var tempPosition = -1;
				for (var i = 0; i <= this.position; i++) {
					if (this.get(i) == null) {
						var j = i + 1;
						while (j <= this.position) {
							if (this.get(j) != null) {
								this.arrys[i] = this.get(j);
								this.arrys[j] = null;
								tempPosition = i;
								break;
							} else {
								j++;
							}
						}
					} else {
						tempPosition = i;
					}
				}
				this.position = tempPosition;
			};

			this.get = function(index) {
				return this.arrys[index];
			}

			this.remove = function(id) {
				var index = this.indexOf(id);
				if (index != -1) {
					this.arrys[index] = null;
				}
				this.compress();
			};

			this.removeNull = function() {
				var tempArr = [];
				for (var i = 0; i < this.size(); i++) {
					if (this.get(i) != null) {
						tempArr[i] = this.get(i);
					}
				}
				return tempArr;
			}
		}
		
		/**
		 *{
			url:'www.baidu.com',
			data:{},
			async:true,
			type:'GET'
			}
		 */
		function Ajax(){
			
			this.request=function(options){
				var defOptions={
						async:true,
						data:{}
				}
				var _options={};
				_options=$.extend(_options,defOptions,options);
				$.ajax({
				    url: _options.url,
				    type: _options.type,
				    //context: document.body,
				    data:_options.data,
				    async:_options.async,
				    success: function(data){
				    	$_util.log('success: '+_options.url);
				    	_options.success(data);
				    },
				    error:function(data){
				    	$_util.log('error: '+_options.url);
				    	_options.error(data);
				    }
				});
			}
		}
		
		this.ajaxGet=function(options){
			var ajax=new Ajax();
			ajax.request($.extend({},options,{type:'GET'}));
		}
		
		this.ajaxPost=function(options){
			var ajax=new Ajax();
			ajax.request($.extend({},options,{type:'POST'}));
		}
		
		this.newList=function(){
			return new List();
		}
		
		this.log=function(msg){
			if(window.console){
				window.console.log(msg);
			}
		}
		
		this.serializeArray=function(formId){
			$('#'+formId).serializeArray();
		}
		
		this.json=function(obj){
			return JSON.stringify(obj);
		}
		
	}
	
	window.$_util=new _Util();
})(window);
