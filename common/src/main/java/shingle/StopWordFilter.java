package shingle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

public class StopWordFilter {
	HashSet<String> stopWords = new HashSet<String>();
	boolean loaded = true;

	public StopWordFilter() {

	}

	// private Pattern pattern =
	// Pattern.compile("^\\d+([^\\d]+\\d+)*[^届年月日]?$");
	private Pattern[] patterns = new Pattern[] {
			Pattern.compile("^\\d+([^\\d]+\\d+)*[^届年月日]?$"),
			Pattern.compile("^\\d+$"), // all digits
			Pattern.compile("^[\\pP !@#%&_=><+*~`$?]+$") };

	public void load(BufferedReader reader) {
		String line = null;
		try {
			while (null != (line = reader.readLine())) {
				stopWords.add(line.trim());
			}

		} catch (IOException e) {
			loaded = false;
		}
	}

	public boolean isLoaded() {
		return loaded;
	}

	public boolean isStopWord(String word) {
		if (word.length() <= 1)
			return true;

		if (stopWords.contains(word)) {
			return true;
		}

		boolean ret = false;
		for (Pattern pattern : patterns) {
			if (pattern.matcher(word).matches()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static void main(String[] args) throws IOException {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// new FileInputStream(
		// "/home/xiafan/Documents/dataset/stopword.txt")));
		StopWordFilter filter = new StopWordFilter();
		// filter.load(reader);
		System.out.println(filter.isStopWord("哈哈"));
		System.out.println(filter.isStopWord("我"));
		System.out.println(filter.isStopWord("微博"));

		String[] samples = new String[] { "123.123米", "123", "0.123米",
				"123.123", "0-123", "0001", "0000", "0.123米", "0,1995",
				"0,6666", "0---99rmb", "0.484,2008", "0.5x0.5", "0.5x0.5",
				"0.61+", "你好", "sdfsdf", "sdf,sdf", "sdf-sdf", "00--6", "01.",
				"0九", "0046393qwer", "020-84583977", "01年", "01届", "01日",
				"01月", "1,711.60", "0.0" };
		for (String data : samples) {
			if (filter.isStopWord(data)) {
				System.out.println(data + " matched");
			} else {
				System.err.println(data + " not matched");
			}
		}
		String[] tweets = new String[] { "asdf//@哈哈 : sdfsdf",
				"asdf// @哈哈 : sdfsdf", "asdf//@哈哈: sdfsdf",
				"asdf//@哈哈 : sdfsdf" };
		Pattern pattern = Pattern.compile("(//[ ]*)?@[^ :]+[ ]*[:]*");
		for (String tweet : tweets)
			System.out.println(pattern.matcher(tweet).replaceAll(""));
		pattern = Pattern.compile("^[\\pP !@#%&_=><+*~`$?]+$");
		System.out.println(pattern.matcher("? ？!@#&+*~`$?%__><>").matches());

	}
}
