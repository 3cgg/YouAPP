package j.jave.kernal.eventdriven.servicehub;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class JDefaultAsyncEventResultRepoService extends JServiceFactorySupport<JDefaultAsyncEventResultRepoService>
	implements JAsyncEventResultRepoService {

	/**
	 * KEY: UNIQUE VALUE. it's the value of property <code>unique</code> of {@code JAPPEvent}
	 * <p>VALUE:  the event response that means the event is done completely.
	 * <p>about the collection we can consider to store all data in the file system whose active time after executed by thread is long, 
	 * void the OutOfMemory.. 
	 */
	private final ConcurrentHashMap<String, JEventExecution> waitForGets=new ConcurrentHashMap<String, JEventExecution>(256);

	private final ReentrantLock lock=new ReentrantLock();
	
	private static final JDefaultAsyncEventResultRepoService EVENT_RESULT_REPO=new JDefaultAsyncEventResultRepoService();
	
	/**
	 * get expected result, if the event is still not complete, an exception of <code>JEventExecutionException.EVENT_NOT_COMPLETE</code> thrown.
	 * @param eventUnique
	 * @return
	 * @throws JEventExecutionException
	 */
	public Object getEventResult(String eventUnique) throws JEventExecutionException{
		try {
			lock.lockInterruptibly();
		} catch (InterruptedException e) {
			throw new JEventExecutionException(e);
		}
		try{
			if(waitForGets.containsKey(eventUnique)){
				JEventExecution eventExecution= waitForGets.get(eventUnique);
				waitForGets.remove(eventUnique);
				return eventExecution.getResult();
			}
			throw new JEventExecutionException(JEventExecutionException.EVENT_NOT_COMPLETE,
					"event is still not executed completely! please wait.");
		}finally{
			lock.unlock();
		}
	}
	
	@Override
	public JDefaultAsyncEventResultRepoService getService() {
		return EVENT_RESULT_REPO;
	}

	@Override
	public String getName() {
		return "default-hash-map storage.";
	}
	
	public void putEventResult(JEventExecution eventExecution){
		waitForGets.put(eventExecution.getEvent().getUnique(), eventExecution);
	}
	
}
