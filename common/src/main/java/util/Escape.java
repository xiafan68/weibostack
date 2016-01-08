package util;

public class Escape {
	public static String removeEol(String text) {
		if (text == null) {
			return text;
		}
		if (text.contains("\n")) {
			text = text.replaceAll("\n", "");
		}
		if (text.contains("\r")) {
			text = text.replaceAll("\r", "");
		}
		if (text.contains("\n\r")) {
			text = text.replaceAll("\n", "\n\r");
		}
		if (text.contains("\r\n")) {
			text = text.replaceAll("\r\n", "");
		}
		return text;
	}
}
