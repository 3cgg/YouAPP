package j.jave.framework.http;

public interface JResponseHandler<T> {
	public  T  process(byte[] bytes) throws ProcessException;
	
	static class ProcessException extends RuntimeException{
		public ProcessException(Exception e){
			super(e);
		}
	}
}
