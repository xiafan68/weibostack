package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class DirLineReader implements Iterable<String> {
	File[] files;
	int cIdx = 0;
	BufferedReader cReader = null;
	FileInputStream curIS = null;

	public DirLineReader(String dir) throws IOException {
		files = FileUtil.listDirOrFile(dir);
		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File arg0, File arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
		if (files.length == 0)
			throw new IOException("no files contained in dir" + dir);
		cReader = new BufferedReader(new InputStreamReader(new FileInputStream(files[cIdx]), "utf-8"));
	}

	String line = null;

	public boolean hasNext() {
		if (peek() != null)
			return true;
		return false;
	}

	public String peek() {
		if (line != null) {
			return line;
		} else {
			do {
				try {
					line = cReader.readLine();
					if (line != null)
						break;
					else {
						if (cIdx < files.length - 1) {
							cReader.close();
							curIS = new FileInputStream(files[++cIdx]);
							cReader = new BufferedReader(new InputStreamReader(curIS, "utf-8"));
						} else
							break;
					}
				} catch (IOException e) {
				}
			} while (line == null);
		}
		return line;
	}

	public String readLine() {
		String ret = peek();
		line = null;
		return ret;
	}

	public void close() throws IOException {
		cReader.close();
	}

	public static void main(String[] args) throws IOException {
		DirLineReader reader = new DirLineReader("/home/xiafan/Documents/dataset/expr/2012tweet_seg");
		String line = null;
		int count = 0;
		while (null != (line = reader.readLine())) {
			count++;
			// System.out.println(line);
		}
		System.out.println(count);
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			@Override
			public boolean hasNext() {
				return DirLineReader.this.hasNext();
			}

			@Override
			public String next() {
				return readLine();
			}

			@Override
			public void remove() {
				throw new RuntimeException("remove is not implemented for DirLineReader");
			}
		};
	}
}
