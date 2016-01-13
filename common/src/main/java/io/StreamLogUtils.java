package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

public class StreamLogUtils {
	private static final String ENCODING = "utf8";

	public static void log(OutputStream output, String log)
			throws UnsupportedEncodingException, IOException {
		output.write(log.getBytes(ENCODING));
		output.write("\n".getBytes(ENCODING));
	}

	public static void log(OutputStream output, String... fields)
			throws UnsupportedEncodingException, IOException {
		output.write(StringUtils.join(fields, "\t").getBytes(ENCODING));
		output.write("\n".getBytes(ENCODING));
	}
}
