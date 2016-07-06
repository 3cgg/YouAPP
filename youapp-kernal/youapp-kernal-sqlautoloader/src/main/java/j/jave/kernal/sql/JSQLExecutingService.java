package j.jave.kernal.sql;

import j.jave.kernal.jave.service.JService;

import java.util.Map;

public interface JSQLExecutingService extends JService{
	
	public void execute(String sql,Map<String, Object> params);
	
	public void execute(String sql);
	
}
