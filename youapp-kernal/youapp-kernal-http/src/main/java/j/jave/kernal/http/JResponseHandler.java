package j.jave.kernal.http;

public interface JResponseHandler<T> {
	public  T  process(byte[] bytes) throws ProcessException;
	
	@SuppressWarnings("serial")
	static class ProcessException extends RuntimeException{
		public ProcessException(Exception e){
			super(e);
		}
	}
}
