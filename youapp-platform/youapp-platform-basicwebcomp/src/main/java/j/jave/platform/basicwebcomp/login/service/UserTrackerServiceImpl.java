package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.model.UserTracker;
import j.jave.platform.basicwebcomp.login.repo.UserTrackerRepo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userTrackerService.transation")
public class UserTrackerServiceImpl extends ServiceSupport<UserTracker> implements UserTrackerService {

	@Autowired
	private UserTrackerRepo<?> userTrackerMapper;
	
	@Override
	public List<UserTracker> getUserTrackerByName(String userName) {
		return userTrackerMapper.getUserTrackerByName(userName);
	}

	@Override
	public void saveUserTracker(ServiceContext context, UserTracker userTracker)
			throws JServiceException {
		userTracker.setLoginTime(new Timestamp(new Date().getTime()));
		saveOnly(context, userTracker);
	}

	@Override
	public void updateUserTracker(ServiceContext context,
			UserTracker userTracker) throws JServiceException {
		updateOnly(context, userTracker);
	}
	
	@Override
	public JIPersist<?, UserTracker> getRepo() {
		return userTrackerMapper;
	}

}
