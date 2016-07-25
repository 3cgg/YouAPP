package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.web.htmlclient.request.RequestVO;
import j.jave.web.htmlclient.response.ResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author JIAZJ
 *
 */
public class TempInterceptor implements DataRequestServletRequestInterceptor {
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		RequestVO requestVO=servletRequestInvocation.getRequestVO();
		if("/sample/datatables/getDatatables".equals(requestVO.getEndpoint())){
			return getPageble(servletRequestInvocation);
		}
		return ResponseModel.newError().setData("sorry,no data found : "+requestVO.getEndpoint());
	}
	
	/**
	 * /sample/datatables/getDatatables
	 * @param servletRequestInvocation
	 * @return
	 */
	private Object getPageble(DataRequestServletRequestInvocation servletRequestInvocation){
		Map<String, Object> obj= JJSON.get().parse(servletRequestInvocation.getRequestVO().getPaginationData());
		int page=Integer.parseInt(String.valueOf(obj.get("page")));
		int size=Integer.parseInt(String.valueOf(obj.get("size")));
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
