package j.jave.kernal.jave.support.parser;

public interface JSimpleDataParser<T> extends JParser {

	T parse(Object object) throws Exception;
	
}
