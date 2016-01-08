package kafkastore;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import weibo4j.WeiboException;
import weibo4j.model.Status;
import weibo4j.org.json.JSONException;

public class KryoTest {
	@Test
	public void statusTest()
			throws KryoException, IOException, WeiboException, JSONException, weibo4j.model.WeiboException {
		String input = "/Users/kc/Documents/dataset/weibo/status/statussample.txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
		TweetKafkaProducer producer = new TweetKafkaProducer(KafkaProducerFactory.createProducer("localhost:9092"));
		String line = null;
		while (null != (line = reader.readLine())) {
			for (Status status : Status.constructWapperStatus(line).getStatuses()) {
				if (status.getRetweetedStatus() != null)
					producer.store(KafkaTopics.RETWEET_TOPIC, status);
				// ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
				Kryo kryo = KryoFactory.create();
				Output output = new Output(4096, 8192);
				kryo.writeObject(output, status);
				output.flush();

				Status rstatus = kryo.readObject(new Input(output.getBuffer(), 0, output.position()), Status.class);
				System.out.println(rstatus.equals(status));
				// System.out.println(status);
				// System.out.println(rstatus);
			}
		}
		reader.close();
		producer.close();
	}
}
