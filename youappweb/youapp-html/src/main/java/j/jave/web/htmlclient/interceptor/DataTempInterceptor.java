package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.DefaultHtmlFileService;
import j.jave.web.htmlclient.HtmlService;
import j.jave.web.htmlclient.request.RequestVO;
import j.jave.web.htmlclient.response.ResponseModel;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author JIAZJ
 *
 */
public class DataTempInterceptor implements DataRequestServletRequestInterceptor {
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		RequestVO requestVO=servletRequestInvocation.getRequestVO();
		if("/sample/datatables/getDatatables".equals(requestVO.getEndpoint())){
			return getPageble(servletRequestInvocation);
		}
		if("/develop/html/setHtmlPath".equals(requestVO.getEndpoint())){
			return setHtmlPath(servletRequestInvocation);
		}
		if("/develop/html/getHtmlPath".equals(requestVO.getEndpoint())){
			return getHtmlPath(servletRequestInvocation);
		}
		if("/develop/html/getUseCache".equals(requestVO.getEndpoint())){
			return getUseCache(servletRequestInvocation);
		}
		if("/develop/html/setUseCache".equals(requestVO.getEndpoint())){
			return setUseCache(servletRequestInvocation);
		}
		return servletRequestInvocation.proceed();
	}
	
	private HtmlService htmlService=JServiceHubDelegate.get().getService(this, HtmlService.class);
	
	private Object getUseCache(DataRequestServletRequestInvocation servletRequestInvocation){
		ResponseModel responseModel=ResponseModel.newSuccess();
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("useCache", String.valueOf(htmlService.isUseCache()));
		responseModel.setData(data);
		return responseModel;
	}
	
	private Object setUseCache(DataRequestServletRequestInvocation servletRequestInvocation){
		Map<String, Object> obj= JJSON.get().parse(servletRequestInvocation.getRequestVO().getFormData());
		String useCache=String.valueOf(obj.get("useCache"));
		if(JStringUtils.isNullOrEmpty(useCache)){
			useCache="";
		}
		htmlService.setUseCache("true".equalsIgnoreCase(useCache)
				||"on".equalsIgnoreCase(useCache));
		ResponseModel responseModel=ResponseModel.newSuccess();
		return responseModel;
	}
	
	private Object getHtmlPath(DataRequestServletRequestInvocation servletRequestInvocation){
		ResponseModel responseModel=ResponseModel.newSuccess();
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("htmlPath", DefaultHtmlFileService.getHtmlPath());
		responseModel.setData(data);
		return responseModel;
	}
		
	private Object setHtmlPath(DataRequestServletRequestInvocation servletRequestInvocation){
		Map<String, Object> obj= JJSON.get().parse(servletRequestInvocation.getRequestVO().getFormData());
		String htmlPath=String.valueOf(obj.get("htmlPath"));
		DefaultHtmlFileService.clearHtmlPath();
		for(String path:htmlPath.split(";")){
			if(JStringUtils.isNotNullOrEmpty(path)){
				DefaultHtmlFileService.addHtmlPath(path.trim());
			}
		}
		return getHtmlPath(servletRequestInvocation);
	}
	/**
	 * /sample/datatables/getDatatables
	 * @param servletRequestInvocation
	 * @return
	 */
	private Object getPageble(DataRequestServletRequestInvocation servletRequestInvocation){
		Map<String, Object> obj= JJSON.get().parse(servletRequestInvocation.getRequestVO().getPaginationData());
		int page=Integer.parseInt(String.valueOf(obj.get("pageNumber")));
		int size=Integer.parseInt(String.valueOf(obj.get("pageSize")));
		ResponseModel responseModel=ResponseModel.newSuccess();
		
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<93;i++){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", JUniqueUtils.unique());
			map.put("code", "code-"+i);
			map.put("value", "value-"+i);
			map.put("desc", "desc-"+i);
			list.add(map);
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("totalRecordNumber", 93);
		map.put("totalPageNumber", 10);
		map.put("pageable", new HashMap());
		int next=(page+1)*size>list.size()?list.size():(page+1)*size;
		map.put("content", list.subList(page*size,next));
		
		return responseModel.setData(map);
	}
	
	
	
	
	
	
	
	
	
}
