package weibo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import util.Pair;
import weibo4j.WeiboException;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * oMid
 * rtTime|#|rtMid|#|rttext|#|source|#|rtuid|#|rtuname|#|otime|#|otext|#|source
 * |#|ouid|#|ouname 格式：mid |#| uid |#| timestamp |#| content |#| omid |#| uid
 * |#| timestamp |#| content
 * 
 * @author xiafan
 * 
 */
public class NormFormat {
	private static final String[] ESCAPE_ARR = new String[] { "\t", "\n", "|#|" };
	private static final String ESCAPE_REG = "(\t|\n|(\n\r)|\r|(\r\n)|\\|#\\|)";
	private static final Pattern espPattern = Pattern.compile(ESCAPE_REG);
	public final static String SEPERATOR = "\t";
	public final static String SEP_REGRX = "\t";
	public static final SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");

	/**
	 * simple form中保留原微博的id和mid,不保留他们的详细信息
	 * 
	 * @annotation 还在用
	 * 
	 * @param status
	 * @return
	 */
	public static String transToSimpleNorm(Status status) {
		if (status.getUser() == null || status.getCreatedAt() == null)
			return "";

		StringBuffer buf = new StringBuffer();

		Status rt = status.getRetweetedStatus();
		if (rt == null) {
			buf.append(status.getIdstr());
			buf.append(SEPERATOR);
			buf.append(status.getMid());
			buf.append(SEPERATOR);
		} else {
			buf.append(rt.getIdstr());
			buf.append(SEPERATOR);
			buf.append(rt.getMid());
			buf.append(SEPERATOR);
		}

		buf.append(status.getCreatedAt().getTime());
		buf.append(SEPERATOR);
		buf.append(status.getIdstr());
		buf.append(SEPERATOR);
		buf.append(status.getMid());
		buf.append(SEPERATOR);
		buf.append(escape(status.getText()));
		buf.append(SEPERATOR);
		buf.append(status.getUser().getId());
		buf.append(SEPERATOR);
		buf.append(escape(status.getUser().getName()));
		buf.append(SEPERATOR);
		buf.append(status.getGeo());
		buf.append(SEPERATOR);
		buf.append(escape(status.getAnnotations()));
		buf.append(SEPERATOR);
		buf.append(status.getCommentsCount());
		buf.append(SEPERATOR);
		buf.append(status.getRepostsCount());
		buf.append(SEPERATOR);
		return buf.toString();
	}

	/*
	 * @author xiafan
	 * 
	 * @return a retweet b
	 * 
	 * @throws Exception
	 */
	public static TweetWithID transToFullTweetOfSimple(String line) throws Exception {
		TweetWithID ret = null;
		String[] fields = line.trim().split(SEP_REGRX);

		if (fields.length >= 12) {
			try {
				ret = new TweetWithID(fields);
			} catch (Exception ex) {
				ex.printStackTrace();
				ret = null;
			}
		} else {
			throw new Exception("omid is " + fields[1] + "more fields are obtained " + fields.length);
		}
		return ret;
	}

	/**
	 * oMid
	 * rtTime|#|rtMid|#|rttext|#|source|#|rtuid|#|rtuname|#|otime|#|otext|#|
	 * source|#|ouid|#|ouname 格式：mid |#| uid |#| timestamp |#| content |#| omid
	 * |#| uid |#| timestamp |#| content
	 * 
	 * @author xiafan
	 * @return a retweet b
	 * @throws Exception
	 */
	public static Pair<Long, Tweet> transToTweetOfSimple(String line) throws Exception {
		Pair<Long, Tweet> ret = null;
		String[] fields = line.trim().split(SEP_REGRX);

		if (fields.length >= 5) {
			ret = new Pair<Long, Tweet>();
			ret.setKey(Long.parseLong(fields[1]));
			ret.setValue(new Tweet(fields[4], fields[5], fields[7], fields[6], Long.parseLong(fields[2]), fields[1]));
		} else {
			throw new Exception("omid is " + fields[1] + "more fields are obtained " + fields.length);
		}

		return ret;
	}

	public static String transToNorm(Status status) {
		if (status.getUser() == null || status.getCreatedAt() == null)
			return "";

		StringBuffer buf = new StringBuffer();

		Status rt = status.getRetweetedStatus();
		if (rt == null || rt.getUser() == null || rt.getCreatedAt() == null)
			return "";

		buf.append(rt.getIdstr());
		buf.append(" ");

		buf.append(status.getCreatedAt().getTime());
		buf.append(SEPERATOR);
		buf.append(status.getIdstr());
		buf.append(SEPERATOR);
		buf.append(escape(status.getText()));
		buf.append(SEPERATOR);
		buf.append(status.getSource());
		buf.append(SEPERATOR);
		buf.append(status.getUser().getId());
		buf.append(SEPERATOR);
		buf.append(escape(status.getUser().getName()));
		buf.append(SEPERATOR);

		buf.append(rt.getCreatedAt().getTime());
		buf.append(SEPERATOR);
		buf.append(escape(rt.getText()));
		buf.append(SEPERATOR);
		buf.append(rt.getSource());
		buf.append(SEPERATOR);
		buf.append(rt.getUser().getId());
		buf.append(SEPERATOR);
		buf.append(escape(rt.getUser().getName()));
		buf.append(SEPERATOR);

		return buf.toString();
	}

	/**
	 * oMid
	 * rtTime|#|rtMid|#|rttext|#|source|#|rtuid|#|rtuname|#|otime|#|otext|#|
	 * source|#|ouid|#|ouname 格式：mid |#| uid |#| timestamp |#| content |#| omid
	 * |#| uid |#| timestamp |#| content
	 * 
	 * @author xiafan
	 * @return a retweet b
	 * @throws Exception
	 */
	public static Pair<Tweet, Tweet> transToTweets(String line) throws Exception {
		Pair<Tweet, Tweet> ret = null;
		int idx = line.indexOf('\t');
		if (idx < 0)
			idx = line.indexOf(' ');
		if (idx < 0) {
			throw new Exception("cann't find main delimiter");
		} else {
			String oMid = line.substring(0, idx);
			String[] fields = line.substring(idx, line.length()).trim().split(SEP_REGRX);

			if (fields.length >= 11) {
				ret = new Pair<Tweet, Tweet>();

				ret.setValue(new Tweet(oMid, fields[7], fields[10], fields[9], Long.parseLong(fields[6]), "-1"));
				ret.setKey(new Tweet(fields[1], fields[2], fields[5], fields[4], Long.parseLong(fields[0]),
						ret.getValue().getMid()));

				/*
				 * ret.arg1 = new Tweet(oMid, fields[7], fields[10], fields[9],
				 * format.parse(fields[6]).getTime(), "-1"); ret.arg0 = new
				 * Tweet(fields[1], fields[2], fields[5], fields[4],
				 * format.parse(fields[0]).getTime(), ret.arg1.getMid());
				 */

			} else {
				throw new Exception("omid is " + oMid + "more fields are obtained " + fields.length);
			}
		}
		return ret;
	}

	public static Tweet transHxmToTweets(String line) {
		Tweet ret = new Tweet();

		String[] fields = line.split("\t");
		if (fields.length != 2)
			return null;

		int idx = fields[0].lastIndexOf(SEPERATOR);
		if (idx < 0)
			return null;

		String curMid = fields[0].substring(idx + SEPERATOR.length());
		ret.setMid(curMid);

		fields = fields[1].split(SEP_REGRX);
		if (fields.length < 9)
			return null;

		try {
			ret.setoMid(fields[1].trim()); // origin tweet mid
			ret.setTs(format.parse(fields[2]).getTime());
			ret.setContent(fields[3]);
			// ret.setRtCount(Integer.parseInt(fields[4]));
			ret.setUid(fields[7]);
			ret.setUname(fields[8]);
		} catch (Exception ex) {
			return null;
		}
		return ret;
	}

	public static Tweet normTwitterToTweet(String line) {
		Tweet ret = new Tweet();
		int idx = line.indexOf(SEPERATOR);
		if (idx < 0)
			return null;

		try {
			Date date = format.parse(line.substring(0, idx));
			ret.setTs(date.getTime());
		} catch (ParseException e) {
			return null;
		}

		int preIdx = idx + SEPERATOR.length();
		idx = line.indexOf(SEPERATOR, preIdx);
		if (idx < 0)
			return ret;
		ret.setUname(line.substring(preIdx, idx));
		preIdx = idx + SEPERATOR.length();
		ret.setContent(line.substring(preIdx));
		return ret;
	}

	public static String escape(String tweet) {
		// for (String escape : ESCAPE_ARR) {
		// tweet = tweet.replace(escape, "");
		// }
		// tweet = tweet.replaceAll(ESCAPE_REG, "");
		tweet = espPattern.matcher(tweet).replaceAll(",");
		tweet = tweet.replaceAll("\n", "");
		return tweet;
	}

	/**
	 * 从微博数据中抽取用户信息
	 * 
	 * @param status
	 * @return
	 */
	public static List<String> extractUser(Status status) {
		List<String> ret = new ArrayList<String>();
		if (status.getUser() != null) {
			ret.add(extractUserWithJSON(status.getUser()));
		}
		if (status.getRetweetedStatus() != null && status.getRetweetedStatus().getUser() != null) {
			ret.add(extractUserWithJSON(status.getRetweetedStatus().getUser()));
		}
		return ret;
	}

	public static User mergeUserProfile(Iterator<String> lines) {
		User user = null;
		String line = null;
		while (lines.hasNext()) {
			line = lines.next();
			if (user == null) {
				user = parseUser(line);
			} else {
				User curUser = NormFormat.parseUser(line);
				if (curUser != null) {
					if (curUser.getStatusesCount() > user.getStatusesCount()) {
						User tmpUser = curUser;
						curUser = user;
						user = tmpUser;
					}

					try {
						Class<User> uClass = User.class;
						for (Field field : uClass.getDeclaredFields()) {
							if (!Modifier.isStatic(field.getModifiers())) {
								field.setAccessible(true);
								if (field.getType() == String.class) {
									Object value = field.get(user);
									if (value == null || value.toString().isEmpty()) {
										field.set(user, field.get(curUser));
									}
								} else if (field.getType() == Integer.class) {
									long value = field.getLong(user);
									long curValue = field.getLong(curUser);
									if (curValue > value) {
										field.set(user, field.get(curUser));
									}
								} else {
									if (field.get(user) == null) {
										field.set(user, field.get(curUser));
									}
								}
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return user;
	}

	public static final String DEFAULT_TIME = "2000-12-12 00:00:00";

	public static String extractUser(User user) {
		StringBuffer buf = new StringBuffer();

		buf.append(user.getId());
		buf.append(SEPERATOR);
		buf.append(user.getScreenName());
		buf.append(SEPERATOR);
		buf.append(escape(user.getName()));
		buf.append(SEPERATOR);
		buf.append(user.getGender());
		buf.append(SEPERATOR);
		if (user.getCreatedAt() != null) {
			buf.append(format.format(user.getCreatedAt()));
		} else {
			buf.append(DEFAULT_TIME);
		}
		buf.append(SEPERATOR);
		buf.append(user.getFriendsCount());
		buf.append(SEPERATOR);
		buf.append(user.getFollowersCount());
		buf.append(SEPERATOR);
		buf.append(user.getFavouritesCount());
		buf.append(SEPERATOR);
		buf.append(user.getDescription());
		buf.append(SEPERATOR);
		buf.append(user.getVerifiedType());
		buf.append(SEPERATOR);
		buf.append(user.getVerifiedReason());
		buf.append(SEPERATOR);

		buf.append(user.getProfileImageURL());
		buf.append(SEPERATOR);
		buf.append(user.getAvatarLarge());
		buf.append(SEPERATOR);
		buf.append(user.getCity());
		buf.append(SEPERATOR);
		buf.append(user.getProvince());
		buf.append(SEPERATOR);
		buf.append(user.getLocation());

		return buf.toString();
	}

	public static String extractUserWithJSON(User user) {
		return user.toString();
	}

	public static User parseUser(String line) {
		try {
			return new User(new JSONObject(line));
		} catch (WeiboException | weibo4j.model.WeiboException | JSONException e) {
			e.printStackTrace();
		} catch (Exception ex) {

		}
		return null;
	}

	public static String transToNorm2015(Status status) {
		if (status.getUser() == null || status.getCreatedAt() == null)
			return "";

		StringBuffer buf = new StringBuffer();

		buf.append(tranOnlyTweet(status));

		Status rt = status.getRetweetedStatus();
		if (rt != null) {
			buf.append(SEPERATOR);
			buf.append(tranOnlyTweet(rt));
		}

		return buf.toString();
	}

	private static String tranOnlyTweet(Status status) {
		StringBuffer buf = new StringBuffer();

		buf.append(status.getIdstr()); // 0
		buf.append(SEPERATOR);
		buf.append(status.getMid()); // 1
		buf.append(SEPERATOR);
		// 2
		if (status.getCreatedAt() != null)
			buf.append(status.getCreatedAt().getTime());
		else {
			buf.append(0);
		}

		buf.append(SEPERATOR);
		buf.append(escape(status.getText()));// 3
		buf.append(SEPERATOR);
		buf.append(status.getSource());// 4
		buf.append(SEPERATOR);
		// 5,6
		if (status.getUser() != null) {
			buf.append(status.getUser().getId());
			buf.append(SEPERATOR);
			buf.append(escape(status.getUser().getName()));
		} else {
			buf.append(-1);
			buf.append(SEPERATOR);
			buf.append("!!!exception");
		}
		return buf.toString();
	}

	public static Pair<Tweet, Tweet> parseNorm2015(String line) {
		List<String> fields = new ArrayList<String>();
		fields.addAll(Arrays.asList(line.split("\t")));
		Pair<Tweet, Tweet> ret = new Pair<Tweet, Tweet>(null, null);
		Tweet rt = parseTweetFromFields(fields);
		if (rt != null) {
			Tweet otweet = parseTweetFromFields(fields);
			if (otweet != null) {
				rt.setoMid(otweet.getMid());
				otweet.setoMid(otweet.getMid());
			} else {
				rt.setoMid(rt.getMid());
			}
			ret.setKey(rt);
			ret.setValue(otweet);
		}
		return ret;
	}

	public static Tweet parseTweetFromFields(List<String> fields) {
		// String mid, String content, String uname, String uid, long ts,String
		// oMid
		Tweet t = null;
		try {
			if (fields.size() >= 7) {
				t = new Tweet(fields.get(1), fields.get(3), fields.get(6), fields.get(5), Long.parseLong(fields.get(2)),
						"-1");
				fields.subList(0, 7).clear();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(fields.toString());
			t = null;
		}
		return t;
	}

	/**
	 * 测试本类的转换功能能够正确的进行转换和解析
	 * 
	 * @param args
	 * @throws WeiboException
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		/*
		 * String[] tests = new String[] {
		 * "11·22青岛输油管道爆炸事件|#|3350615938049307	<a herf=\"http://weibo.com/\" rel=\"nofollow\">新浪微博</a>|#|3350615728886679|#|2011-08-26 12:22:34|#|哪个手机?|#|1|#|0|#||#|1840399283|#|醉梦拾忆|#|贵州 贵阳"
		 * ,
		 * "11·22青岛输油管道爆炸事件|#|3487364930818554	<a herf=\"http://weibo.com/\" rel=\"nofollow\">新浪微博</a>|#|3487048080458977|#|2012-09-06 20:54:14|#|//@慕容雪村: //@叶匡政: 最后的疯狂！//@记录者陈宝成: 【山东九月不靖】9月1日7至8时，青岛平度城关金沟子复员军人陈亮珂家遭血洗；1日19时至20时，平度刘芳眼镜城三店面遭横扫，此前该店老板因遭威胁而远走海外；3日8时，威海荣成滕家镇政府遭爆炸。不要再雇佣痞子治理民众。|#|0|#|0|#||#|1949548360|#|喜山风|#|陕西"
		 * };
		 * 
		 * for (String test : tests) {
		 * System.out.println(NormFormat.transHxmToTweets(test)); } System.out
		 * .println(NormFormat .transToTweets(
		 * "3408026125539633 1328025600000|#|3408048745579062|#|转发微博|#|<a herf=\"http://weibo.com\" rel=\"nofollow\">新浪微博</a>|#|1913727755|#|和木头一起|#|1328020209000|#| 明天起，做一个幸福的人，读书，旅行，努力工作，关心身体和心情，我没有2012的船票，但我有个美好的梦想，面朝新的一年，花开不怠。|#|<a herf=\"http://weibo.pp.cc/time/\" rel=\"nofollow\">皮皮时光机</a>|#|1644948230|#|法制晚报|#|"
		 * ));
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("/Users/xiafan/Documents/dataset/microblog/sample.txt"), "utf-8"));

		DataOutputStream dos = new DataOutputStream(new FileOutputStream("./data/sample_norm.txt"));
		String line = null;
		int count = 0;
		while (null != (line = reader.readLine()) && count++ < 10) {
			Status status = new Status(escape(line));
			// if (status.getRetweetedStatus() != null) {
			String norm = NormFormat.transToSimpleNorm(status);
			for (String field : norm.split(" "))
				System.out.print(field + " -> ");
			System.out.println();
			// System.out.println(norm.substring(0, norm.indexOf(" ")));
			// System.out.println(norm);
			// System.out.println("data:" + line);
			System.out.println("transformed");
			System.out.println(NormFormat.transToTweetOfSimple(norm));
			dos.write(norm.getBytes());
			dos.write("\n".getBytes());
			// }
		}
		dos.close();
		reader.close();
		StringBuffer buf = new StringBuffer();
		buf.append("asdf,\ttab\n,newline|#|,escape");
		String test = buf.toString();
		System.out.println(NormFormat.escape(test));

	}
}