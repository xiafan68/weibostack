package weibo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;

import io.DirLineReader;
import weibo.rttree.RTTreeConstructor;
import weibo4j.model.Status;

public class Tweet implements UncertainObject, Comparable<Tweet> {
    public static final String SEPERATOR = "|#|";
    public static final String SEP_REG = "\\|#\\|";
    public static long MID = -1;
    public static long UID = -1;

    protected String mid = "-1";
    protected String oMid = "-1";
    protected String content = "";
    protected String uname = "";
    protected String uid = "";
    protected long ts = -1;

    @uncheck
    protected boolean certain = true;

    public Tweet() {
        certain = false;
    }

    public Tweet(Status status) {
        parse(status);
    }

    public Tweet(String mid, String content, String uname, String uid, long ts,
            String oMid) {
        super();
        this.mid = mid;
        this.content = content;
        this.uname = uname;
        this.uid = uid;
        this.ts = ts;
        this.oMid = oMid;
        if (mid.startsWith("-") || ts <= 0)
            certain = false;
    }

    public String getoMid() {
        return oMid;
    }

    public void setoMid(String oMid) {
        this.oMid = oMid;
    }

    public String getMid() {
        return mid;
    }

    public String getContent() {
        return content;
    }

    public String getUname() {
        return uname;
    }

    public String getUid() {
        return uid;
    }

    public long getTs() {
        return ts;
    }

    public static String getSeperator() {
        return SEPERATOR;
    }

    public void setMid(String mid) {
        if (mid.startsWith("-"))
            certain = false;
        else
            certain = true;
        this.mid = mid;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

    public void setUname(String uname) {
        this.uname = uname.trim();
    }

    public void setUid(String uid) {
        if (uid.startsWith("-"))
            certain = false;
        else
            certain = true;
        this.uid = uid;
    }

    public void setTs(long ts) {
        if (ts <= 0)
            certain = false;
        else
            certain = true;
        this.ts = ts;
    }

    @Override
    public String toString() {
        return mid + SEPERATOR + NormFormat.escape(content) + SEPERATOR
                + NormFormat.escape(uname) + SEPERATOR + uid + SEPERATOR + ts
                + SEPERATOR + oMid + SEPERATOR + certain;
    }

    public void parse(String text) {
        text = text.trim();
        String[] fields = text.split(SEP_REG);
        if (fields.length >= 6) {
            this.mid = fields[0];
            this.content = fields[1];
            this.uname = fields[2];
            this.uid = fields[3];
            this.ts = Long.parseLong(fields[4]);
            this.oMid = fields[5];
            this.certain = true;
        }
    }

    public void parse(Status status) {
        this.mid = status.getId();
        this.content = status.getText();
        this.uname = status.getUser().getName();
        this.uid = status.getUser().getId();
        this.ts = status.getCreatedAt().getTime();
        if (status.getRetweetedStatus() != null) {
            this.oMid = status.getRetweetedStatus().getMid();
        }
        this.certain = true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    /**
     * 返回当前微博转发的微博，如果没有则返回空
     *
     * @return
     */
    public Tweet computeRetweet() {
        // parse The content of The tweet
        Matcher matcher = RTTreeConstructor.TWITTER_RT_PATTERN.matcher(content);
        String lastName = null;
        int cIdx = -1;
        while (matcher.find()) {
            lastName = matcher.group(2);
            cIdx = matcher.end();
        }

        Tweet ret = null;
        if (lastName != null) {
            ret = new Tweet();
            ret.setUname(lastName);
            ret.setContent(content.substring(cIdx));
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        Tweet oT = (Tweet) o;
        // if (certain && oT.certain)
        // return mid.equals(oT.getMid());
        if (content.equals(oT.content) && uname.equals(oT.uname)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws ParseException, IOException {
        Tweet a = new Tweet("1", "test", "uname", "uid", 1, "-1");
        Tweet b = new Tweet("2", "test", "uname", "uid", 2, "-1");
        Tweet c = new Tweet("2", "test", "uname", "uid", 2, "-1");
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(a);
        tweets.add(c);
        tweets.add(b);

        Collections.sort(tweets, new TimeComparator());
        System.out.println(tweets);
        System.out.println("false:" + a.equals(b));
        c = new Tweet("", "test", "uname", "uid", 1, "-1");
        System.out.println("true:" + a.equals(c));

        HashSet<Tweet> set = new HashSet<Tweet>();
        set.add(a);
        set.add(b);
        System.out.println("true:" + set.contains(a));
        System.out.println("true:" + set.contains(b));
        System.out.println("true:" + set.contains(c));
        Tweet e = new Tweet("", "test1", "uname", "uid", 1, "-1");
        System.out.println("false:" + set.contains(e));

        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh:mm:ss");
        format.parse("2011_01_10_00:00:00");

        DirLineReader reader = new DirLineReader(
                "/Users/xiafan/Documents/dataset/expr/twitter/orgin/part0.txt");
        String line = null;
        while (null != (line = reader.readLine())) {
            Tweet t = NormFormat.normTwitterToTweet(line);
            System.out.println(t);
            System.out.println(t.computeRetweet());
            System.out.println("******************");
        }
    }

    @Override
    public int compareTo(Tweet arg0) {
        long ret = ts - arg0.ts;
        if (ret == 0) {
            ret = content.compareTo(arg0.content);
            if (ret == 0) {
                ret = uname.compareTo(arg0.uname);
            }
        }
        if (ret > 0)
            return 1;
        else if (ret < 0)
            return -1;
        return 0;
    }

    @Override
    public boolean certain() {
        return certain;
    }

    public void cleanup(TweetWithID newTweet) {
        Class tclass = Tweet.class;
        Field[] fields = tclass.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (field.getAnnotation(uncheck.class) == null) {
                        if (field.getType() == String.class) {
                            field.setAccessible(true);
                            String oval = field.get(this).toString();
                            if (field.getName() == "content") {
                                if (oval.contains("该微博已经被删除") || oval
                                        .contains("该微博已经被原作者删除")) {
                                    field.set(this, field.get(newTweet));
                                }
                            } else if (oval.equals("") || oval.equals("-1")
                                    || oval.equals("null")) {
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

    @Override
    public void reconcile(UncertainObject other) {
        if (certain())
            return;
        if (other.certain()) {
            Tweet ot = (Tweet) other;
            this.setTs(ot.getTs());
            this.setMid(ot.getMid());
            this.setUid(ot.getUid());
        }
    }

    public static class TimeComparator implements Comparator<Tweet> {

        @Override
        public int compare(Tweet arg0, Tweet arg1) {
            long ret = arg0.getTs() - arg1.getTs();
            if (ret > 0)
                return 1;
            else if (ret < 0)
                return -1;
            else
                return 0;
        }

    }
}
