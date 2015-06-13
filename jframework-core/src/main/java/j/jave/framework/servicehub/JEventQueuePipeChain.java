package j.jave.framework.servicehub;

import j.jave.framework.exception.JInitializationException;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.logging.JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * the event queue chain thats link different event queue pipe, the default order is 
 * <pre>
 * {@link JEventQueueIN} -> {@link JEventQueueProcessing} -> {@link JEventQueueOUT} -> ...( custom event queue )... -> {@link JEventQueueEnd}
 * </pre>
 * @author J
 *@see JEventQueueIN
 *@see JEventQueueProcessing
 *@see JEventQueueOUT
 *@see JEventQueueEnd
 */
public class JEventQueuePipeChain {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());

	private final static List<JEventQueuePipe> eventQueuePipes=new ArrayList<JEventQueuePipe>(6);
	
	private JEventQueueOUT eventQueueOUT=null;
	
	public JEventQueuePipeChain(){
		int order=-1; 
		register(JEventQueueIN.class, ++order);
		register(JEventQueueProcessing.class, ++order);
		eventQueueOUT=(JEventQueueOUT) register(JEventQueueOUT.class, ++order);
		if(LOGGER.isDebugEnabled()){
			register(JEventQueueResultLogger.class, ++order);
		}
		register(JEventQueueEnd.class, ++order);
	}
	
	
	private JEventQueuePipe register(Class<? extends JEventQueuePipe> clazz,int order){
		try {
			JEventQueuePipe eventQueuePipe= clazz.newInstance();
			eventQueuePipe.setOrder(order);
			eventQueuePipe.setEventQueuePipeChain(this);
			eventQueuePipes.add(eventQueuePipe);
			return eventQueuePipe;
		} catch (Exception e) {
			throw new JInitializationException(e);
		} 
	}
	
	
	static class JEventQueueEnd extends JEventQueuePipe{
		@Override
		protected void addEventExecution(JEventExecution eventExecution) {
			LOGGER.debug("the event is processed completely,then drop it.");
		}
	}
	
	final JEventQueuePipe next(JEventQueuePipe current){
		
		if(current==null){
			return eventQueuePipes.get(0);
		}
		
		int index=(1+current.getOrder());
		if(index<eventQueuePipes.size()){
			return eventQueuePipes.get(index);
		}
		return null;
	}
	
	void addAPPEvent(JAPPEvent<? > appEvent){
		next(null).addAPPEvent(appEvent);
	}
	
	Object getEventResult(String eventUnique) throws JEventExecutionException{
		return eventQueueOUT.getEventResult(eventUnique);
	}
	
	
}
