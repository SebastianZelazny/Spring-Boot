package Sebastian.demo.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static boolean checkEmailOrPassword(String pattern,String pstr) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pstr);
		return m.matches();
		
	}
}
