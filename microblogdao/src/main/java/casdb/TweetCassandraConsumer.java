package casdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import kafkastore.KafkaTopics;
import kafkastore.TweetConsumer;
import weibo4j.model.Status;

/**
 * 从kafka中读取数据，并且存储到cassandra中去
 * 
 * @author xiafan
 *
 */
public class TweetCassandraConsumer extends Thread {
	TweetConsumer consumer;
	CassandraConn con;
	TweetDao tweetDao;

	public TweetCassandraConsumer(TweetConsumer consumer, CassandraConn con) {
		this.consumer = consumer;
		this.con = con;
		this.tweetDao = new TweetDao(con);
	}

	@Override
	public void run() {
		while (true) {
			for (Entry<String, List<Status>> topicData : consumer.nextStatus().entrySet()) {
				for (Status cur : topicData.getValue()) {
					System.err.println(cur);

					// tweetDao.putTweet(cur);
					// if (cur.getRetweetedStatus() != null) {
					// tweetDao.putTweet(cur.getRetweetedStatus());
					// }
				}
			}
		}
	}

	/**
	 * args[0]: cassandra server args[1]: kafka server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		OptionParser parser = new OptionParser();
		parser.accepts("c", "cassandra server address").withRequiredArg().ofType(String.class);
		parser.accepts("k", "kafka server address").withRequiredArg().ofType(String.class);
		OptionSet set = parser.parse(args);
		CassandraConn conn = new CassandraConn();
		conn.connect(set.valueOf("c").toString());// "127.0.0.1"
		TweetConsumer consumer = new TweetConsumer();
		consumer.open(Arrays.asList(KafkaTopics.RETWEET_TOPIC, KafkaTopics.TWEET_TOPIC), "test",
				set.valueOf("k").toString(), true);// "localhost:9092"
		TweetCassandraConsumer storer = new TweetCassandraConsumer(consumer, conn);
		storer.start();
	}
}
