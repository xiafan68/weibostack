package weibo4j.model;

import net.sf.json.JSONObject;

public class Source implements java.io.Serializable {

	private static final long serialVersionUID = -8972443458374235866L;
	private String url; // 来源连接
	private String relationShip;
	private String name; // 来源文案名称

	public Source() {

	}

	public Source(String str) {
		super();
		String[] source = str.split("\"", 5);
		url = source[1];
		relationShip = source[3];
		name = source[4].replace(">", "").replace("</a", "");
	}

	public Source(JSONObject obj) {
		super();
		url = obj.getString("url");
		if (obj.containsKey("relationship"))
			relationShip = obj.getString("relationship");
		else
			relationShip = obj.getString("relationShip");
		name = obj.getString("name");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRelationship() {
		return relationShip;
	}

	public void setRelationship(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "<a herf=\\\"" + url + "\\\"" + " rel=\\\"" + relationShip + "\\\">" + name + "</a>";
	}

}
