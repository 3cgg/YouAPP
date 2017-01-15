package j.jave.kernal.taskdriven.tkdd.flow;

import j.jave.kernal.taskdriven.tkdd.JNotifyStart;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public class JSimpleBoundary implements JBoundary {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JSimpleBoundary.class);
	
	private JNotifyStart  notifyStart;
	
	private JBoundaryConfig boundaryConfig;
	
	public JSimpleBoundary(JNotifyStart notifyStart) {
		this.notifyStart=notifyStart;
	}
	
	@Override
	public Object start() throws Exception {
		if(onOff(boundaryConfig)){
			return delegate(null);
		}
		return notifyStart.start();
	}

	@Override
	public Object start(JFlowContext flowContext) throws Exception {
		if(onOff(boundaryConfig)){
			return delegate(null);
		}
		return notifyStart.start(flowContext);
	}

	@Override
	public boolean onOff(JBoundaryConfig boundaryConfig ) {
		return boundaryConfig.isActive();
	}

	@Override
	public Object delegate(JFlowContext flowContext) {
		try{
			if(flowContext==null) flowContext=JFlowContext.get();
			return notifyStart.start(flowContext);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			if(boundaryConfig.getExceptionHandler()!=null){
				boundaryConfig.getExceptionHandler().handler(e);
			}
			if(!catchException(boundaryConfig)){
				throw new JTaskExecutionException(e);
			}
		}
		return null;
	}
	
	
	@Override
	public boolean catchException(JBoundaryConfig boundaryConfig ) {
		return boundaryConfig.isCatchException();
	}
	
	

}
