package j.jave.kernal.streaming.coordinator;

/**
 * 1 ({@link #_TRUE}) is parrallel, otherwise 0 ({@link #_FALSE})
 * @author JIAZJ
 *
 */
public interface IParallel {

	public static final String _TRUE="1";
	
	public static final String _FALSE="0";
	
	
	boolean isParallel();
	
}
