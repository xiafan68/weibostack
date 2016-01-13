package io;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DirReader extends Reader {
	File[] files;
	int cIdx = 0;
	BufferedReader cReader = null;

	public DirReader(String dir) throws IOException {
		files = FileUtil.listDirOrFile(dir);
		if (files.length == 0)
			throw new IOException("no files contained in dir" + dir);
		cReader = new BufferedReader(new InputStreamReader(new FileInputStream(
				files[cIdx]), "utf-8"));
	}

	@Override
	public void close() throws IOException {
		if (cReader != null)
			cReader.close();
	}

	@Override
	public int read(char[] arg0, int arg1, int arg2) throws IOException {
		if (cIdx >= files.length)
			throw new EOFException();
		int offset = arg1;
		int len = arg2;
		do {
			int count = cReader.read(arg0, offset, len);
			offset += count;
			len -= count;
			if (offset < arg1 + arg2 && cIdx < files.length - 1) {
				arg0[offset++] = '\r';
				arg0[offset++] = '\n';
				len -= 2;
				cReader.close();
				cReader = new BufferedReader(new InputStreamReader(
						new FileInputStream(files[++cIdx]), "utf-8"));
			} else {
				break;
			}
		} while (true);
		return offset - arg1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new DirReader(
				"/Users/xiafan/Documents/dataset/microblog/EventData0903"));
		String line = null;
		int count = 0;
		while (null != (line = reader.readLine())) {
			if (count++ % 10000 == 0)
				System.out.println(line);
		}
		System.out.println(count);
	}
}
