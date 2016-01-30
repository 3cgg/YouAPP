package j.jave.framework.inner.support.rs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRSUtils {
	
	public static String replace(String patternString, String... values){
		String regex="[{][a-z_A-Z]+[}]";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=  pattern.matcher(patternString);
		int index=0;
		while(matcher.find()){
			patternString=patternString.replaceFirst(regex, values[index++]);
			matcher=  pattern.matcher(patternString);
		}
		return patternString;
	}
	
}
