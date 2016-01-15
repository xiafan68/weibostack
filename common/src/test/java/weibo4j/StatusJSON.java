package weibo4j;

import org.junit.Test;

import weibo4j.model.Status;
import weibo4j.model.User;

public class StatusJSON {
	@Test
	public void test() {

		Status status = new Status();
		status.setUser(new User());
		status.setAnnotations("[{'location':'1'}]");
		Status rt = new Status();
		rt.setUser(new User());
		status.setRetweetedStatus(rt);
		System.out.println(StatusSerDer.toJSON(status));
		// System.out.println(JSONObject.toBean(arr.getJSONObject(0),
		// Status.class, jsonConfig));
		System.out.println(StatusSerDer.fromJSON(StatusSerDer.toJSON(status)));
		status = StatusSerDer.fromJSON(StatusSerDer.toJSON(status).toString());
		System.out.println(status.toString());
		System.out.println(status.getCreatedAt());
	}
}
