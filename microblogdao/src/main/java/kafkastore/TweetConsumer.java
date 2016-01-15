package kafkastore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import weibo4j.StatusSerDer;
import weibo4j.model.Status;

/**
 * 如何流式的构建微博的转发序列
 * 
 * @author xiafan
 *
 */
public class TweetConsumer {
	private static final Logger logger = Logger.getLogger(TweetConsumer.class);
	KafkaConsumer<byte[], byte[]> consumer;

	public TweetConsumer() {

	}

	/**
	 * used for test
	 * 
	 * @param topics
	 * @param group
	 * @param servers
	 * @param reset
	 */
	public void open(List<String> topics, String group, String servers, boolean reset) {
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		props.put("group.id", group);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		consumer = new KafkaConsumer<byte[], byte[]>(props);
		if (reset) {
			for (String topic : topics)
				for (PartitionInfo info : consumer.partitionsFor(topic)) {
					TopicPartition part = new TopicPartition(info.topic(), info.partition());
					consumer.assign(Arrays.asList(part));
					consumer.seekToBeginning(part);
				}
		} else {
			consumer.subscribe(topics);
		}
	}

	public void open(List<String> topics, String group, String servers) {
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		props.put("group.id", group);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		consumer = new KafkaConsumer<byte[], byte[]>(props);
		consumer.subscribe(topics);
		/*
		 * for (String topic : topics) for (PartitionInfo info :
		 * consumer.partitionsFor(topic)) { TopicPartition part = new
		 * TopicPartition(info.topic(), info.partition());
		 * consumer.assign(Arrays.asList(part)); consumer.seekToBeginning(part);
		 * }
		 */
	}

	public void close() {
		if (consumer != null) {
			consumer.close();
			consumer = null;
		}
	}

	@Override
	public void finalize() {
		close();
	}

	private static Object readObject(byte[] data) {
		// ret.add(kryos.get().readObject(new Input(record.value()),
		// UserCrawlState.class));

		ObjectInputStream input;
		try {
			input = new ObjectInputStream(new ByteArrayInputStream(data));
			return input.readObject();
		} catch (IOException e) {
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
		return null;
	}

	public Map<String, List<Object>> nextStates() {
		Map<String, List<Object>> ret = new HashMap<String, List<Object>>();
		ConsumerRecords<byte[], byte[]> records = null;
		int count = 0;
		do {
			try {
				count++;
				records = consumer.poll(1000);
				if (count % 20 == 0) {
					logger.info("looping " + count + " times, no results fetched!!!");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} while (records == null || records.count() == 0 || count > 100);

		for (ConsumerRecord<byte[], byte[]> record : records) {
			if (!ret.containsKey(record.topic())) {
				ret.put(record.topic(), new ArrayList<Object>());
			}
			try {
				Object data = readObject(record.value());
				if (data != null)
					ret.get(record.topic()).add(data);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}

	public List<UserCrawlState> nextCrawlerStates() {
		List<UserCrawlState> ret = new ArrayList<UserCrawlState>();
		for (Entry<String, List<Object>> entry : nextStates().entrySet()) {
			assert entry.getKey().equals(KafkaTopics.VIP_UID_TOPIC);
			for (Object data : entry.getValue())
				ret.add((UserCrawlState) data);
		}
		return ret;
	}

	public List<TimeSeriesUpdateState> nextTSUpdateStates() {
		List<TimeSeriesUpdateState> ret = new ArrayList<TimeSeriesUpdateState>();
		for (Entry<String, List<Object>> entry : nextStates().entrySet()) {
			assert entry.getKey().equals(KafkaTopics.RTSERIES_STATE_TOPIC);
			for (Object data : entry.getValue())
				ret.add((TimeSeriesUpdateState) data);
		}
		return ret;
	}

	public List<RepostCrawlState> nextRepostStates() {
		List<RepostCrawlState> ret = new ArrayList<RepostCrawlState>();
		for (Entry<String, List<Object>> entry : nextStates().entrySet()) {
			assert entry.getKey().equals(KafkaTopics.RTCRALW_STATE_TOPIC);
			for (Object data : entry.getValue())
				ret.add((RepostCrawlState) data);
		}
		return ret;
	}

	public Map<String, List<Status>> nextStatus() {
		Map<String, List<Status>> ret = new HashMap<String, List<Status>>();
		ConsumerRecords<byte[], byte[]> records = null;

		do {
			try {
				records = consumer.poll(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} while (records == null || records.count() == 0);

		for (ConsumerRecord<byte[], byte[]> record : records) {
			if (!ret.containsKey(record.topic())) {
				ret.put(record.topic(), new ArrayList<Status>());
			}
			try {
				Status cur = StatusSerDer.fromJSON(new String(record.value(), TweetKafkaProducer.ENCODING));
				if (cur != null) {
					ret.get(record.topic()).add(cur);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("conf/log4j.properties");
		TweetConsumer consumer = new TweetConsumer();
		consumer.open(Arrays.asList(KafkaTopics.VIP_UID_TOPIC, KafkaTopics.RTCRALW_STATE_TOPIC),
				KafkaTopics.TWEET_STORE_GROUP, "10.11.1.212:9092", true);

		for (UserCrawlState state : consumer.nextCrawlerStates()) {
			System.out.println(state);
		}

		for (RepostCrawlState state : consumer.nextRepostStates()) {
			System.out.println(state);
		}
	}
}
