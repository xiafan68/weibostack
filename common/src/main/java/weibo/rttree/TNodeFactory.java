package weibo.rttree;

import ds.tree.trie.Node;
import ds.tree.trie.NodeFactory;
import weibo.Tweet;

public class TNodeFactory implements NodeFactory<Tweet> {
	public static TNodeFactory instance = new TNodeFactory();

	@Override
	public Node<Tweet> createNode(int level, Tweet value, Node<Tweet> parent) {
		return new TweetNode(level, value, (TweetNode) parent);
	}
}
