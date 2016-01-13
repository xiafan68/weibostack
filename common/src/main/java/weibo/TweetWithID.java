package weibo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.sf.json.JSONObject;

public class TweetWithID extends Tweet {
	private String id = "";
	private String oId = "";
	private String geo = "";
	private String annotations = "";
	private long commentCount = 0;
	private long rtCount = 0;

	public TweetWithID(String[] fields) {
		super(fields[4], fields[5], fields[7], fields[6], Long.parseLong(fields[2]), fields[1]);
		init(fields);
	}

	public TweetWithID() {
		// TODO Auto-generated constructor stub
	}

	private void init(String[] fields) {
		oId = fields[0];
		id = fields[3];
		geo = fields[8];
		annotations = fields[9];
		commentCount = Long.parseLong(fields[10]);
		rtCount = Long.parseLong(fields[11]);
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public long getRtCount() {
		return rtCount;
	}

	public void setRtCount(long rtCount) {
		this.rtCount = rtCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public String toString() {
		return JSONObject.fromObject(this).toString().replace("\n", "");
	}

	public static TweetWithID parseFromString(String line) {
		String[] fields = line.split("\t");
		if (fields.length >= 12) {
			try {
				return new TweetWithID(fields);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public void cleanup(TweetWithID newTweet) {
		super.cleanup(newTweet);
		Class tclass = TweetWithID.class;
		Field[] fields = tclass.getDeclaredFields();
		try {
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					if (field.getAnnotation(uncheck.class) == null) {
						if (field.getType() == String.class) {
							field.setAccessible(true);
							String oval = field.get(this).toString();
							if (field.getName() == "content") {
								if (oval.contains("该微博已经被删除") || oval.contains("该微博已经被原作者删除")) {
									field.set(this, field.get(newTweet));
								}
							} else if (oval.equals("") || oval.equals("-1") || oval.equals("null")) {
								field.set(this, field.get(newTweet));
							}
						} else if (field.getType() == Long.class) {
							field.setAccessible(true);
							long oval = field.getLong(this);
							long newVal = field.getLong(newTweet);
							if (oval < newVal) {
								field.set(this, field.get(newTweet));
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TweetWithID test = new TweetWithID();
		test.setContent("该微博已经被原作者删除");
		System.out.println(test.toString());
		TweetWithID newTweet = new TweetWithID();
		test.cleanup(newTweet);
		System.out.println(test.toString());
	}
}
