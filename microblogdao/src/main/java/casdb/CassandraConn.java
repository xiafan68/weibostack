package casdb;

import org.apache.log4j.Logger;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.SyntaxError;

import casdb.DBUtil.StatusFieldValueInSpector;
import casdb.DBUtil.UserFieldInSpector;
import weibo4j.model.User;

/**
 * microblog_table mid -> []
 * 
 * rt_table mid -> [content --- mid]
 * 
 * @author xiafan
 *
 */
public class CassandraConn {
	private final Logger logger = Logger.getLogger(CassandraConn.class);
	private Cluster cluster;
	private Session session;

	PreparedStatement userStatement;

	public void connect(String node) {
		cluster = Cluster.builder().withProtocolVersion(ProtocolVersion.V4).addContactPoint(node).build();
		PoolingOptions options = cluster.getConfiguration().getPoolingOptions();
		options.setCoreConnectionsPerHost(HostDistance.LOCAL, 10);
		options.setMaxConnectionsPerHost(HostDistance.LOCAL, 100);
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}

		connectDB();
		userStatement = session.prepare(DBUtil.genPrepareStatement(User.class, new UserFieldInSpector()));
	}

	private void connectDB() {
		try {
			session = cluster.connect(DBUtil.KEYSPACE);
		} catch (InvalidQueryException ex) {
			logger.error(ex.getMessage());
			if (session == null) {
				session = cluster.connect();
				init();
			}
		}
	}

	public PreparedStatement prepare(String cql) {
		PreparedStatement statement = null;
		while (true) {
			try {
				statement = session.prepare(cql);
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage() + "sql:" + cql);
				session.close();
				connectDB();
			}
		}
		return statement;
	}

	public void executeCQL(String cql) {
		while (true) {
			try {
				logger.debug(cql);
				session.execute(cql);
				break;
			} catch (Exception ex) {
				logger.error(cql + "," + ex.getMessage());
				session.close();
				connectDB();
			}
		}
	}

	public void executeStmt(Statement bound) {
		while (true) {
			try {
				if (bound instanceof BatchStatement) {
					if (((BatchStatement) bound).size() == 0)
						break;
				}
				session.execute(bound);
				break;
			} catch (InvalidQueryException ex) {
				logger.error(ex.getMessage() + ";sql:" + bound.toString());
			} catch (Exception ex) {
				logger.error(ex.getMessage() + ";sql:" + bound.toString());
				session.close();
				connectDB();
			}
		}
	}

	public void execute(BoundStatement bound) {
		while (true) {
			try {
				session.execute(bound);
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage() + ";sql:" + bound.toString());
				session.close();
				connectDB();
			}
		}
	}

	public ResultSet query(String cql) {
		ResultSet ret = null;
		while (true) {
			try {
				ret = session.execute(cql);
				break;
			} catch (Exception ex) {
				if (ex instanceof SyntaxError) {
					break;
				}
				logger.error(ex.getMessage() + ";sql:" + cql);
				session.close();
				connectDB();
			}
		}
		return ret;
	}

	public ResultSet query(BoundStatement cql) {
		ResultSet ret = null;
		while (true) {
			try {
				ret = session.execute(cql);
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage() + ";sql:" + cql);
				session.close();
				connectDB();
			}
		}
		return ret;
	}

	public void storeUser(User user) {
		BoundStatement boundStatement = new BoundStatement(userStatement);
		StatusFieldValueInSpector inspector = new StatusFieldValueInSpector(boundStatement);
		DBUtil.genBound(inspector, user);
		session.execute(inspector.getBound());
	}

	public void init() {
		executeCQL("create keyspace if not exists " + DBUtil.KEYSPACE
				+ " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };");
		session.close();
		session = cluster.connect(DBUtil.KEYSPACE);
		System.out.println("creating table status");
		executeCQL(DBUtil.genStatusTable());
		System.out.println("creating table user");
		executeCQL(DBUtil.genUserTable());
		System.out.println("creating table usercrawlstate");
		executeCQL(DBUtil.genUserCrawlState());
		System.out.println("creating table repostcrawlstate");
		executeCQL(DBUtil.genRepostCrawlState());
		System.out.println("creating table retweet");
		executeCQL(String.format(
				"create table IF NOT EXISTS  %s(omid text, hashcode int, content text, rtmid text, PRIMARY KEY(omid, hashcode, content));",
				DBUtil.RETWEET_TABLE));

		System.out.println("creating table retweetseries");
		executeCQL(String.format(
				"CREATE TABLE IF NOT EXISTS  %s (mid text, tstime bigint,freq counter, primary key(mid, tstime));",
				DBUtil.RETWEET_SERIES_TABLE));
		System.out.println("creating table segstate");
		executeCQL(String.format("CREATE TABLE IF NOT EXISTS  %s (mid text PRIMARY KEY, updatetime bigint);",
				DBUtil.SEG_STATE));

		System.out.println("creating table IF NOT EXISTS  wordzscore");
		// 用于记录每个小时最异常的词
		executeCQL(String.format(
				"create table IF NOT EXISTS  %s(word text, zscore float, tstime bigint, PRIMARY KEY(tstime, word));",
				DBUtil.WORD_ZSCORE_TABLE));
		// 用于记录每个单词的词频时序
		executeCQL(String.format(
				"create table IF NOT EXISTS  %s(word text, freq counter, tstime bigint, PRIMARY KEY(word,tstime))",
				DBUtil.WORD_FREQ_TABLE));
		executeCQL(String.format(
				"create table IF NOT EXISTS  %s(crawltype text, freq counter, tstime bigint, PRIMARY KEY(crawltype, tstime))",
				DBUtil.CRAWL_STATS));
		RetweetStatsDao.init(this);
	}

	public void close() {
		cluster.close();
	}

	public static void main(String[] args) {
		CassandraConn conn = new CassandraConn();
		conn.connect(args[0]);
		// conn.executeCQL("select * from status limit 10");
		conn.init();
		conn.close();
	}

}
