package segmentation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.WeiboException;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;

public class LocalSegDriver {
	public static void main(String[] args) throws IOException, WeiboException,
			weibo4j.model.WeiboException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"/Users/xiafan/Documents/dataset/microblog/part-8"),
				"utf-8"));
		String line = null;
		try {
			while (null != (line = dis.readLine())) {
				StatusWapper wrapper = Status.constructWapperStatus(line);
				// List<Status> statuses = Status.constructStatuses(line);
				for (Status status : wrapper.getStatuses()) {
					System.out.println("user:" + status.getUser()
							+ "; content:" + status.getText() + "; retweet:"
							+ status.getRetweetedStatus());
				}
			}
		} finally {
			dis.close();
		}
	}
}
