package j.jave.framework.components.weight.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.weight.mapper.WeightMapper;
import j.jave.framework.components.weight.model.Weight;
import j.jave.framework.components.weight.model.WeightSearchCriteria;
import j.jave.framework.model.JPagination;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JStringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="weightService.transation")
public class WeightServiceImpl  extends ServiceSupport<Weight>  implements WeightService {

	@Autowired
	private WeightMapper  weightMapper;
	
	@Override
	protected JMapper<Weight> getMapper() {
		return weightMapper;
	}
	
	@Override
	public void saveWeight(ServiceContext context, Weight weight)
			throws JServiceException {
		if(JStringUtils.isNullOrEmpty(weight.getUserName())){
			// DEFAULT TO LOGIN USER
			weight.setUserName(context.getUser().getUserName());
		}
		if(weight.getRecordTime()==null){
			weight.setRecordTime(new Timestamp(new Date().getTime()));
		}
		saveOnly(context, weight);
	}

	@Override
	public void updateWeight(ServiceContext context, Weight weight)
			throws JServiceException {
		updateOnly(context, weight);
	}

	@Override
	public List<Weight> getWeightByName(ServiceContext context, String userName) {
		return weightMapper.getWeightByName(userName);
	}
	
	@Override
	public List<Weight> getWeightsByPage(ServiceContext context, JPagination pagination) {
		return weightMapper.getWeightsByPage(pagination); 
	}

	@Override
	public Weight getWeightById(ServiceContext context, String id) {
		return getById(context, id);
	}
}
