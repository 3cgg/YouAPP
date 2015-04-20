package j.jave.framework.components.weight.action;

import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.views.chart.SimpleLineChart;
import j.jave.framework.components.web.jsp.JSPAction;
import j.jave.framework.components.weight.model.Weight;
import j.jave.framework.components.weight.model.WeightSearchCriteria;
import j.jave.framework.components.weight.service.WeightService;
import j.jave.framework.utils.JUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller(value="weight.weightaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WeightAction extends JSPAction {
	
	private Weight  weight;
	
	private WeightSearchCriteria weightSearchCriteria;
	
	@Autowired
	private WeightService weightService;
	
	@Autowired
	private UserService userService;
	
	
	public String toRecordWeight() throws Exception {
		return "/WEB-INF/jsp/weight/record-weight.jsp";
	}
	
	public String recordWeight() throws Exception {
		weightService.saveWeight(getServiceContext(), weight);
		setSuccessMessage(CREATE_SUCCESS);
		return "/WEB-INF/jsp/weight/record-weight.jsp";
	}
	
	
	public String toViewWeight() throws Exception {
		
		String id=getParameter("id");
		Weight weight= weightService.getWeightById(getServiceContext(),id);
		if(weight!=null){
			setAttribute("weight", weight);
		}
		return "/WEB-INF/jsp/weight/view-weight.jsp"; 
	}
	
	public String toViewAllWeight() throws Exception {
		
		List<Weight> weights=weightService.getWeightByName(getServiceContext(), getSessionUser().getUserName());
		setAttribute("weights", weights);
		return "/WEB-INF/jsp/weight/view-all-weight.jsp";
	}
	
	public String toViewChart() throws Exception {
		List<Weight> weights=weightService.getWeightByName(getServiceContext(), getSessionUser().getUserName());
		List<SimpleLineChart> lineCharts=new ArrayList<SimpleLineChart>();
		if(weights!=null){
			for (Iterator<Weight> iterator = weights.iterator(); iterator.hasNext();) {
				Weight weight =  iterator.next();
				SimpleLineChart lineChart=new SimpleLineChart();
				lineChart.setXvalue(JUtils.formatWithSeconds(weight.getRecordTime()));
				lineChart.setYvalue(weight.getWeight());
				lineCharts.add(lineChart);
			}
		}
		setAttribute("lineCharts", lineCharts);
		return "/WEB-INF/jsp/weight/view-chart-weight.jsp";
	}
	
	public String getWeightsWithsCondition(){
		
		Weight weight=new Weight();
		String latestMonth=getParameter("lastetMonth");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1*Integer.valueOf(JUtils.isNullOrEmpty(latestMonth)?"1":latestMonth));
		weight.setRecordTime(new Timestamp(calendar.getTime().getTime()));
		weight.setUserName(getSessionUser().getUserName());
		List<Weight> weights=weightService.getWeightsByPage(getServiceContext(), weight);
		setAttribute("weights", weights);
		return "/WEB-INF/jsp/weight/view-all-weight.jsp";
	}
	
	public String deleteWeight(){
		weightService.delete(getServiceContext(), getParameter("id")); 
		setSuccessMessage(DELETE_SUCCESS);
		return getWeightsWithsCondition();
	}
	
	public String toEditWeight(){
		return getWeightsWithsCondition();
	}
	
}
