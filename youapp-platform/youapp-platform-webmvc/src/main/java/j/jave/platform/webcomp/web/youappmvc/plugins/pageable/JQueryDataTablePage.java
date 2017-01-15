/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.plugins.pageable;

import j.jave.platform.data.web.model.BaseCriteria;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.jave.json.JJSONObject;
import me.bunny.kernel.jave.model.JModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the class is for JQuery DataTable plugin.
 * @author J
 */
@SuppressWarnings("serial")
public class JQueryDataTablePage<T extends JModel> extends BaseCriteria implements JJSONObject<Map<String, Object>> {

	public static final String SECHO="sEcho";
	
	public static final String IDISPLAYSTART="iDisplayStart";
	
	public static final String IDISPLAYLENGTH="iDisplayLength";
	
	public static final String SORTCOLUMN="sortColumn";
	
	public static final String SORTTYPE="sortType";
	
	public static final String ITOTALRECORDS="iTotalRecords";
	
	public static final String ITOTALDISPLAYRECORDS="iTotalDisplayRecords";
	
	public static final String AADATA="aaData";
	/**
	 * {@value JQueryDataTablePage}
	 */
	private String sEcho;
	
	/**
	 * iDisplayStart
	 */
	private int iDisplayStart;
	
	/**
	 * iDisplayLength
	 */
	private int iDisplayLength;
	
	private int iTotalRecords;
	
	private int iTotalDisplayRecords;
	
	private List<?> aaData;

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<?> getAaData() {
		return aaData;
	}

	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	
	@Override
	public Map<String, Object> serializableJSONObject() {
		Map<String, Object> pagination=new HashMap<String, Object>();
		pagination.put(ITOTALRECORDS, iTotalRecords);
		pagination.put(SECHO,sEcho); 
		pagination.put(ITOTALDISPLAYRECORDS, iTotalDisplayRecords);
		pagination.put(AADATA, aaData);
		return pagination;
	}
	
	
	public static <T extends JModel> JQueryDataTablePage<T> parse(HttpContext httpContext){
		String sEcho=httpContext.getParameter("sEcho");
		int iDisplayStart=Integer.parseInt(httpContext.getParameter("iDisplayStart"));
		int iDisplayLength=Integer.parseInt(httpContext.getParameter("iDisplayLength"));
		
		JQueryDataTablePage<T> page=new JQueryDataTablePage<T>();
		page.setsEcho(sEcho);
//		page.setPageSize(iDisplayLength);
		int pageNum=iDisplayStart/iDisplayLength;
//		page.setPageNumber(pageNum+1);
//		page.setColumnDirection(httpContext.getParameter("sortColumn")+" "
//				+httpContext.getParameter("sortType"));
		return page;
		
	}
	
}
