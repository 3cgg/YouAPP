/**
 * 
 */
package j.jave.framework.components.web.model;

import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.json.JJSONObject;
import j.jave.framework.model.JPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the class is for JQuery DataTable plugin.
 * @author J
 */
public class JQueryDataTablePage extends JPage implements JJSONObject<Map<String, Object>> {

	private static final long serialVersionUID = -8159481727794008226L;

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
		pagination.put(ITOTALRECORDS, getTotalRecordNum());
		pagination.put(SECHO,sEcho); 
		pagination.put(ITOTALDISPLAYRECORDS, getTotalRecordNum());
		pagination.put(AADATA, aaData);
		return pagination;
	}

	@Override
	public void readJSONObject(Map<String, Object> serializableJSONObject) {
		throw new JOperationNotSupportedException(" JQuery DataTable Model not supports converting JSON Object.");
	}
	
	
	
}
