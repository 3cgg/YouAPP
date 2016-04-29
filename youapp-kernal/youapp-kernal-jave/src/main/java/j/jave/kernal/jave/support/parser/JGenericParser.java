package j.jave.kernal.jave.support.parser;

public interface JGenericParser<T> extends JParser {

	T parse() throws Exception;
	
}
