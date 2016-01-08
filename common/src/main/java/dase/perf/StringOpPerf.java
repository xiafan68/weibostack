package dase.perf;

import java.util.ArrayList;
import java.util.Random;

/**
 * 学习StringBuffer和String直接用+法操作的性能差别
 * @author xiafan
 *
 */
public class StringOpPerf {

	public static void main(String[] args) {
		int height = 1000;
		int width = 10;
		int testLoop = 1000;

		ArrayList[] matrix = new ArrayList[height];

		// generate data
		Random rand = new Random();
		for (int i = 0; i < height; i++) {
			matrix[i] = new ArrayList();
			for (int j = 0; j < width; j++) {
				matrix[i].add(rand.nextInt() % 20);
			}
		}

		// test
		long start = System.currentTimeMillis();
		for (int i = 0; i < testLoop; i++) {
			for (int j = 0; j < height; j++) {
				StringBuffer tmp = new StringBuffer();
				for (int k = 0; k < width; k++) {
					tmp.append("\t" + matrix[j].get(k));
				}
				tmp.toString().split("\t");
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time cost:" + (end - start) / 1000);

		// test
		start = System.currentTimeMillis();
		for (int i = 0; i < testLoop * 1000; i++) {
			String tmp = "asd" + "sdf" + "sdf" + "dfg" + "fgh" + "ghj" + "hjk"
					+ "fgh" + "789" + "567" + "345" + "234" + "123";
		}
		end = System.currentTimeMillis();
		System.out.println("time cost:" + (end - start));
	}
}
