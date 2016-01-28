package shingle;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shingle.preprocess.PreProcRules.DotsFinder;
import shingle.preprocess.PreProcRules.EmailFinder;
import shingle.preprocess.PreProcRules.PubNameFinder;
import shingle.preprocess.PreProcRules.TopicFinder;
import shingle.preprocess.PreProcRules.UNameFinder;
import shingle.preprocess.PreProcRules.URLFinder;
import shingle.preprocess.PreProcess;

/**
 * 增加了一些规则时期能够识别某些微博相关的语法
 * 
 * @author xiafan
 *
 */
public abstract class MicroblogShingle implements ITextShingle {

	public boolean preserveUName = false;
	List<PreProcess> rules = new ArrayList<PreProcess>();
	StopWordFilter filter = null;
	PubNameFinder pubNameFinder = new PubNameFinder();

	public MicroblogShingle(StopWordFilter filter) {
		this.filter = filter;
		rules.add(new EmailFinder());
		rules.add(new URLFinder());
		rules.add(new UNameFinder());
		rules.add(new TopicFinder());
		rules.add(new DotsFinder());
	}

	private List<String> preProcess(String text, boolean retain) {
		HashSet<String> words = new HashSet<String>();
		for (PreProcess rule : rules) {
			List<String> rtn = rule.preProcess(text, retain);
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

	BreakIterator optSeg = BreakIterator.getWordInstance();

	protected abstract List<String> shingleImpl(String text, boolean isQuery) throws IOException;

	/**
	 * 分词
	 * 
	 * @param text
	 * @param isQuery
	 * @return
	 * @throws IOException
	 */
	public List<String> shingling(String text, boolean isQuery) throws IOException {
		List<String> words = preProcess(text, !isQuery);
		text = words.remove(words.size() - 1).trim();
		if (isQuery) {
			words.addAll(pubNameFinder.preProcess(text, !isQuery));
			text = words.remove(words.size() - 1).trim();
		}
		HashSet<String> ret = new HashSet<String>();
		List<String> newWords = shingleImpl(text, isQuery);

		if (newWords.isEmpty() && !text.isEmpty())
			words.addAll(optSeg(Arrays.asList(text)));
		else if (!newWords.isEmpty()) {
			words.addAll(newWords);
		}

		if (!isQuery) {
			for (String word : words) {
				word = postProcess(word);
				if (word.length() > 0)
					ret.add(word);
			}
		} else {
			ret.addAll(words);
		}
		return new ArrayList<String>(ret);

	}

	private List<String> optSeg(List<String> words) {
		List<String> ret = new ArrayList<String>();
		for (String text : words) {
			if (text.length() < 5 || text.startsWith("@") || text.startsWith("#")) {
				ret.add(text);
				continue;
			}
			optSeg.setText(text);
			int preIdx = 0;
			do {
				int idx = optSeg.next();
				if (idx != BreakIterator.DONE) {
					ret.add(text.substring(preIdx, idx));
				} else {
					ret.add(text.substring(preIdx, text.length()));
					break;
				}
				preIdx = idx;
			} while (true);
		}
		return ret;
	}
}
