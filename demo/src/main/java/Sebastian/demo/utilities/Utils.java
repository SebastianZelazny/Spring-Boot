package Sebastian.demo.utilities;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static boolean checkEmailOrPassword(String pattern,String pstr) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pstr);
		return m.matches();
		
	}
	
	public static String randomStringGenerator() {
		String randomString = "";
		
		String signs = "ancdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		Random rng = new Random();
		
		for(int i = 0;  i < 45; i++) {
			int liczba = rng.nextInt(signs.length());
			randomString += signs.substring(liczba,liczba+1);
		}
		System.out.println(randomString);
		
		return randomString;
	}
}
