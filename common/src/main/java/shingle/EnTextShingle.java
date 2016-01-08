package shingle;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shingle.preprocess.PreProcRules.DotsFinder;
import shingle.preprocess.PreProcRules.EmailFinder;
import shingle.preprocess.PreProcRules.TopicFinder;
import shingle.preprocess.PreProcRules.UNameFinder;
import shingle.preprocess.PreProcRules.URLFinder;
import shingle.preprocess.PreProcess;

/**
 * 微博特定的分词，这里涉及到一些特定的预处理
 * 
 * @author xiafan
 * 
 */

public class EnTextShingle {

	BreakIterator segmenter = BreakIterator.getWordInstance();
	public boolean preserveUName = false;
	List<PreProcess> rules = new ArrayList<PreProcess>();
	StopWordFilter filter = null;

	public EnTextShingle(StopWordFilter filter) {
		this.filter = filter;
		rules.add(new EmailFinder());
		rules.add(new URLFinder());
		rules.add(new UNameFinder());
		rules.add(new TopicFinder());
		rules.add(new DotsFinder());
	}

	private List<String> preProcess(String text) {
		HashSet<String> words = new HashSet<String>();
		for (PreProcess rule : rules) {
			List<String> rtn = rule.preProcess(text, true);
			text = rtn.remove(rtn.size() - 1);
			words.addAll(rtn);
		}
		List<String> ret = new ArrayList<String>(words);
		ret.add(text);
		return ret;
	}

	Pattern pattern = Pattern.compile("(^([ ]|\\pP)+)|(([ ]|\\pP)+$)");

	/**
	 * 按照一些规则处理
	 * 
	 * @param word
	 * @return
	 */
	private String postProcess(String word) {
		word = word.trim();
		if (word.startsWith("@") || word.startsWith("#"))
			return word;
		// 删除掉尾部的标点符号
		Matcher matcher = pattern.matcher(word);
		if (matcher.find()) {
			word = matcher.replaceAll("");
		}

		if (filter != null && filter.isStopWord(word)) {
			word = "";
		}

		return word;
	}

	/**
	 * 按照一些规则处理
	 * 
	 * @param word
	 * @return
	 */
	private List<String> postProcess(List<String> words) {
		// 删除掉尾部的标点符号
		HashSet<String> ret = new HashSet<String>();
		for (String word : words) {
			word = postProcess(word);
			if (filter != null && !filter.isStopWord(word))
				ret.add(postProcess(word));
		}
		return new ArrayList<String>(ret);
	}

	/**
	 * 分词
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public List<String> shingling(String text) throws IOException {
		//
		List<String> words = preProcess(text);
		text = words.remove(words.size() - 1);
		HashSet<String> ret = new HashSet<String>();

		segmenter.setText(text);
		int preIdx = 0;
		do {
			int idx = segmenter.next();
			if (idx != BreakIterator.DONE) {
				words.add(text.substring(preIdx, idx));
			} else {
				words.add(text.substring(preIdx, text.length()));
				break;
			}
			preIdx = idx;
		} while (true);

		for (String word : words) {
			word = postProcess(word);
			if (word.length() > 0)
				ret.add(word);
		}
		return new ArrayList<String>(ret);
	}

	// Pattern.compile("\\pP")

	public static void main(String[] args) throws IOException {
		EnTextShingle shingle = new EnTextShingle(null);
		shingle.preserveUName = true;

		String[] tweets = new String[] { "鸦片战争:中国输掉的一场金融战争",
				"みんな食べてるな～。食べたくなるな～ RT @yamaguchixcom: チキンタツタなう。 以前食べたよりおいしく感じるのはひさしぶりだからかー。 http://bit.ly/CwFvc",
				"Ψήφο στην κομμουνιστική αριστερά που πρέπει νααλλάξει http://bit.ly/Cyq6h", "united kingdom",
				"Like any researcher, I start my task with a search to see what others have done. Here is what I found:" };
		for (String tweet : tweets) {
			System.out.println(String.format("%s \n --> %s\n -->", tweet, shingle.shingling(tweet).toString()));
		}

		String[] puncTests = new String[] { "@haha:", "@:", "@asdf :", " @ haha:", "@ haha : sdf", "//@ haha : sdf",
				"// @ haha : sdf", "// @ haha : //sdf", "// @ @haha: : //sdf" };
		for (String punc : puncTests) {
			System.out.println(String.format("%s --> %s", punc, shingle.postProcess(punc)));
		}

		String[] dots = new String[] { "ads.dsf", "sdf....", "asdf." };
		DotsFinder dFinder = new DotsFinder();
		System.out.println("DotsFinder");
		for (String dot : dots) {
			System.out.println(String.format("sfinder %s\t%s", dot, dFinder.preProcess(dot, false)));
		}

		System.out.println(shingle.shingling("苹果手机# iphone4 #ss"));
		System.out.println(shingle.shingling("#^&*"));
	}
}
