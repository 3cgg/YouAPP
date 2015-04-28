package j.jave.framework.components.login.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.UserTrackerMapper;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.mybatis.JMapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userTrackerService.transation")
public class UserTrackerServiceImpl extends ServiceSupport<UserTracker> implements UserTrackerService {

	@Autowired
	private UserTrackerMapper userTrackerMapper;
	
	@Override
	public List<UserTracker> getUserTrackerByName(String userName) {
		return userTrackerMapper.getUserTrackerByName(userName);
	}

	@Override
	public void saveUserTracker(ServiceContext context, UserTracker userTracker)
			throws ServiceException {
		userTracker.setLoginTime(new Timestamp(new Date().getTime()));
		saveOnly(context, userTracker);
	}

	@Override
	public void updateUserTracker(ServiceContext context,
			UserTracker userTracker) throws ServiceException {
		updateOnly(context, userTracker);
	}
	
	@Override
	protected JMapper<UserTracker> getMapper() {
		return userTrackerMapper;
	}

}
