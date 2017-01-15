package me.bunny.kernel._c.support.parser;

public interface JSimpleDataParser<T> extends JParser {

	T parse(Object object) throws Exception;
	
}
