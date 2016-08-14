(function(){
	function Layout($dom){
		var $htl=$dom;
		this.dataHtmlUrls=function(){
			var all=$_youapp.$_util.newList();
			if($htl.attr("data-htmlurl")!=undefined){
				all.add($htl);
			}
			var $foundHtmlUrl=$htl.find('[data-htmlurl]');
			if($foundHtmlUrl.length>0){
				for(var i=0;i<$foundHtmlUrl.length;i++){
					all.add($($foundHtmlUrl[i]));
				}
			}
			return all.val();
		}
		
		this.requset=function(){
			var $allHtmlUrlls=this.dataHtmlUrls();
			for(var i=0;i<$allHtmlUrlls.length;i++){
				var $htmlUrl=$($allHtmlUrlls[i]);
				var layoutId=$htmlUrl.data('layoutid');
				var htmlUrl=$htmlUrl.data('htmlurl');
				if(htmlUrl==''){ 
					continue;
				}
				var requsetVO={
						layoutId:layoutId,
						htmlUrl:htmlUrl
				}
				$_youapp.$_util.ajaxGet({
					url:$_youapp.$_config.getHtmlEndpoint(),
					data:{data:$_youapp.$_util.json(requsetVO)},
					success:function(data){
						var resp=JSON.parse(data);
						$layoutDom=$_youapp.$_layout.getLayoutDom(resp.htmlDef.layoutId);
						var param={};
						if(resp.htmlParam){
							param=JSON.parse(resp.htmlParam);
						}
						var token={};
						if(resp.token){
							token=JSON.parse(resp.token);
						}
						$layoutDom.data('param',param);
						$layoutDom.data('token',token);
						var layout=new Layout($(resp.html));
						layout.draw(resp.htmlDef.layoutId);
					},
					error:function(data){
//						var layout=new Layout(data);
//						layout.draw();
					}
					})
			}
		}
		
		this.appendTo=function(layoutId){
			var $target=$(document).find('[data-layoutid="'+layoutId+'"]');
			$htl.appendTo($target);
		}
		
		this.bingSelect=function(){
			if($_youapp.$_codeTable&&$_youapp.$_codeTable.defaultDraw){
				$_youapp.$_codeTable.defaultDraw($htl);
			}
		}
		
		this.draw=function(layoutId){
			this.bingSelect();
			if(layoutId){
				this.appendTo(layoutId);
			}
			this.requset();
		}
	}
	
	
	function LayoutDraw(){
		this.draw=function(layoutId,$dom){
			var layout=new Layout($dom);
			layout.draw(layoutId);
		}
		/*
		 * $_youapp.$_layout.drawTab('Me','MMMEEE','ui/pages/default.html','layoutTab')
		 * */
		this.drawTab=function(id,title,htmlurl,tabDomId){
			if($_youapp.$tabs[tabDomId].exists(id)){
				$_youapp.$tabs[tabDomId].active(id);
				return ;
			}
			var tabContent=new TabContent($('#'+tabDomId));
			tabContent.draw(id,title,htmlurl);
			$_youapp.$tabs.tabDomId=$('#'+tabDomId).tabulous({
				effect: 'scaleUp'
			});
			$_youapp.$tabs.tabDomId.active(id);
		}
		
		this.removeTab=function(id,tabDomId){
			if($_youapp.$tabs[tabDomId].exists(id)){
				$_youapp.$tabs[tabDomId].remove(id);
			}
		}
		
		this.isLayout=function($_dom){
			var $dom=$($_dom);
			return $dom.is('div')&&$dom.attr("data-layoutid");
		}
		
		this.getClosestLayoutId=function($_dom){
			var $dom=$($_dom);
			var $parent=$dom;
			var cycl=0
			do{
				if(this.isLayout($parent)){
					return $parent.data('layoutid');
				}
				cycl++;
			}while(cycl<1000&&($parent=$parent.parent()));
		}
		
		this.replace=function(layoutId,htmlurl){
			var $target=$(document).find('[data-layoutid="'+layoutId+'"]');
			var $dom;
			if($target){
				$target.empty();
				$target.data('htmlurl',htmlurl);
				$dom=$target;
			}
			else{
				var layoutString=this.layoutDomString(layoutId,htmlurl);
				$dom=$(layoutString);
			}
			this.draw(layoutId,$dom);
		}
		
		this.layoutDomString=function(layoutId,htmlurl){
			return '<div data-layoutId="'+id+'" data-htmlUrl="'+htmlurl+'" ></div>';
		}
		
		this.getLayoutDom=function(layoutId){
			return $(document).find('div[data-layoutId="'+layoutId+'"]');
		}
		
		this.getParameter=function($dom) {
			var layoutId=this.getClosestLayoutId($dom);
			var layoutDom=this.getLayoutDom(layoutId);
			return $(layoutDom).data('param');
		}
		
		this.getToken=function($dom) {
			var layoutId=this.getClosestLayoutId($dom);
			var layoutDom=this.getLayoutDom(layoutId);
			return $(layoutDom).data('token');
		}
		
	}
	
	
	function TabContent($tabDom){
		var $htl=$tabDom;
		this.getMenu=function(id,title){
			var $li= $('<li><a id="'+id+'"   href="#'+id+'" title="'+title+'">'+title+'</a></li>');
			return $li;
		}
		
		this.getSlice=function(id,title,htmlurl){
			return $('<div id="'+id+'">'
					+'<div data-layoutId="tab-'+id+'" data-htmlUrl="'+htmlurl+'"   style="height: auto;"></div>'
					+'</div>');
		}
		
		this.draw=function(id,title,htmlurl,close){
			
			var menu=this.getMenu(id,title);
			var sliceView=this.getSlice(id,title,htmlurl);
			menu.insertAfter($htl.children('ul.tabul').find('li:last'));
			$btns = $('<div class="action-panel">' +
	                '<span class="cancel"  data-tabid="'+$htl.attr('id')+'"  data-tabmenuid="'+id+'" >删除</span>' +
	                '</div>').appendTo(menu);
			menu.on( 'mouseenter', function() {
	            $(this).children('div.action-panel').stop().animate({height: 24});
	        });

			menu.on( 'mouseleave', function() {
				$(this).children('div.action-panel').stop().animate({height: 0});
	        });
			
			$btns.on('click',function(event){
				var menuid=$(this).children('span.cancel').data('tabmenuid');
				var tabid=$(this).children('span.cancel').data('tabid');
				$_youapp.$_layout.removeTab(menuid,tabid);
				$_youapp.$tabs[tabid].active();
			});
			
			sliceView.appendTo($htl.children('div.tabcontainer'));
			$_youapp.$_layout.draw(null,sliceView);
		}
		
	}
	
	
	function DataExchange(){
		
		/**
		 * {
		 * url:'',
		 * formData:'',
		 * paginationData:''
		 * }
		 */
		this.ajaxGet=function(options){
			
			var paginationDataOpts={
			};
			if(options.paginationData){
				paginationDataOpts=$.extend(paginationDataOpts,{
					paginationData:$_youapp.$_util.json({
						pageNumber:options.paginationData.pageNumber,
						pageSize:options.paginationData.pageSize,
						orders:options.paginationData.orders
					})
				})
			}
			
			var formDataOpts={};
			if(options.formData){
				formDataOpts=$.extend(formDataOpts,{
					formData:$_youapp.$_util.json(options.formData)
				})
			}
			
			
			$_youapp.$_util.ajaxGet({
				url:$_youapp.$_config.getDataEndpoint()+options.url,
				data:{data:$_youapp.$_util.json($.extend({},{
					endpoint:options.url
			  		},formDataOpts,paginationDataOpts
			  		))},
		  		success:function(data){
		  			var resp=JSON.parse(data);
		  			if(!resp.success){
		  				$_youapp.$_toast.error("error",resp.data);
		  				return;
		  			}
		  			options.success(resp.data);
		  			}
				
			});
		}
		
		/**
		 * {
		 * url:'',
		 * formData:'',
		 * paginationData:'',
		 * token:'',
		 * success:function(){},
		 * failure:function(resp){}
		 * }
		 */
		this.ajaxPost=function(options){
			var paginationDataOpts={
			};
			if(options.paginationData){
				paginationDataOpts=$.extend(paginationDataOpts,{
					paginationData:$_youapp.$_util.json({
						pageNumber:options.paginationData.pageNumber,
						pageSize:options.paginationData.pageSize,
						orders:options.paginationData.orders
					})
				})
			}
			
			var formDataOpts={};
			if(options.formData){
				formDataOpts=$.extend(formDataOpts,{
					formData:$_youapp.$_util.json(options.formData)
				})
			}
			
			var tokenOpts={};
			if(options.token){
				tokenOpts=$.extend(tokenOpts,{
					token:$_youapp.$_util.json(options.token)
				})
			}
			
			
			$_youapp.$_util.ajaxPost({
				url:$_youapp.$_config.getDataEndpoint()+options.url,
				data:{data:$_youapp.$_util.json($.extend({},{
					endpoint:options.url
			  		},formDataOpts,paginationDataOpts,tokenOpts
			  		))},
		  		success:function(data){
		  			var resp=JSON.parse(data);
		  			if(!resp.success){
		  				$_youapp.$_toast.error("error",resp.data);
		  				if(options.failure){
		  					options.failure(resp);
		  				}
		  				return;
		  			}
		  			options.success(resp.data);
		  			}
				
			});
		}
		
		/**
		 * {
		 * url:'',
		 * formSelector:'',
		 * success:function(){},
		 * failure:function(resp){}
		 * }
		 */
		this.submitForm=function(options){
			var $form=$(options.formSelector);
			var layoutId=$_youapp.$_layout.getClosestLayoutId($form);
			var layoutDom=$_youapp.$_layout.getLayoutDom(layoutId);
			var token=$(layoutDom).data('token');
			this.ajaxPost({
				url:options.url,
				formData:$_youapp.$_util.serializeObj(options.formSelector),
				token:token,
				success:function(data){
					if(options.success){
						options.success(data);
					}
		  		},
		  		failure:function(resp){
		  			var $form=$(options.formSelector);
		  			var layoutId=$_youapp.$_layout.getClosestLayoutId($form);
					var layoutDom=$_youapp.$_layout.getLayoutDom(layoutId);
					var token={};
					if(resp.token){
						token=resp.token;
					}
					return $(layoutDom).data('token',token);
		  		}
			});
		}
	}
	
	function HtmlExchange(){
		this.htmlView=function(htmlUrl,layoutId){
			$_youapp.$_layout.replace(layoutId,htmlUrl);
		}
		
		this.linkView=function($linkDom,htmlUrl,layoutId){
			if(!layoutId)
				layoutId=$_youapp.$_layout.getClosestLayoutId($linkDom);
			this.htmlView(htmlUrl,layoutId);
		}
		
		this.newTab=function($aDom){
			/*
			 * data-url="ui/pages/tabs.html"
            data-urldata=""
            data-tabid="layoutTab"
            data-tabmenuid="tabDemo"
            data-tabmenutitle="Tab-Demo"
			 */
			$a=$($aDom);
			var url=$a.data("url");
			var urldata=$a.data("urldata");
			var tabid=$a.data("tabid");
			var tabmenuid=$a.data("tabmenuid");
			var tabmenutitle=$a.data("tabmenutitle");
			$_youapp.$_layout.drawTab(tabmenuid,tabmenutitle,url,tabid);
		}
	}
	
	(function(){
		window.$_youapp.$_layout=new LayoutDraw();
		window.$_youapp.$_layout.draw('body',$('body'));
		window.$_youapp.$_data=new DataExchange();
		window.$_youapp.$_html=new HtmlExchange();
		window.$_youapp.pageTemplate={
				ajaxGet:function(options){
					$_youapp.$_data.ajaxGet(options);
				},
				submitForm:function(options){
					$_youapp.$_data.submitForm(options);
				},
				ajaxPost:function(options){
					$_youapp.$_data.ajaxPost(options);
				},
				codeTable:function($dom){
					$_youapp.$_codeTable.draw($($dom));
				}
		}
		
		
		window.$_youapp.$_toast=(function(){
			
			function def(opts){
				var defOpts={
					stack:10,
					position: 'top-right'
				}
				return $.extend({},defOpts,opts);
			}
			
			this.success=function(heading,text){
				$.toast(def({
				    heading: heading,
				    text: text,
				    showHideTransition: 'slide',
				    icon: 'success'
				}));
			}
			
			this.warning=function(heading,text){
				$.toast(def({
				    heading: heading,
				    text: text,
				    showHideTransition: 'slide',
				    icon: 'warning'
				}));
			}
			
			this.info=function(heading,text){
				$.toast(def({
				    heading: heading,
				    text: text,
				    showHideTransition: 'slide',
				    icon: 'info'
				}));
			}
			
			this.error=function(heading,text){
				$.toast(def({
				    heading: heading,
				    text: text,
				    showHideTransition: 'slide',
				    hideAfter: 10000,
				    icon: 'error'
				}));
			}
			return {
				success:this.success,
				info:this.info,
				warning:this.warning,
				error:this.error
			}
		})();
		
		window.$_youapp.ready=function(func){
			$(function(){
				try{
					func();
				}catch (e) {
					$_youapp.$_util.log(e);
				}
			});
		}
	})();
	
	$.fn.extend({
		goView:function(htmlUrl,data,layoutId){
			if(data){
				var defData={
						htmlRequestTime:new Date().getTime()
				}
				htmlUrl=htmlUrl+'?param='+$_youapp.$_util.json($.extend({},defData,data));
			}
			window.$_youapp.$_html.linkView($(this),htmlUrl,layoutId);
		},
		getViewParam:function(){
			return $_youapp.$_layout.getParameter(this);
		},
		serializeObj:function(){
			return $_youapp.$_util.serializeObj(this);
		}
	});
	
	setTimeout(function(){
		var minHeight = $(window).outerHeight()
		- $(".main-header").outerHeight()
		- $(".main-footer").outerHeight();
		$(".content-wrapper").css("min-height", minHeight + "px");
	},1000);
	
	$.validator.addMethod("valueNotEquals", function(value, element, arg){
		  return arg != value;
		 }, "Value must not equal arg .");

})(window);
