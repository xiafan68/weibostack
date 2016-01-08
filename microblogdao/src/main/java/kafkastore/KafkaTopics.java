package kafkastore;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class KafkaTopics {
	// different groups
	// 将kafka中的tweet数据写入到数据库中的分组
	public static final String TWEET_STORE_GROUP = "pubtweet_crawler_grp";

	// 持久化爬取状态的组
	public static final String STATE_STORE_GROUP = "state_grp";

	// 爬取转发的分组，需要从kafka中读取爬取的状态
	public static final String RETWEET_CRAWLER_GROUP = "rtcrawler_grp";
	// 更新转发序列
	public static final String RTSERIES_GROUP = "rtseries_grp";

	// 对转发序列进行近似的分组，需要读取存放到RETWEET_TOPIC中的数据
	public static final String RTSERIES_SEG_GROUP = "rtseg_grp";

	public static final String EMONITOR_GROUP = "emonitor";
	public static final String SPARK_MONITOR_GROUP = "semonitor";

	// 用于统计词频的group
	public static final String WORD_STATS_GROUP = "wordstats_grp";

	// public cralwer的数据写入到这个topic中去
	public static final String PUB_TWEET_TOPIC = "pubtweet";
	// repost monitor的数据写入到这个topics中去
	// event monitor或者repost cralwer爬取到的数据
	public static final String RETWEET_TOPIC = "retweet";

	public static final String VIP_UID_TOPIC = "vipstate";
	// 转发的状态
	public static final String RTCRALW_STATE_TOPIC = "rtstate";
	// 时间序列更新状态
	public static final String RTSERIES_STATE_TOPIC = "rtsupdate";

	public static final String EMONITOR_TOPIC = "emonitor";
	public static final String SPARK_MONITOR_TOPIC = "semonitor";

	// 最开始监控的public timeline和vip用户的tweets
	public static final String TWEET_TOPIC = "tweet";

	// 需要对时间序列进行segmentation的mid
	public static final String SEG_TOPIC = "segjob";

	/**
	 * 初始化topics
	 * 
	 * @param args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		List<String> commands = new ArrayList<String>();
		Field[] fields = KafkaTopics.class.getFields();
		for (Field field : fields) {
			if (field.getName().contains("topic")) {
				commands.add(String.format(
						"bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic %s",
						args[0], field.get(KafkaTopics.class).toString()));
			}
		}

		for (String command : commands) {
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
