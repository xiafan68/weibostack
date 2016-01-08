package kafkastore;

import java.sql.Date;

import com.esotericsoftware.kryo.Kryo;

import weibo4j.model.Source;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.Visible;

public class KryoFactory {
	public static Kryo create() {
		Kryo kryo = new Kryo();
		kryo.register(Status.class);
		kryo.register(User.class);
		kryo.register(RepostCrawlState.class);
		kryo.register(UserCrawlState.class);
		kryo.register(TimeSeriesUpdateState.class);
		kryo.register(Visible.class);
		kryo.register(Source.class);
		kryo.register(Date.class);
		return kryo;
	}
}
