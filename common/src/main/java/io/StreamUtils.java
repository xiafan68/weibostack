package io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class StreamUtils {
	public static OutputStream outputStream(String path)
			throws FileNotFoundException {
		return new BufferedOutputStream(new DataOutputStream(
				new FileOutputStream(path)));
	}

	public static OutputStream outputStream(File path)
			throws FileNotFoundException {
		return new BufferedOutputStream(new DataOutputStream(
				new FileOutputStream(path)));
	}
}
