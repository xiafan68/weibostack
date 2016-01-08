package shingle.preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcRules {
	public static class URLFinder implements PreProcess {
		Pattern pattern = Pattern.compile("http[s]?://[a-z0-9?&+-_/]+");

		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String name = matcher.group().trim();
				matcher.appendReplacement(sb, " ");
				ret.add(name);
			}
			matcher.appendTail(sb);
			ret.add(sb.toString());
			return ret;
		}

	}

	public static class EmailFinder implements PreProcess {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?");

		/**
		 * retain在当前规则中无效
		 */
		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String name = matcher.group().trim();
				matcher.appendReplacement(sb, " ");
				ret.add(name);
			}
			matcher.appendTail(sb);
			ret.add(sb.toString());
			return ret;
		}
	}

	/**
	 * 这个是索引额外会索引的内容，对于每条微博，同时索引一个&uname&的单词,只能在查询的时候使用
	 * 
	 * @author xiafan
	 *
	 */
	public static class PubNameFinder implements PreProcess {
		Pattern pattern = Pattern.compile("&([^&]+)&");

		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String name = matcher.group();
				matcher.appendReplacement(sb, " ");
				ret.add(name);
			}
			matcher.appendTail(sb);
			ret.add(sb.toString());
			return ret;
		}
	}

	public static class UNameFinder implements PreProcess {
		private static final int MAX_UNAME_LENGTH = 20; // 10个字符的名字应该已经够长了，出了奇葩。。
		// 不允许名字中间有标点符号,允许-_，但是这样也会有问题
		Pattern pattern = Pattern.compile("(//[ ]*)?@[ ]*(([^\\s:@\\pP][-_]{0,3})+)[ ]*[:：]?");

		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String name = matcher.group(2).trim();
				if (name.length() > 0 && name.length() < MAX_UNAME_LENGTH) {
					if (!retain)
						matcher.appendReplacement(sb, " ");
					ret.add("@" + name);
				}
			}
			if (!retain) {
				matcher.appendTail(sb);
				ret.add(sb.toString());
			} else {
				ret.add(text);
			}
			return ret;
		}
	}

	/**
	 * topic finder
	 * 
	 * @author xiafan
	 * 
	 */
	public static class TopicFinder implements PreProcess {
		private static final int MAX_TOPIC_LEN = 20;// 暂时只能通过这种方式避免误判
		Pattern pattern = Pattern.compile("#([^#]+)#");

		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			StringBuffer buf = new StringBuffer();
			while (matcher.find()) {
				String topic = matcher.group(1).trim();
				if (topic.length() > 0 && topic.length() < MAX_TOPIC_LEN) {// 应该已经够长了
					ret.add("#" + topic);
					if (!retain)
						matcher.appendReplacement(buf, " ");
				}
			}
			if (!retain) {
				matcher.appendTail(buf);
				ret.add(buf.toString());
			} else {
				ret.add(text);
			}
			return ret;
		}
	}

	/**
	 * 找出....这种语句
	 * 
	 * @author xiafan
	 * 
	 */
	public static class DotsFinder implements PreProcess {
		Pattern pattern = Pattern.compile("[.]{2,}");

		public List<String> preProcess(String text, boolean retain) {
			List<String> ret = new ArrayList<String>();
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				ret.add("$DOTS$");
			}
			ret.add(matcher.replaceAll(" "));
			return ret;
		}
	}
}
