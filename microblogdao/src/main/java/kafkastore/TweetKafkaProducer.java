package kafkastore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kafkastore.model.RepostCrawlState;
import kafkastore.model.TimeSeriesUpdateState;
import kafkastore.model.UserCrawlState;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import weibo4j.model.Status;
import weibo4j.org.json.JSONObject;

/**
 * 用于将爬取到的tweet发布到kafka中去
 * 
 * @author xiafan
 *
 */
public class TweetKafkaProducer {
	public static final String ENCODING = "UTF8";
	Producer<byte[], byte[]> producer;

	/*
	 * private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() { protected
	 * Kryo initialValue() { return KryoFactory.create(); }; }; private
	 * ThreadLocal<Output> output = new ThreadLocal<Output>() { protected Output
	 * initialValue() { return new Output(4096, 8192); }; };
	 */
	public TweetKafkaProducer(Producer<byte[], byte[]> producer) {
		this.producer = producer;
	}

	public void store(String topic, Status tweet) {
		if (tweet == null || tweet.getMid() == null) {
			return;
		}
		while (true) {
			Future<RecordMetadata> future;
			try {
				future = producer.send(new ProducerRecord<byte[], byte[]>(topic, tweet.getMid().getBytes(),
						new JSONObject(tweet).toString().getBytes(ENCODING)));
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1);
			}
			try {
				future.get();
				break;
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
			}
		}
	}

	private void storeObject(String topic, byte[] key, Object obj) {
		while (true) {
			try {
				ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
				ObjectOutputStream output = new ObjectOutputStream(bOutput);
				output.writeObject(obj);

				Future<RecordMetadata> future = producer
						.send(new ProducerRecord<byte[], byte[]>(topic, key, bOutput.toByteArray()));
				future.get();
				break;
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void storeRepostCrawlState(RepostCrawlState state) {
		storeObject(KafkaTopics.RTCRALW_STATE_TOPIC, state.mid.getBytes(), state);
	}

	public void close() {
		producer.flush();
		producer.close();
	}

	/**
	 * 向线段近似程序发送通知消息
	 * 
	 * @param state
	 */
	public void storeTsUpdateState(TimeSeriesUpdateState state) {
		storeObject(KafkaTopics.RTSERIES_STATE_TOPIC, state.mid.getBytes(), state);
	}

	/**
	 * 向kafka中写入用户的爬取状态
	 * 
	 * @param vipCrawlerState
	 */
	public void storeUserCrawlState(UserCrawlState userState) {
		storeObject(KafkaTopics.VIP_UID_TOPIC, userState.uid.getBytes(), userState);
	}

	public static void main(String[] args) {
		TweetKafkaProducer producer = new TweetKafkaProducer(KafkaProducerFactory.createProducer("localhost:9092"));

	}
}
