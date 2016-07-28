package j.jave.platform.webcomp.web.youappmvc.plugins.pageable.jquerydatatable;

import j.jave.platform.webcomp.web.youappmvc.interceptor.ServletRequestInterceptor;
import j.jave.platform.webcomp.web.youappmvc.interceptor.ServletRequestInvocation;

import java.util.Map;

/**
 * sysparamListObj.listTable=$('#sysparamListTable').initDataTable({
			url:"/sysparam/getSysParamsByPage",
			urlDataFn:function(){
				return $("#searchSysParamForm").serializeJson();
			},
			lengthChange:false,
			checkbox:true,
			ops:{
				view:function(id,rowData){
					alert('view '+id);
				},
				edit:function(id,rowData){
					alert('edit '+id);
				},
				del:function(id,rowData){
					delSysparamById(id);
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
				"width": "10%"
			}
			
			]
		});
 * @author JIAZJ
 *
 */
public class PageableInterceptor implements ServletRequestInterceptor {

	private boolean isPage(Object obj){
		boolean isPage=false;
		if(obj instanceof Map){
			Map<?, ?> map=(Map<?, ?>) obj;
			isPage=map.get("totalRecordNumber")!=null
			&&map.get("totalPageNumber")!=null
			&&map.get("pageable")!=null
			&&map.get("content")!=null;
		}
		return isPage;
	}
	
	private int getTotalRecordNumber(Object obj){
		Map<?, ?> map=(Map<?, ?>) obj;
		return Integer.valueOf(String.valueOf(map.get("totalRecordNumber")));
	}
	
	private int getTotalPageNumber(Object obj){
		Map<?, ?> map=(Map<?, ?>) obj;
		return Integer.valueOf(String.valueOf(map.get("totalPageNumber")));
	}
	
	private Object getContent(Object obj){
		Map<?, ?> map=(Map<?, ?>) obj;
		return map.get("content");
	}
	
	
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation)  throws Throwable {
		Object obj=servletRequestInvocation.proceed();
//		if(obj instanceof InvokeResultVO){
//			InvokeResultVO invokeResultVO=(InvokeResultVO) obj;
//			if(invokeResultVO.isSuccess()){
//				Object pageObj=invokeResultVO.getData();
//				if(isPage(pageObj)){
//					DataTable dataTable=new DataTable();
//					dataTable.setDraw(0);
//					dataTable.setRecordsFiltered(getTotalRecordNumber(pageObj));
//					dataTable.setRecordsTotal(getTotalRecordNumber(pageObj));
//					dataTable.setData(getContent(pageObj));
//					invokeResultVO.setData(dataTable);
//				}
//			}
//		}
		return obj;
	}
	
	
}
