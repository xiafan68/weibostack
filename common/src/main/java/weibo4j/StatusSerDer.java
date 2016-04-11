package weibo4j;

import java.util.Date;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import weibo4j.model.Source;
import weibo4j.model.Status;
import weibo4j.model.Visible;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class StatusSerDer {
	private static Gson gson = new Gson();

	public static class JsonDateValueProcessor implements JsonValueProcessor {
		public JsonDateValueProcessor() {
			super();
		}

		@Override
		public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
			return process(paramObject);
		}

		@Override
		public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
			return process(paramObject);
		}

		private Object process(Object value) {
			if (value instanceof Date) {
				return ((Date) value).getTime();
			}
			return value == null ? "" : value.toString();
		}

	}

	private final static JsonConfig jsonConfig = new JsonConfig();

	static {
		jsonConfig.setExcludes(new String[] { "retweetedStatus" });
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

	}

	public static String toJSON(Status status) {
		return serByGSON(status);
	}

	public static Status fromJSON(String str) {
		return derByGSON(str);
	}

	private static void setupDefault(Status status) {
		if (status.getCreatedAt() == null)
			status.setCreatedAt(new Date());

		if (status.getSource() == null)
			status.setSource(new Source());
		if (status.getVisible() == null)
			status.setVisible(new Visible());

		if (status.getUser().getCreatedAt() == null) {
			status.getUser().setCreatedAt(new Date());
		}
	}

	/**
	 * json-lib经常包invocation
	 * exception，通过使用valueprocessor可以保证它不会把json格式的String成员变量给解析成jsonobject对象，
	 * 这是由于它fromObject这个函数职能划分不清晰导致的
	 */
	/**
	 * 
	 * @param status
	 * @return
	 */
	public static String serByJSONLib(Status status) {
		JSONArray arr = new JSONArray();
		if (status.getUser() == null) {
			return arr.toString();
		}

		try {
			setupDefault(status);
			arr.add(JSONObject.fromObject(status, jsonConfig));
			if (status.getRetweetedStatus() != null) {
				if (status.getRetweetedStatus().getUser() == null) {
					return arr.toString();
				}
				setupDefault(status.getRetweetedStatus());
				arr.add(JSONObject.fromObject(status.getRetweetedStatus(), jsonConfig));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return arr.toString();
	}

	public static Status derByJSONLib(String str) {
		JSONArray arr = JSONArray.fromObject(str);
		return derByJSONLib_intern(arr);
	}

	public static Status derByJSONLib_intern(JSONArray arr) {
		Status ret = null;
		try {
			if (arr.size() >= 1) {
				ret = new Status(arr.getJSONObject(0));
				if (arr.size() == 2) {
					Status rt = new Status(arr.getJSONObject(1));
					ret.setRetweetedStatus(rt);
				}
			}
		} catch (JSONException | WeiboException e) {
			e.printStackTrace();
		} catch (weibo4j.WeiboException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String serByGSON(Status status) {
		return gson.toJson(status);
	}

	public static Status derByGSON(String json) {
		return gson.fromJson(json, Status.class);
	}

}
