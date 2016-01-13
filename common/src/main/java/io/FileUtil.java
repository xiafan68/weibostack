package io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class FileUtil {
	public static void mkdirs(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	public static void markDel(File file) throws IOException {
		File pDir = file.getParentFile();
		String name = FilenameUtils.getBaseName(file.getName());
		File delFile = new File(pDir, name + ".del");
		System.out.println(name + "\n<" + delFile);
		delFile.createNewFile();
	}

	public static void delete(String path) {
		delete(new File(path));
	}

	public static void delete(File file) {
		if (file.isDirectory() && !Files.isSymbolicLink(file.toPath())) {
			File[] childs = file.listFiles();
			for (File child : childs) {
				delete(child);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	public static void emptyDir(String dataDir) {
		File file = new File(dataDir);
		if (file.isDirectory()) {
			File[] childs = file.listFiles();
			for (File child : childs) {
				delete(child);
			}
		}
	}

	/**
	 * 返回目录path下面所有的文件,如果path本身是文件，那么返回它本身
	 * 
	 * @param path
	 * @return
	 */
	public static File[] listDirOrFile(String path) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			return listDirFilter(path, "^[^.].*[^~]$");
		} else {
			return new File[] { dir };
		}
	}

	/**
	 * 返回目录path下面所有的文件
	 * 
	 * @param path
	 * @return
	 */
	public static File[] listDir(String path) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			return dir.listFiles();
		} else
			return new File[0];
	}

	public static File[] listDirFilter(String path, final String pattern) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			NameFileComparator comp = new NameFileComparator();
			RegexFileFilter filter = new RegexFileFilter(pattern);
			List<File> dirFiles = comp.sort(new ArrayList<File>(FileUtils
					.listFiles(dir, filter, FalseFileFilter.INSTANCE)));
			File[] ret = new File[dirFiles.size()];
			dirFiles.toArray(ret);
			return ret;
		} else
			return new File[0];
	}

	public static long filesSize(File[] files) {
		long ret = 0;
		for (File file : files) {
			if (file.isFile())
				ret += file.length();
		}
		return ret;
	}

	public static DataOutputStream openDos(String file)
			throws FileNotFoundException {
		return new DataOutputStream(new FileOutputStream(file));
	}

	public static void main(String[] args) throws IOException {

		markDel(new File("/Users/xiafan/Documents/lsmodata/datadir/0_0.data"));
	}

	private static void test() throws IOException {
		// test whether directory can be deleted
		File test = new File("testdir");
		test.mkdir();
		for (int i = 0; i < 3; i++) {
			File child = new File(test, "child" + i);
			child.createNewFile();
		}

		FileUtil.delete(test);
		test = new File("testdir");
		System.out.println("should be false after delete:" + test.exists());
		for (File file : FileUtil
				.listDirOrFile("/home/xiafan/dataset/twitter/twitter_postings"))
			System.out.println(file);
	}
}
