package cms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import searchapi.FetchTweetQuery;
import searchapi.InvalidJob;
import searchapi.TKeywordQuery;
import searchapi.TweetSeg;
import searchapi.TweetService.Iface;
import searchapi.Tweets;

/**
 * 当前实现通过读取zookeeper中的注册信息，发现提供查询服务的server
 * 
 * @author xiafan
 *
 */
public class TSearchService implements Iface, Watcher, StatCallback {
	List<TSearchServer> servers = new ArrayList<TSearchServer>();
	String zkServer;
	ZooKeeper zk;
	String watchNode;
	boolean dead;

	public TSearchService(String zkServer, String watchNode) {
		this.zkServer = zkServer;
		this.watchNode = watchNode;
		dead = false;
	}

	public void connect() throws IOException {
		zk = new ZooKeeper(zkServer, 3000, this);
		zk.exists(watchNode, true, this, null);
	}

	public void process(WatchedEvent event) {
		String path = event.getPath();
		if (event.getType() == Event.EventType.None) {
			// We are are being told that the state of the
			// connection has changed
			switch (event.getState()) {
			case SyncConnected:
				// In this particular example we don't need to do anything
				// here - watches are automatically re-registered with
				// server and any watches triggered while the client was
				// disconnected will be delivered (in order of course)
				break;
			case Expired:
				// TODO: It's all over, close
				dead = true;
				break;
			}
		} else {
			if (path != null && path.equals(watchNode)) {
				// Something has changed on the node, let's find out
				zk.exists(watchNode, true, this, null);
			}
		}
	}

	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists;
		switch (rc) {
		case Code.Ok:
			exists = true;
			break;
		case Code.NoNode:
			exists = false;
			break;
		case Code.SessionExpired:
		case Code.NoAuth:
			dead = true;
			// TODO:close
			return;
		default:
			// Retry errors
			zk.exists(watchNode, true, this, null);
			return;
		}

		byte b[] = null;
		if (exists) {
			try {
				b = zk.getData(watchNode, false, null);
			} catch (KeeperException e) {
				// We don't need to worry about recovering now. The watch
				// callbacks will kick off any exception handling
				e.printStackTrace();
			} catch (InterruptedException e) {
				return;
			}
		}
		// if ((b == null && b != prevData) || (b != null &&
		// !Arrays.equals(prevData, b))) {
		// listener.exists(b);
		// prevData = b;
		// }
	}

	@Override
	public void indexTweetSeg(TweetSeg seg) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Long> search(TKeywordQuery query) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tweets fetchTweets(FetchTweetQuery query) throws InvalidJob, TException {
		// TODO Auto-generated method stub
		return null;
	}
}
