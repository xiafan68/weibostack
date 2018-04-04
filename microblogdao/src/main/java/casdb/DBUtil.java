package casdb;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Iterator;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Row;

import kafkastore.model.RepostCrawlState;
import kafkastore.model.UserCrawlState;
import weibo4j.WeiboException;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

public class DBUtil {
	public static final String KEYSPACE = "microblogapp";
	public static final String MICROBLOG_TABLE = Status.class.getSimpleName();
	public static final String USER_TABLE = User.class.getSimpleName();
	public static final String RETWEET_TABLE = "rttable"; // 用于记录一条微博的所有转发微博
	public static final String RETWEET_SERIES_TABLE = "rtseries"; // 用于记录一条微博的转发时间序列
	public static final String WORD_ZSCORE_TABLE = "wordzscore";
	public static final String WORD_FREQ_TABLE = "wordfreq";
	public static final String SEG_STATE = "segstate";
	public static final String USER_CRAWL_STATE = UserCrawlState.class.getSimpleName();
	public static final String REPOST_CRAWL_STATE = RepostCrawlState.class.getSimpleName();
	public static final String CRAWL_STATS = "crawlstates";

	public static String genStatusTable() {
		StringBuffer buf = new StringBuffer(
				"create table IF NOT EXISTS " + MICROBLOG_TABLE + "(omid text,uid text, uname text,");
		Class sclass = Status.class;
		buf.append(genObjFields(sclass));
		buf.append(", primary key(mid));");
		return buf.toString();
	}

	public static String genUserCrawlState() {
		return genTableForClass(UserCrawlState.class, "primary key(uid)");
	}

	public static String genRepostCrawlState() {
		return genTableForClass(RepostCrawlState.class, "primary key(mid)");
	}

	public static String genUserTable() {
		return genTableForClass(User.class, "primary key(id)");
	}

	public static String genTableForClass(Class tableClass, String priKey) {
		StringBuffer buf = new StringBuffer("create table IF NOT EXISTS " + tableClass.getSimpleName() + "(");
		buf.append(genObjFields(tableClass));
		buf.append(",");
		buf.append(priKey);
		buf.append(")");
		return buf.toString();
	}

	public static String genObjFields(Class sclass) {
		StringBuffer buf = new StringBuffer();
		Field[] fields = sclass.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers()) && field.getType() != User.class
					&& field.getType() != Status.class && !field.getName().startsWith("_")) {
				// System.out.println(field.getName() + "," + field.getType());
				buf.append(field.getName());
				if (field.getType() == String.class) {
					buf.append(" text,");
				} else if (field.getType() == long.class) {
					buf.append(" bigint,");
				} else if (field.getType() == int.class) {
					buf.append(" int,");
				} else if (field.getType() == boolean.class) {
					buf.append(" boolean,");
				} else if (field.getType() == Date.class) {
					buf.append(" bigint,");
				} else if (field.getType() == short.class) {
					buf.append(" smallint,");
				} else {
					buf.append(" text,");
				}
			}
		}
		if (buf.length() > 0)
			buf.delete(buf.length() - 1, buf.length());
		return buf.toString();
	}

	public interface ObjectInspector {
		public void process(Object obj, Field field);
	}

	public static abstract class PrepareStatementGen implements ObjectInspector {
		public StringBuffer fieldBuf;
		public StringBuffer valBuf;

		public PrepareStatementGen(StringBuffer fieldBuf, StringBuffer valBuf) {
			this.fieldBuf = fieldBuf;
			this.valBuf = valBuf;
		}

		public void setFieldBuf(StringBuffer fieldBuf) {
			this.fieldBuf = fieldBuf;
		}

		public void setValBuf(StringBuffer valBuf) {
			this.valBuf = valBuf;
		}

		public StringBuffer getFieldBuf() {
			return fieldBuf;
		}

		public StringBuffer getValBuf() {
			return valBuf;
		}
	}

	/**
	 * 用于生成prepare statement的那条cql
	 * 
	 * @author xiafan
	 *
	 */
	public static class StatusFieldInSpector extends PrepareStatementGen {
		public StatusFieldInSpector() {
			super(new StringBuffer(), new StringBuffer());
		}

		public void process(Object obj, Field field) {
			if (field.getType() == Status.class) {
				fieldBuf.append("omid,");
				valBuf.append("?,");
			} else if (field.getType() == User.class) {
				fieldBuf.append("uid,uname,");
				valBuf.append("?,?,");
			}
		}
	}

	/**
	 * 用于生成prepare statement的那条cql
	 * 
	 * @author xiafan
	 *
	 */
	public static class UserFieldInSpector extends PrepareStatementGen {
		public UserFieldInSpector() {
			super(new StringBuffer(), new StringBuffer());
		}

		public void process(Object obj, Field field) {

		}
	}

	/**
	 * 根据对象的成员变量设置boundstatement的类
	 * 
	 * @author xiafan
	 *
	 */
	public static class PrepareStatementBounder implements ObjectInspector {
		BoundStatement bound;

		public PrepareStatementBounder(BoundStatement bound) {
			this.bound = bound;
		}

		public void process(Object obj, Field field) {
			try {
				if (!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")) {
					field.setAccessible(true);
					if (field.getType() == String.class) {
						bound.setString(field.getName(), field.get(obj).toString());
					} else if (field.getType() == int.class) {
						bound.setInt(field.getName(), field.getInt(obj));
					} else if (field.getType() == short.class) {
						bound.setShort(field.getName(), field.getShort(obj));
					} else if (field.getType() == long.class) {
						bound.setLong(field.getName(), field.getLong(obj));
					} else if (field.getType() == boolean.class) {
						bound.setBool(field.getName(), field.getBoolean(obj));
					} else if (field.getType() == Date.class) {
						Object value = field.get(obj);
						if (value == null) {
							bound.setLong(field.getName(), 0l);
						} else {
							bound.setLong(field.getName(), ((Date) field.get(obj)).getTime());
						}
					} else {
						processNonPrimative(obj, field);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 默认不处理
		 * 
		 * @param obj
		 * @param field
		 */
		public void processNonPrimative(Object obj, Field field) {

		}

		public BoundStatement getBound() {
			return bound;
		}

		public void setBound(BoundStatement bound) {
			this.bound = bound;
		}
	}

	/**
	 * 用于绑定cql的参数
	 * 
	 * @author xiafan
	 *
	 */
	public static class StatusFieldValueInSpector extends PrepareStatementBounder {

		public StatusFieldValueInSpector(BoundStatement bound) {
			super(bound);
		}

		@Override
		public void processNonPrimative(Object obj, Field field) {
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// 不可能到这里来!!!!
			}
			if (field.getType() == Status.class) {
				if (value == null) {
					bound.setString("omid", "-1");
				} else {
					bound.setString("omid", ((Status) value).getMid());
				}
			} else if (field.getType() == User.class) {
				if (value == null) {
					bound.setString("uid", "-1");
					bound.setString("uname", "-1");
				} else {
					User user = (User) value;
					bound.setString("uid", user.getId());
					bound.setString("uname", user.getName());
				}
			}
		}
	}

	// 以下代码用于生成插入的bound statement
	public static String genPrepareStatement(Class sclass, PrepareStatementGen inspect) {
		StringBuffer fieldBuf = inspect.getFieldBuf();
		StringBuffer valBuf = inspect.getValBuf();
		Field[] fields = sclass.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")) {
				if (field.getType().isPrimitive() || field.getType() == String.class || field.getType() == Date.class) {
					fieldBuf.append(field.getName() + ",");
					valBuf.append("?,");
				} else {
					inspect.process(null, field);
				}
			}
		}
		if (valBuf.length() > 0) {
			valBuf.delete(valBuf.length() - 1, valBuf.length());
			fieldBuf.delete(fieldBuf.length() - 1, fieldBuf.length());
		}

		return "INSERT INTO " + sclass.getSimpleName()
				+ String.format("(%s) VALUES(%s)", fieldBuf.toString(), valBuf.toString());
	}

	/**
	 * 自动根基obj设置boundstatement的参数
	 * 
	 * @param bounder
	 * @param obj
	 */
	public static void genBound(PrepareStatementBounder bounder, Object obj) {
		Class sclass = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			bounder.process(obj, field);
		}
	}

	public static String toJSON(Row row) {
		JSONObject obj = new JSONObject();
		ColumnDefinitions defs = row.getColumnDefinitions();
		Iterator<Definition> iter = defs.iterator();
		while (iter.hasNext()) {
			Definition def = iter.next();
			try {
				if (def.getType() == DataType.text() || def.getType() == DataType.varchar()) {
					obj.put(def.getName(), row.getString(def.getName()));
				} else if (def.getType() == DataType.cboolean()) {
					obj.put(def.getName(), row.getBool(def.getName()));
				} else if (def.getType() == DataType.bigint()) {
					obj.put(def.getName(), row.getLong(def.getName()));
				} else if (def.getType() == DataType.cint()) {
					obj.put(def.getName(), row.getInt(def.getName()));
				} else {
					System.out.println("error" + def.getType());
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return obj.toString();
	}

	public static void main(String[] args) throws WeiboException, weibo4j.model.WeiboException {
		System.out.println(genPrepareStatement(Status.class, new StatusFieldInSpector()));
		System.out.println(genPrepareStatement(User.class, new StatusFieldInSpector()));
	}
}
