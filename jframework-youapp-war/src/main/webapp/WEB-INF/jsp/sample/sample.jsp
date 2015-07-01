<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<div id="youapp-content">



<section class="content">

			
			<div class="col-md-6  ">
		        <div class="youapp-box  youapp-box-solid">
	                <div class="youapp-box-header  youapp-with-border">
	                  <h3 class="youapp-box-title">De-Encode Test</h3>
	                </div><!-- /.box-header -->
	                <div class="youapp-box-body ">
		                <div  class="youapp-box-body-line">
		                	
		                	<textarea class="form-control"  rows="3"  id="decode-encode" ></textarea> 
		                	
		                	<div class="form-group">
		                      <label for="encodeURI">encodeURI</label>
		                      <span  class="form-control" 
		                      	id="encodeURI" ></span>
		                    </div>
		                    <div class="form-group">
		                      <label for="encodeURIComponent">encodeURIComponent</label>
		                      <span  class="form-control" 
		                      	id="encodeURIComponent" ></span>
		                    </div>
		                	
		                	<input type="button" value="Click"  id="decode-encode-click">
		                	
		                	
		                	
		                	
		                	<script type="text/javascript">
		                	
		                	$( function (){
		                		
		                		$("#decode-encode-click").on("click",function (e){
		                			
		                			$('#encodeURI').text(encodeURI($('#decode-encode').val()));
		                			$('#encodeURIComponent').text(encodeURIComponent($('#decode-encode').val()) );
		                			
		                		});
		                		
		                	}
		                			
		                	);
		                	
		                	</script>
		                	
		                	
		                	</div>
		                	
		                	</div>
		                	</div>
		                	</div>


	        
	        <div class="col-md-6  ">
		        <div class="youapp-box  youapp-box-solid">
	                <div class="youapp-box-header  youapp-with-border">
	                  <h3 class="youapp-box-title">Linked-Request Test</h3>
	                </div><!-- /.box-header -->
	                <div class="youapp-box-body ">
		                <div  class="youapp-box-body-line">
		                	
		                	<input type="text"  id="mutil-count"  value="0" /> 
		                	<input type="button" value="Click"  id="multi-click">
		                	
		                	
		                	<script type="text/javascript">
		                	
		                	
		                	$(function (){
		                		
		                		$('#multi-click').on("click",function (e){
		                			
		                			Console(this);
		                			
		                			var count=$("#mutil-count").val();
		                			var params="";
		                			for(var i=0;i<count;i++){
		                				params=params+"index"+i+"="+i+"&";
		                			}
		                			
		                			params=params+"sdf=ad&key=我们"
		                			+"&end=000&mk=2f927de8ecdf4417be89201ddf120188&ssssssssssssssssssssssssssssssssssssssss=cvxcvxccccccccccccccccccccccccccccccccc"
		                			+"&dsfasdfasdfsafd=uoiweuroweuroo&mvkdnvlfdnkl=asdfasdufoiufosdf&mlmlsklk=sdfasdfsdf46435";
		                			
		                			GETOnLinkedRequest("/sample.sampleaction/testMultiRequest", params, function (obj){
		                				alert(obj);
		                			}, function (e){
		                				
		                			});
		                			
		                		});
		                	}
		                	);
		                	
		                	</script>
		                	
		                	
		                	
		                	
		                </div>
		               
	                </div>
	              </div><!-- /.box -->
	            </div><!-- ./col -->
					


				<div class="col-md-6  ">
		        <div class="youapp-box  youapp-box-solid">
	                <div class="youapp-box-header  youapp-with-border">
	                  <h3 class="youapp-box-title">Upload Test</h3>
	                </div><!-- /.box-header -->
	                <div class="youapp-box-body ">
		                <div  class="youapp-box-body-line">
		                	
		                	<div id="container">
							    <a id="pickfiles" href="javascript:;">[Select files]</a> 
							    <a id="uploadfiles" href="javascript:;">[Upload files]</a>
							</div>
		                	
		                	<pre id="console"></pre>
		                	<script type="text/javascript">
		                	
		                	
									var uploader = new plupload.Uploader({
								        // General settings
								        runtimes :"flash",
										browse_button : 'pickfiles', // you can pass in id...
								        url : 'http://192.168.0.100:8686/youapp/web/service/dispatch/user.useraction/uploadImage?_youapp_ticket='+$.cookie("ticket"),
								        chunk_size : '1mb',
								        unique_names : true,
								 
								        // Resize images on client-side if we can
								        resize : { width : 320, height : 240, quality : 90 },
								        
								        filters : {
								            max_file_size : '10mb',
								
											// Specify what files to browse for
								            mime_types: [
								                {title : "Image files", extensions : "jpg,gif,png"},
								                {title : "Zip files", extensions : "zip"}
								            ]
								        },
								 
										flash_swf_url : '${pageContext.request.contextPath}/plugins/plupload/Moxie.swf',
										silverlight_xap_url : '${pageContext.request.contextPath}/plugins/plupload/Moxie.xap',
								         
								        // PreInit events, bound before the internal events
								        preinit : {
								            Init: function(up, info) {
								                log('[Init]', 'Info:', info, 'Features:', up.features);
								            },
								 
								            UploadFile: function(up, file) {
								                log('[UploadFile]', file);
								 
								                // You can override settings before the file is uploaded
								                // up.setOption('url', 'upload.php?id=' + file.id);
								                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
								            }
								        },
								 
								        // Post init events, bound after the internal events
								        init : {
											PostInit: function() {
												// Called after initialization is finished and internal event handlers bound
												log('[PostInit]');
												
												document.getElementById('uploadfiles').onclick = function() {
													uploader.start();
													return false;
												};
											},
								
											Browse: function(up) {
								                // Called when file picker is clicked
								                log('[Browse]');
								            },
								
								            Refresh: function(up) {
								                // Called when the position or dimensions of the picker change
								                log('[Refresh]');
								            },
								 
								            StateChanged: function(up) {
								                // Called when the state of the queue is changed
								                log('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");
								            },
								 
								            QueueChanged: function(up) {
								                // Called when queue is changed by adding or removing files
								                log('[QueueChanged]');
								            },
								
											OptionChanged: function(up, name, value, oldValue) {
												// Called when one of the configuration options is changed
												log('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);
											},
								
											BeforeUpload: function(up, file) {
												// Called right before the upload for a given file starts, can be used to cancel it if required
												log('[BeforeUpload]', 'File: ', file);
											},
								 
								            UploadProgress: function(up, file) {
								                // Called while file is being uploaded
								                log('[UploadProgress]', 'File:', file, "Total:", up.total);
								            },
								
											FileFiltered: function(up, file) {
												// Called when file successfully files all the filters
								                log('[FileFiltered]', 'File:', file);
											},
								 
								            FilesAdded: function(up, files) {
								                // Called when files are added to queue
								                log('[FilesAdded]');
								 
								                plupload.each(files, function(file) {
								                    log('  File:', file);
								                });
								            },
								 
								            FilesRemoved: function(up, files) {
								                // Called when files are removed from queue
								                log('[FilesRemoved]');
								 
								                plupload.each(files, function(file) {
								                    log('  File:', file);
								                });
								            },
								 
								            FileUploaded: function(up, file, info) {
								                // Called when file has finished uploading
								                log('[FileUploaded] File:', file, "Info:", info);
								            },
								 
								            ChunkUploaded: function(up, file, info) {
								                // Called when file chunk has finished uploading
								                log('[ChunkUploaded] File:', file, "Info:", info);
								            },
								
											UploadComplete: function(up, files) {
												// Called when all files are either uploaded or failed
								                log('[UploadComplete]');
											},
								
											Destroy: function(up) {
												// Called when uploader is destroyed
								                log('[Destroy] ');
											},
								 
								            Error: function(up, args) {
								                // Called when error occurs
								                log('[Error] ', args);
								            }
								        }
								    });
								 
								 
								    function log() {
									
									Console(this);
									
								        var str = "";
								 
								        plupload.each(arguments, function(arg) {
								            var row = "";
								 
								            if (typeof(arg) != "string") {
								                plupload.each(arg, function(value, key) {
								                    // Convert items in File objects to human readable form
								                    if (arg instanceof plupload.File) {
								                        // Convert status to human readable
								                        switch (value) {
								                            case plupload.QUEUED:
								                                value = 'QUEUED';
								                                break;
								 
								                            case plupload.UPLOADING:
								                                value = 'UPLOADING';
								                                break;
								 
								                            case plupload.FAILED:
								                                value = 'FAILED';
								                                break;
								 
								                            case plupload.DONE:
								                                value = 'DONE';
								                                break;
								                        }
								                    }
								 
								                    if (typeof(value) != "function") {
								                        row += (row ? ', ' : '') + key + '=' + value;
								                    }
								                });
								 
								                str += row + " ";
								            } else {
								                str += arg + " ";
								            }
								        });
								 
								        var log = document.getElementById('console');
								        log.innerHTML += str + "\n";
								    }
								
									uploader.init();
								
								</script>
		                	
		                	
		                	
		                	
		                </div>
		               
	                </div>
	              </div><!-- /.box -->
	            </div><!-- ./col -->








<div class="col-md-6  ">
		        <div class="youapp-box  youapp-box-solid">
	                <div class="youapp-box-header  youapp-with-border">
	                  <h3 class="youapp-box-title">Upload JAR</h3>
	                </div><!-- /.box-header -->
	                <div class="youapp-box-body ">
		                <div  class="youapp-box-body-line">
		                	
		                	<div id="jarcontainer">
							    <a id="jarpickfiles" href="javascript:;">[Select JAR files]</a> 
							    <a id="jaruploadfiles" href="javascript:;">[Upload JAR files]</a>
							</div>
		                	
		                	<pre id="jarconsole"></pre>
		                	<script type="text/javascript">
		                	
		                	
									var jaruploader = new plupload.Uploader({
								        // General settings
								        runtimes :"flash",
										browse_button : 'jarpickfiles', // you can pass in id...
								        url : 'http://192.168.0.100:8686/youapp/web/service/dispatch/sample.sampleaction/deployComponent?_youapp_ticket='+$.cookie("ticket"),
								        chunk_size : '1mb',
								        unique_names : true,
								 
								        // Resize images on client-side if we can
								        resize : { width : 320, height : 240, quality : 90 },
								        
								        filters : {
								            max_file_size : '10mb',
								
											// Specify what files to browse for
								            mime_types: [
								                {title : "Zip files", extensions : "zip"},
								                {title : "Zip files", extensions : "jar"}
								            ]
								        },
								 
										flash_swf_url : '${pageContext.request.contextPath}/plugins/plupload/Moxie.swf',
										silverlight_xap_url : '${pageContext.request.contextPath}/plugins/plupload/Moxie.xap',
								         
								        // PreInit events, bound before the internal events
								        preinit : {
								            Init: function(up, info) {
								                jarlog('[Init]', 'Info:', info, 'Features:', up.features);
								            },
								 
								            UploadFile: function(up, file) {
								                jarlog('[UploadFile]', file);
								 
								                // You can override settings before the file is uploaded
								                // up.setOption('url', 'upload.php?id=' + file.id);
								                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
								            }
								        },
								 
								        // Post init events, bound after the internal events
								        init : {
											PostInit: function() {
												// Called after initialization is finished and internal event handlers bound
												jarlog('[PostInit]');
												
												document.getElementById('jaruploadfiles').onclick = function() {
													jaruploader.start();
													return false;
												};
											},
								
											Browse: function(up) {
								                // Called when file picker is clicked
								                jarlog('[Browse]');
								            },
								
								            Refresh: function(up) {
								                // Called when the position or dimensions of the picker change
								                jarlog('[Refresh]');
								            },
								 
								            StateChanged: function(up) {
								                // Called when the state of the queue is changed
								                jarlog('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");
								            },
								 
								            QueueChanged: function(up) {
								                // Called when queue is changed by adding or removing files
								                jarlog('[QueueChanged]');
								            },
								
											OptionChanged: function(up, name, value, oldValue) {
												// Called when one of the configuration options is changed
												jarlog('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);
											},
								
											BeforeUpload: function(up, file) {
												// Called right before the upload for a given file starts, can be used to cancel it if required
												jarlog('[BeforeUpload]', 'File: ', file);
											},
								 
								            UploadProgress: function(up, file) {
								                // Called while file is being uploaded
								                jarlog('[UploadProgress]', 'File:', file, "Total:", up.total);
								            },
								
											FileFiltered: function(up, file) {
												// Called when file successfully files all the filters
								                jarlog('[FileFiltered]', 'File:', file);
											},
								 
								            FilesAdded: function(up, files) {
								                // Called when files are added to queue
								                jarlog('[FilesAdded]');
								 
								                plupload.each(files, function(file) {
								                    jarlog('  File:', file);
								                });
								            },
								 
								            FilesRemoved: function(up, files) {
								                // Called when files are removed from queue
								                jarlog('[FilesRemoved]');
								 
								                plupload.each(files, function(file) {
								                    jarlog('  File:', file);
								                });
								            },
								 
								            FileUploaded: function(up, file, info) {
								                // Called when file has finished uploading
								                jarlog('[FileUploaded] File:', file, "Info:", info);
								            },
								 
								            ChunkUploaded: function(up, file, info) {
								                // Called when file chunk has finished uploading
								                jarlog('[ChunkUploaded] File:', file, "Info:", info);
								            },
								
											UploadComplete: function(up, files) {
												// Called when all files are either uploaded or failed
								                jarlog('[UploadComplete]');
											},
								
											Destroy: function(up) {
												// Called when uploader is destroyed
								                jarlog('[Destroy] ');
											},
								 
								            Error: function(up, args) {
								                // Called when error occurs
								                jarlog('[Error] ', args);
								            }
								        }
								    });
								 
								 
								    function jarlog() {
									
									Console(this);
									
								        var str = "";
								 
								        plupload.each(arguments, function(arg) {
								            var row = "";
								 
								            if (typeof(arg) != "string") {
								                plupload.each(arg, function(value, key) {
								                    // Convert items in File objects to human readable form
								                    if (arg instanceof plupload.File) {
								                        // Convert status to human readable
								                        switch (value) {
								                            case plupload.QUEUED:
								                                value = 'QUEUED';
								                                break;
								 
								                            case plupload.UPLOADING:
								                                value = 'UPLOADING';
								                                break;
								 
								                            case plupload.FAILED:
								                                value = 'FAILED';
								                                break;
								 
								                            case plupload.DONE:
								                                value = 'DONE';
								                                break;
								                        }
								                    }
								 
								                    if (typeof(value) != "function") {
								                        row += (row ? ', ' : '') + key + '=' + value;
								                    }
								                });
								 
								                str += row + " ";
								            } else {
								                str += arg + " ";
								            }
								        });
								 
								        var log = document.getElementById('jarconsole');
								        log.innerHTML += str + "\n";
								    }
								
								jaruploader.init();
								
								</script>
		                	
		                	
		                	
		                	
		                </div>
		               
	                </div>
	              </div><!-- /.box -->
	            </div><!-- ./col -->











	</section>
	        

	        



	       
	       


</div>    
    