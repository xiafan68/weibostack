package weibo.rttree;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import ds.tree.trie.Node;
import ds.tree.trie.TrieTree;
import util.Histogram;
import util.Pair;
import weibo.NormFormat;
import weibo.Tweet;

/**
 * 给定一颗转发树，计算转发树中每条微博的时间序列
 * 
 * @author xiafan
 * 
 */
public class RTTreeTimeSeries implements Iterator<Pair<Tweet, Histogram>> {
	TrieTree<Tweet> tree;

	public RTTreeTimeSeries(TrieTree<Tweet> tree) {
		super();
		this.tree = tree;
		Node<Tweet> root = tree.getRoot();
		stack.push(new Pair<Node<Tweet>, Iterator<Node<Tweet>>>(root, root.iterator()));
		histStack.push(new Histogram());
	}

	@Override
	public boolean hasNext() {
		if (ret == null)
			advance();
		return ret != null;
	}

	int lastLevel = -1;

	Stack<Pair<Node<Tweet>, Iterator<Node<Tweet>>>> stack = new Stack<Pair<Node<Tweet>, Iterator<Node<Tweet>>>>();
	Stack<Histogram> histStack = new Stack<Histogram>();

	Pair<Tweet, Histogram> ret = null;

	private void advance() {
		if (ret != null)
			return;
		while (!stack.isEmpty()) {
			Pair<Node<Tweet>, Iterator<Node<Tweet>>> cur = stack.peek();
			if (cur.getValue().hasNext()) {
				Node<Tweet> child = cur.getValue().next();
				histStack.peek().increment(child.getValue().getTs());
				stack.push(new Pair<Node<Tweet>, Iterator<Node<Tweet>>>(child, child.iterator()));
				histStack.push(new Histogram());
			} else {
				Node<Tweet> node = cur.getKey();
				stack.pop();
				Histogram hist = histStack.pop();
				if (hist.size() > 0) {
					if (histStack.size() > 0)
						histStack.peek().merge(hist);
					ret = new Pair<Tweet, Histogram>(node.getValue(), hist);
					break;
				}
			}
		}
	}

	@Override
	public Pair<Tweet, Histogram> next() {
		if (ret == null)
			advance();
		Pair<Tweet, Histogram> tmp = ret;
		ret = null;
		return tmp;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new DataInputStream(new BufferedInputStream(new FileInputStream(
						// "/home/xiafan/Documents/dataset/expr/sample/全球时尚.txt"))),
						"/Users/xiafan/Documents/dataset/microblog/全球时尚.txt"))), "utf-8"));
		String line = null;
		Tweet ot = null;
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		while (null != (line = reader.readLine())) {
			Tweet tweet = new Tweet();
			Pair<Tweet, Tweet> pair = null;
			try {
				pair = NormFormat.transToTweets(line);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pair != null) {
				ot = pair.getValue();
				tweets.add(pair.getKey());
			}
			/*
			 * try { Status status = new Status(line); if
			 * (status.getRetweetedStatus() != null && ot == null) { ot = new
			 * Tweet(); ot.parse(status.getRetweetedStatus()); } Tweet tweet =
			 * new Tweet(); tweet.parse(status); tweet.parse(line);
			 * tweets.add(tweet); } catch (WeiboException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (JSONException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (weibo4j.model.WeiboException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); }
			 */
		}
		TrieTree<Tweet> tree = RTTreeConstructor.parse(ot, tweets);
		System.out.println(tree.toString());

		RTTreeTimeSeries series = new RTTreeTimeSeries(tree);
		// System.out.println("ot:" + ot);
		HashSet<String> mids = new HashSet<String>();
		while (series.hasNext()) {
			Pair<Tweet, Histogram> pair = series.next();
			if (mids.contains(pair.getKey().getMid())) {
				System.err.println(pair.getKey().getMid());
			}
			mids.add(pair.getKey().getMid());
			System.out.println(pair.getKey() + " " + pair.getValue().toString());
		}
		reader.close();
	}
}
