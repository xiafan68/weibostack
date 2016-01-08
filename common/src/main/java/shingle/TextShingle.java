package shingle;

import java.io.IOException;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import shingle.preprocess.PreProcRules.DotsFinder;
import shingle.preprocess.PreProcRules.EmailFinder;
import shingle.preprocess.PreProcRules.PubNameFinder;
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

public class TextShingle {

	IKSegmenter segmenter = new IKSegmenter(new StringReader(""), true);
	public boolean preserveUName = false;
	List<PreProcess> rules = new ArrayList<PreProcess>();
	StopWordFilter filter = null;
	PubNameFinder pubNameFinder = new PubNameFinder();

	public TextShingle(StopWordFilter filter) {
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

	BreakIterator optSeg = BreakIterator.getWordInstance();

	/**
	 * 分词
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public List<String> shingling(String text) throws IOException {
		return shingling(text, false);

	}

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

		Lexeme token = null;
		segmenter.reset(new StringReader(text));
		boolean segged = false;
		while (null != (token = segmenter.next())) {
			words.add(token.getLexemeText());
			segged = true;
		}
		if (!segged && !text.isEmpty())
			words.addAll(optSeg(Arrays.asList(text)));
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

	// Pattern.compile("\\pP")

	public static void main(String[] args) throws IOException {
		TextShingle shingle = new TextShingle(null);
		shingle.preserveUName = true;

		String[] tweets = new String[] { "@SDFASD呢", "@KIN玩TRANSFORMERS", "@下凡xiafan68@gmail.com好吧", "接删掉第二个http之前的全部",
				"@北京青年报http://t.cn/RvGSDlv", "#topic1#topic2#topic3#", "#我是bugbugbugbugbugbugbugbugbugbugbugbug#",
				"凡是情侣关注@高丽雅韩国料理，并拍摄情侣合影@高丽雅韩国料理，每周二、周四就有机会#免费#获得超值美味双享！让这个情人节在你和你的另一半的记忆里留下独特味道！超赞！今天奉上：#鲜茄石头饭+辣泡菜炒年糕+真菌石头饭+烧烤组",
				"3d##titanic", "3位及以上好友转发微博，均有机会赢取#吴克羣&BY2合肥歌友会#门票1张！了解更多资讯及抢票方式，登录",
				"3个好友，三天后送出#韩庚#私人赠送的珍藏版专辑三份。在路上，有我们[奥特曼]0771?5881003#我帮帮1003",
				"3G门户NOW直播#午爱美丽##教你增重#现在的\"增肥药\"实际是健脾开胃药,这些药虽能增进人的食欲,促进消化液的分泌,加强胃肠活动,使食物中的营养易于吸收,但这并不能有效增加体重。有的\"增肥药\"宣传一二个月增加六七公斤是根本不符合科学的,是一种虚假的误导宣",

				"iphone4s......", "iphone##iphone4s#", "iphone4s..@",
				"@ACTOYS模玩网@变形金刚中国联盟@变形金刚大本营@BOTCON俱乐部@KIN玩TRANSFORMERS@自护@Skylynx@Freccia_横炮@Star星星叫@HIGASHILIN",
				"@81890官方微博?提问", "@3位好友、转发+评论活动微博，每天抽取1名幸运童鞋，获味域空间50元代金券两张！童鞋们，赶快行动吧~【9月4日晚上21",
				"@36度7_明星-听诊会【习惯造成疼痛】1.压抑愤怒情绪。压抑愤怒情绪的人脊柱部位肌肉紧张。2.电话使用不当。经常把手机夹在肩膀和耳朵之间致颈部和肩膀疼痛。3.烦恼念念不忘。4.不够重视睡眠。睡眠时分泌的生长激素可缓解疼痛。5.上班坐姿欠佳。6.身边朋友过少。孤独易陷入抑郁，抑郁情绪会致生理疼",
				"@1个你最想同行的大吃货即可有机会获得试吃名额。12月14日中午公布10组名额，每组送出200现金券[花心]足够2~3个人的！转起！同期12月份早午茶、饭市满100送50~",
				"@160岁的老老宓小婥@王野全球后援会@auntcoconut@美味芬芳；；；；//@为抚油奋斗的晶晶",
				"@13渡：中国人真正登上了钓鱼岛，是香港同胞，而他们可贪污几十亿，玩群劈，开大会，唱红歌，欺百姓，从没有人站出来告诉全世界，＂钓鱼岛是我的＂?我泪流满面?//@微天下",
				"@3是个神奇的数字　@刁璐　@wasabi鞏　@阿金bk　见证奇迹的时刻来了～姐们儿还是很低调的嘛", "@13579神仙老虎狗（泪流满面地[泪]）硼飞，不是说好了要做彼此的天使么",
				"@080瑾年：感情受伤了。@曲曲动人_曲曲动心：喵喵创业失败了。@想的不少：估计是有心上喵了，在思念它。@mjwww728：应该是怀孕了。@吴向宏：解铃还需系铃喵…(原评论中亮点更多，欢迎围观)//@ZUI西",
				"@047宁波私家车音乐台//@中国机场阳光服务",
				"@1047私家车音乐台，那个让我抓狂的广告有放哦！[抓狂][抓狂][抓狂]＂买锐志到宁兴丰田来＂N遍各个方言版本。难听死了，谁这么俗想出来这个广告。刚听时我尽听成＂买锤子到宁兴丰田来＂！[偷笑]我以为去砸车呢!@燕儿sN_yL",

				"@ACTOYS模玩网@变形金刚中国联盟@变形金刚大本营@BOTCON俱乐部@KIN玩TRANSFORMERS@自护@Skylynx@Freccia_横炮@Star星星叫@HIGASHILIN",
				"#情人节#礼物：【Kenzo高田贤三水之恋女士香水50ml】KENZO 重新创做 L'EAUPAR KENZO 焕然一新的设计，唯有清新的香氛依旧。清新而感性的水，他泛起的淡淡涟漪化成了波浪状的瓶身和包装盒子，以及全新的视觉影像。市场价510元，身边价：185元。http://t.cn/z0DVxSX",
				"#情人节礼物# 喜欢你就@ TA！",
				"回复@思同的围脖: 哎呀，那怎么好意思呢?[害羞][害羞]不过我还是莫莫的笑纳吧，不然多不尊重你的哈哈?//@思同的围脖:回复@FM99子杰:你的绿泥我包了 哈哈哈哈",
				"//@ab:Ψήφο στην κομμουνιστική αριστερά που πρέπει νααλλάξει http://bit.ly/Cyq6h",
				"Like any researcher, I start my task with a search to see what others have done. Here is what I found:" };

		for (String tweet : tweets) {
			System.out.println(String.format("%s \n --> %s\n -->", tweet, shingle.shingling(tweet).toString()));
		}
		shingle.shingling("//@ab:Ψήφο στην κομμουνιστική αριστερά που πρέπει νααλλάξει http://bit.ly/Cyq6h");

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
