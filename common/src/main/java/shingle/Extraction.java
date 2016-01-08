package shingle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Extraction {
	private static final Pattern NAME = Pattern.compile("(//[ ]*)?@[ ]*((?:[^\\s:@\\pP][-_]{0,3})+)[ ]*[:：]?");

	public static List<String> extractMension(String line) {
		List<String> ret = new ArrayList<String>();
		Matcher matcher = NAME.matcher(line);

		while (matcher.find()) {
			if (matcher.group(1) == null || !matcher.group(1).contains("//")) {
				ret.add(matcher.group(2));
			}
		}
		return ret;
	}

	@Test
	public void test() {
		System.out.println(extractMension(""));
		System.out.println(extractMension("//@sdfsdf"));
		System.out.println(extractMension("//@sdfsdf:"));
		System.out.println(extractMension("@sdfsdf:"));
		System.out.println(extractMension("//@sdfsdf:@sdfs"));
		System.out.println(extractMension("//@sdfsdf: @sdfs "));
		System.out.println(extractMension("//@sdfsdf: @sdfs asdfsd"));
		System.out.println(extractMension("//@sdfsdf: @sdfs asdfsd@sadfs"));
	}
}
