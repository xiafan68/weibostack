package weibo.rttree;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ds.tree.trie.Node;
import ds.tree.trie.PostOrderTraverse;
import ds.tree.trie.TrieTree;
import util.Histogram;
import util.Pair;
import weibo.NormFormat;
import weibo.Tweet;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class RTTreeConstructor {
    private static final String RT_TAG = "//@";
    public static Pattern RT_PATTERN = Pattern.compile("(//[ ]*@([^:：]+)[:：])");

    public static Pattern TWITTER_RT_PATTERN = Pattern
            .compile("(rt|RT|Rt|Retweet|retweet|//)[^@]*@([^:： ]+)[:： ]");

    public static TrieTree<Tweet> parse(Tweet oT, List<Tweet> tweets) {

        // 按照时间戳进行排序
        Collections.sort(tweets, new Tweet.TimeComparator());
        TrieTree<Tweet> tree = new TrieTree<Tweet>(TNodeFactory.instance);
        tree.add(oT);

        int midGen = 1;

        for (Tweet t : tweets) {
            ArrayList<Tweet> path = new ArrayList<Tweet>();
            path.add(oT);

            String content = t.getContent();
            Matcher matcher = RT_PATTERN.matcher(t.getContent());
            String curName = t.getUname();
            if (matcher.find()) {
                Stack<Pair<String, String>> stack = new Stack<Pair<String, String>>();
                int start = 0;
                int end = content.length();
                do {
                    end = matcher.start();
                    String con = "";
                    if (start != end) {
                        con = content.substring(start, end).trim();
                    }
                    start = matcher.end();
                    end = content.length();
                    stack.push(new Pair<String, String>(curName, con));
                    curName = matcher.group(2).trim();
                }
                while (matcher.find());

                String con = "";
                if (start != end) {
                    con = content.substring(start, end).trim();
                }
                stack.push(new Pair<String, String>(curName, con));

                String curContent = oT.getContent();
                String preName = oT.getUname();
                while (!stack.isEmpty()) {
                    Pair<String, String> pair = stack.pop();
                    curContent = String
                            .format("%s%s%s:%s", pair.getValue(), RT_TAG,
                                    preName, curContent);
                    preName = pair.getKey();
                    Tweet internNode = null;
                    if (!stack.isEmpty()) {
                        internNode = new Tweet("", curContent, pair.getKey(),
                                "", -1, oT.getMid());
                        UUID uid = UUID.randomUUID();
                        internNode.setMid(Long.toString(
                                uid.getLeastSignificantBits() & uid
                                        .getMostSignificantBits()));
                        // internNode.setMid(String.format("-%s%s", oT.getMid(),
                        // midGen++));
                        internNode.setUid("-" + Integer
                                .toString(pair.getKey().hashCode()));
                    } else {
                        internNode = t;
                        t.setContent(curContent);
                    }

                    path.add(internNode);
                }
            } else {
                t.setContent(
                        t.getContent().trim() + RT_TAG + oT.getUname() + ":"
                                + oT.getContent());
                path.add(t);
            }
            tree.addPath(path);
        }
        setupTimeStamp(tree);
        return tree;
    }

    public static TrieTree<Tweet> parseTwitter(Tweet oT, List<Tweet> tweets) {

        // 按照时间戳进行排序
        Collections.sort(tweets, new Tweet.TimeComparator());
        TrieTree<Tweet> tree = new TrieTree<Tweet>(TNodeFactory.instance);
        tree.add(oT);

        int midGen = 1;
        String midBase = null;
        if (oT.certain()) {
            midBase = oT.getMid();
        } else {
            for (Tweet t : tweets) {
                if (t.certain()) {
                    midBase = t.getMid();
                    break;
                }
            }
            oT.setMid(String.format("-%s%s", midBase, midGen++));
        }
        for (Tweet t : tweets) {
            ArrayList<Tweet> path = new ArrayList<Tweet>();
            path.add(oT);

            String content = t.getContent();
            // Matcher matcher = RT_PATTERN.matcher(t.getContent());
            Matcher matcher = TWITTER_RT_PATTERN.matcher(t.getContent());
            String curName = t.getUname();
            if (matcher.find()) {
                Stack<Pair<String, String>> stack = new Stack<Pair<String, String>>();
                int start = 0;
                int end = content.length();
                do {
                    end = matcher.start();
                    String con = "";
                    if (start != end) {
                        con = content.substring(start, end).trim();
                    }
                    start = matcher.end();
                    end = content.length();
                    stack.push(new Pair<String, String>(curName, con));
                    curName = matcher.group(2).trim();
                }
                while (matcher.find());

				/*
                 * the last one is the original tweet String con = ""; if (start
				 * != end) { con = content.substring(start, end).trim(); }
				 * stack.push(new Pair<String, String>(curName, con));
				 */

                String curContent = oT.getContent();
                String preName = oT.getUname();
                while (!stack.isEmpty()) {
                    Pair<String, String> pair = stack.pop();
                    curContent = String
                            .format("%s%s%s:%s", pair.getValue(), RT_TAG,
                                    preName, curContent);
                    preName = pair.getKey();
                    Tweet internNode = null;
                    if (!stack.isEmpty()) {
                        internNode = new Tweet("", curContent, pair.getKey(),
                                "", -1, oT.getMid());
                        internNode.setMid(String
                                .format("-%s%s", midBase, midGen++));
                        internNode.setUid("-" + Integer
                                .toString(pair.getKey().hashCode()));
                    } else {
                        internNode = t;
                        t.setContent(curContent);
                    }

                    path.add(internNode);
                }
            } else {
                t.setContent(
                        t.getContent().trim() + RT_TAG + oT.getUname() + ":"
                                + oT.getContent());
                path.add(t);
            }
            tree.addPath(path);
        }
        setupTimeStamp(tree);
        return tree;
    }

    public static TrieTree<Tweet> parse_pre(Tweet oT, List<Tweet> tweets) {

        // 按照时间戳进行排序
        Collections.sort(tweets, new Tweet.TimeComparator());
        TrieTree<Tweet> tree = new TrieTree<Tweet>(TNodeFactory.instance);
        tree.add(oT);

        int midGen = 1;

        for (Tweet t : tweets) {
            ArrayList<Tweet> path = new ArrayList<Tweet>();
            path.add(oT);
            String curContent = new String(
                    RT_TAG + oT.getUname() + ":" + oT.getContent());
            if (t.getContent().contains(RT_TAG)) {
                long ts = oT.getTs();
                String content = t.getContent();
                int idx = content.length();
                int end = content.length();
                do {
                    end = idx;
                    idx = content.lastIndexOf(RT_TAG, end - 1);

                    String node = null;

                    if (idx >= 0) {
                        node = content.substring(idx + RT_TAG.length(), end);
                        curContent = String
                                .format("%s%s%s", RT_TAG, node, curContent);
                    } else {
                        node = content.substring(0, end);
                        curContent = String.format("%s%s", node, curContent);
                    }

                    int deIdx = node.indexOf(':');
                    // String[] field = new String[] { "", "" };
                    Tweet internNode = null;
                    if (idx > 0 && deIdx > 0) {
                        String uname = node.substring(0, deIdx);
                        internNode = new Tweet("", curContent, uname, "", -1,
                                oT.getMid());
                        UUID uuid = UUID.randomUUID();
                        // internNode.setMid(String.format("-%s%s", oT.getMid(),
                        // midGen++));
                        internNode.setMid(Long.toString(
                                (uuid.getMostSignificantBits() & uuid
                                        .getLeastSignificantBits())));
                        internNode.setUid("-" + Integer
                                .toString(uname.hashCode()));
                    } else {
                        internNode = t;
                        t.setContent(curContent);
                    }
                    path.add(internNode);
                }
                while (idx > 0);
            } else {
                t.setContent(t.getContent() + curContent);
                path.add(t);
            }
            tree.addPath(path);
        }
        setupTimeStamp(tree);
        return tree;
    }

    public static void setupTimeStamp(TrieTree<Tweet> tweet) {
        Node<Tweet> root = tweet.getRoot();
        if (root != null) {
            setupTs(root, 0);
        }
    }

    private static Random rand = new Random();

    /**
     * 在start和end之间按照uniform distribution取样
     *
     * @author xiafan
     */
    private static class UniformTsGen implements ITimeStampGenerator {
        @Override
        public long chooseTs(long start, long end) {
            long ts = -1;
            if (end <= start + 1) {
                ts = start;
            } else {
                int length = (int) (end - start);
                length = (length < 0) ? 1 : length;
                ts = rand.nextInt(length);
            }
            return ts;
        }
    }

    public static class ZipfTsGen implements ITimeStampGenerator {
        double theta;
        Random rand = new Random();

        /**
         * theta控制了分布的倾斜程度
         *
         * @param theta
         */
        public ZipfTsGen(double theta) {
            this.theta = theta;
        }

        /**
         * 在start和end之间生成符合zipf分布的采样
         */
        @Override
        public long chooseTs(long start, long end) {
            if (start == 0 || end <= start + 1)
                return start;

            long ts = -1;
            double alpha = (1 - theta) / (Math.pow(end, 1 - theta) - Math
                    .pow(start, 1 - theta));
            float z = rand.nextFloat();
            ts = (long) Math
                    .pow((1 - theta) * z / alpha + Math.pow(start, 1 - theta),
                            1 / (1 - theta));
            return ts;
        }
    }

    private static ITimeStampGenerator tsGen = new ZipfTsGen(2);

    /**
     * @param curNode
     * @param upBound the timestamp of the parent node plus one
     */
    public static void setupTs(Node<Tweet> curNode, long upBound) {
        if (!curNode.getValue().certain()) {
            long lowBound = tsLowBound(curNode);
            if (upBound == 0) {
                curNode.getValue().setTs((lowBound - 1) * 1000);
            } else {
                long ts = tsGen.chooseTs(upBound, lowBound);
                curNode.getValue().setTs(ts * 1000);
            }
        }
        Iterator<Node<Tweet>> iter = curNode.iterator();
        while (iter.hasNext()) {
            setupTs(iter.next(), curNode.getValue().getTs() / 1000 + 1);
        }
    }

    public static long tsLowBound(Node<Tweet> curNode) {
        long ret = curNode.getValue().getTs() / 1000;
        if (ret <= 0) {
            Iterator<Node<Tweet>> iter = curNode.iterator();
            if (iter.hasNext()) {
                long bound = tsLowBound(iter.next()) - 1;
                if (bound > ret) {
                    ret = bound;
                }
            }
        } else {
            ret--;
        }
        return ret;
    }

    public static TrieTree<Tweet> parse(String oTweet, List<String> tweets) {
        Tweet oT = new Tweet();
        oT.parse(oTweet);
        List<Tweet> rtTweets = new LinkedList<Tweet>();
        for (String tweet : tweets) {
            Tweet t = new Tweet();
            t.parse(tweet);
            rtTweets.add(t);
        }
        return parse(oT, rtTweets);
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            UUID uid = UUID.randomUUID();
            System.out.println(uid.getLeastSignificantBits() & uid
                    .getMostSignificantBits());
        }
        Tweet t = NormFormat.normTwitterToTweet(
                "2009-09-30 23:55:55|#|daytrend|#|RT @Urbane_Gorilla: RT @IRON100: In case anyone did not know @BarrieAbalard is a really cool person :) I so got that!");

        Tweet ot = t.computeRetweet();
        TrieTree<Tweet> tree = RTTreeConstructor
                .parseTwitter(ot, Arrays.asList(t));
        PostOrderTraverse<Tweet> traverse = new PostOrderTraverse<Tweet>(tree);

        while (traverse.hasNext()) {
            System.out.println(traverse.next());
        }

        RTTreeTimeSeries series = new RTTreeTimeSeries(tree);
        while (series.hasNext()) {
            Pair<Tweet, Histogram> pair = series.next();
            System.out.println(pair);
        }
		/*
		 * Queue<TweetNode> queue = new LinkedList<TweetNode>(); TweetNode node
		 * = (TweetNode) tree.getRoot(); queue.add(node); while
		 * (!queue.isEmpty()) { node = queue.poll(); Histogram hist = new
		 * Histogram(); Iterator<Node<Tweet>> iter = node.iterator(); while
		 * (iter.hasNext()) { TweetNode child = (TweetNode) iter.next();
		 * hist.increment(child.getValue().getTs()); queue.add(child); }
		 * System.out.println(node.getValue());
		 * System.out.println(hist.toString()); }
		 */

    }

    private static void tweetTest() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(
                        "/Users/xiafan/Documents/dataset/expr/twitter/orgin/part0.txt"))),
                "utf-8"));
        String line = null;
        Tweet ot = null;
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        while (null != (line = reader.readLine())) {
            try {
                Status status = new Status(line);
                if (status.getRetweetedStatus() != null && ot == null) {
                    ot = new Tweet();
                    ot.parse(status.getRetweetedStatus());
                }
                Tweet tweet = new Tweet();
                tweet.parse(status);
                tweets.add(tweet);
            } catch (WeiboException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        TrieTree<Tweet> tree = RTTreeConstructor.parse(ot, tweets);
        PostOrderTraverse<Tweet> traverse = new PostOrderTraverse<Tweet>(tree);

        while (traverse.hasNext()) {
            System.out.println(traverse.next());
        }
    }

    private static void statusTest() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(
                        "/Users/xiafan/Documents/dataset/microblog/城管.txt"))),
                "utf-8"));
        String line = null;
        Tweet ot = null;
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        while (null != (line = reader.readLine())) {
            try {
                Status status = new Status(line);
                if (status.getRetweetedStatus() != null && ot == null) {
                    ot = new Tweet();
                    ot.parse(status.getRetweetedStatus());
                }
                Tweet tweet = new Tweet();
                tweet.parse(status);
                tweets.add(tweet);
            } catch (WeiboException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        TrieTree<Tweet> tree = RTTreeConstructor.parse(ot, tweets);
        PostOrderTraverse<Tweet> traverse = new PostOrderTraverse<Tweet>(tree);

        while (traverse.hasNext()) {
            System.out.println(traverse.next());
        }
    }
}
