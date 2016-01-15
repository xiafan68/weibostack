package weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tool {

	public static boolean write(String filepath, String str) {
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, true);
			osw = new OutputStreamWriter(fileos, "GBK");
			bw = new BufferedWriter(osw);
			if (!str.equals("")) {
				bw.append(str);
				bw.newLine();
			}
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean write(String filepath, List<String> list) {
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, true);
			osw = new OutputStreamWriter(fileos, "GBK");
			bw = new BufferedWriter(osw);
			for (String s : list) {
				if (!s.equals("")) {
					bw.append(s);
					bw.newLine();
				}
			}
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean write(String filepath, List<String> list, boolean isAppend, String encode) {
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, isAppend);
			osw = new OutputStreamWriter(fileos, encode);
			bw = new BufferedWriter(osw);
			for (String s : list) {
				bw.append(s);
				bw.newLine();
			}
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean write(String filepath, Set<String> list, boolean isAppend, String encode) {
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, isAppend);
			osw = new OutputStreamWriter(fileos, encode);
			bw = new BufferedWriter(osw);
			for (String s : list) {
				bw.append(s);
				bw.newLine();
			}
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean write(String filepath, String str, boolean isAppend, String encode) {
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, isAppend);
			osw = new OutputStreamWriter(fileos, encode);
			bw = new BufferedWriter(osw);
			bw.append(str);
			bw.newLine();
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean write(String filepath, Map resultMap) {
		Iterator iterator = resultMap.entrySet().iterator();
		ArrayList<String> recordList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry<String, Long>) iterator.next();
			resultList.add(entry.getKey() + "\t" + entry.getValue());
			// write(".\\data\\twitterHashtag",key+"\t"+value);
		}
		OutputStreamWriter osw = null;
		FileOutputStream fileos = null;
		BufferedWriter bw = null;
		try {
			fileos = new FileOutputStream(filepath, true);
			osw = new OutputStreamWriter(fileos, "GBK");
			bw = new BufferedWriter(osw);
			for (String s : resultList) {
				if (!s.equals("")) {
					bw.append(s);
					bw.newLine();
				}
			}
			bw.close();
			osw.close();
			fileos.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static String removeEol(String text) {
		if (text == null) {
			return text;
		}
		if (text.contains("\n")) {
			text = text.replaceAll("\n", "");
		}
		if (text.contains("\r")) {
			text = text.replaceAll("\r", "");
		}
		if (text.contains("\n\r")) {
			text = text.replaceAll("\n", "\n\r");
		}
		if (text.contains("\r\n")) {
			text = text.replaceAll("\r\n", "");
		}
		return text;
	}

	public static boolean deleteFile(File file) {
		try {
			if (file.exists()) {
				file.delete();
				System.out.println("delete：" + file.getName());
				return true;
			}
		} catch (Exception e) {
			System.out.println("delete failed：" + file.getName());
			e.printStackTrace();
		}
		return false;
	}

	public static List<String> readFile(String file, String encode) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
		String line = null;
		List<String> ret = new ArrayList<String>();
		while (null != (line = reader.readLine())) {
			ret.add(line);
		}
		reader.close();
		return ret;
	}
}
