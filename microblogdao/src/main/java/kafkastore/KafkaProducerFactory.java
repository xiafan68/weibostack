package kafkastore;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class KafkaProducerFactory {
	public static Producer<byte[], byte[]> createProducer(String servers) {
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		props.put("acks", "all");
		// props.put("retries", 0);
		// props.put("batch.size", 16384); //批量发送的数据大熊啊
		// props.put("linger.ms", 1); //延迟发送的时间
		// props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		return new KafkaProducer<byte[], byte[]>(props);
	}
}
