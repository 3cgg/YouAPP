package j.jave.framework.servicehub;

import j.jave.framework.utils.JCollectionUtils;

import java.util.List;

@SuppressWarnings("serial")
public class JPersitenceEventAsyncCallbackTask extends JPersitenceTask{
	
	public JPersitenceEventAsyncCallbackTask(
			JEventExecution eventExecution) {
		super(eventExecution);
	}

	@Override
	public Object execute() {
		List<JAsyncCallback> asyncCallbacks=eventExecution.getAsyncCallbackChain();
		if(JCollectionUtils.hasInCollect(asyncCallbacks)){
			for(int i=0;i<asyncCallbacks.size();i++){
				eventExecution.setCurrentCallbackIndex(i);
				asyncCallbacks.get(i).callback((Object[]) eventExecution.getResult(),eventExecution);
			}
		}
		return VOID;
	}
	@Override
	public boolean isVoid() {
		return true;
	}
} 
