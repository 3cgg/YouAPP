package j.jave.kernal.streaming.coordinator;

public interface TaskCallBack {

	SimpleCallBack DEFAULT=new SimpleCallBack() {
		@Override
		public void call(Object object) {
		}
	};
	
	public void call(Task task,SimpleCallBack callBack);
	
}
