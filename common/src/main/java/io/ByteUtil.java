package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

public class ByteUtil {
	private ByteUtil() {

	}

	/**
	 * 从末尾先前数的最后一个非0的位置，位置从0开始
	 * 
	 * @param num
	 * @return
	 */
	public static int lastNonZeroBitFromTail(int num) {
		int endBits = -1;
		while (num != 0) {
			num = num >>> 1;
			if (num != 0)
				endBits++;
		}
		return endBits;
	}

	/**
	 * find the number of common bits
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int commonBitNum(int a, int b) {
		int i = 0;
		int mask = 1 << 31;
		for (; i < 32; i++) {
			if ((a & mask) == (b & mask)) {
				mask >>>= 1;
			} else {
				break;
			}
		}
		return i;
	}

	/**
	 * find the number of common bits
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int headBit(int a, int num) {
		int ret = 0;
		int mask = 1 << 31;
		for (int i = 0; i < num; i++) {
			ret |= mask & a;
			mask >>>= 1;
		}
		ret |= mask;
		return ret;
	}

	/**
	 * return the head bitNum bits of number
	 * 
	 * @param number
	 * @param bitNum
	 * @return
	 */
	public static int fetchHeadBits(int number, int bitNum) {
		int ret = 0;
		int mask = 1 << 31;
		for (int i = 0; i < bitNum; i++) {
			ret |= number & mask;
			mask >>>= 1;
		}
		return ret;
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

	public static int getInt(byte[] b, int start) {
		int targets = (b[start] & 0xff) | ((b[start + 1] << 8) & 0xff00) // |
																			// 表示安位或
				| (b[start + 2] << 24) >>> 8 | (b[start + 3] << 24);
		return targets;
	}

	public static byte[] long2byte(long res) {
		byte[] targets = new byte[8];
		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) ((res >> 24) & 0xff);// 最高位,无符号右移。
		targets[4] = (byte) ((res >> 32) & 0xff);// 次低位
		targets[5] = (byte) ((res >> 40) & 0xff);// 次高位
		targets[6] = (byte) ((res >> 48) & 0xff);
		targets[7] = (byte) (res >>> 56);// 最高位,无符号右移。
		return targets;
	}

	public static byte[] long2byte(byte[] targets, int start, long res) {
		targets[start + 0] = (byte) (res & 0xff);// 最低位
		targets[start + 1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[start + 2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[start + 3] = (byte) ((res >> 24) & 0xff);// 最高位,无符号右移。
		targets[start + 4] = (byte) ((res >> 32) & 0xff);// 次低位
		targets[start + 5] = (byte) ((res >> 40) & 0xff);// 次高位
		targets[start + 6] = (byte) ((res >> 48) & 0xff);
		targets[start + 7] = (byte) (res >>> 56);// 最高位,无符号右移。
		return targets;
	}

	public static long getLong(byte[] b, int start) {
		long targets = 0;
		for (int i = 0; i < 8; i++) {
			targets |= ((long) b[start + i] & 0xff) << (8 * i);
		}
		/*
		 * long targets = (b[start] & 0xffl) | ((long)(b[start + 1] << 8) &
		 * 0xff00l) // | | ((b[start + 2] << 16) & 0xff0000l) | ((long)(b[start
		 * + 3] << 24) & 0xff000000l) | ((long)(b[start + 4] << 32) &
		 * 0xff00000000l) | ((long)(b[start + 5] << 40) & 0xff0000000000l) |
		 * ((long)((b[start + 6] << 48) >>> 8) & 0xff000000000000l) |
		 * ((long)(b[start + 7] << 56) & 0xff00000000000000l);
		 */
		return targets;
	}

	public static int writeInt(int val, byte[] data, int offset) {
		for (int i = 0; i < 4; i++) {
			data[offset + i] = (byte) ((val >> (8 * (3 - i))) & 0xff);
		}
		return offset + 4;
	}

	/**
	 * 使用一个byte的最高位指定当前byte是否为最后一个字节，0表示是最后一个
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] writeVInt(int i) {
		byte[] ret = null;
		if (i < (1 << 7)) {
			ret = new byte[1];
			ret[0] = (byte) (i & 0x7f);
		} else if (i < (1 << 14)) {
			ret = new byte[2];
			ret[1] = (byte) (i & 0x7f);
			ret[0] = (byte) (i >> 7 | 0x80);
		} else if (i < (1 << 21)) {
			ret = new byte[3];
			ret[2] = (byte) (i & 0x7f);
			ret[1] = (byte) (i >> 7 | 0x80);
			ret[0] = (byte) (i >> 14 | 0x80);
		} else {
			ret = new byte[4];
			ret[3] = (byte) (i & 0x7f);
			ret[2] = (byte) (i >> 7 | 0x80);
			ret[1] = (byte) (i >> 14 | 0x80);
			ret[0] = (byte) (i >> 21 | 0x80);
		}
		return ret;
	}

	/**
	 * 使用一个byte的最高位指定当前byte是否为最后一个字节，0表示是最后一个
	 * 
	 * @param i
	 * @return
	 */
	public static void writeVInt(DataOutput output, int i) {
		try {
			if (i < (1 << 7)) {
				output.writeByte((byte) (i & 0x7f));
			} else if (i < (1 << 14)) {
				output.writeByte((byte) (i >> 7 | 0x80));
				output.writeByte((byte) (i & 0x7f));
			} else if (i < (1 << 21)) {
				output.writeByte((byte) (i >> 14 | 0x80));
				output.writeByte((byte) (i >> 7 | 0x80));
				output.writeByte((byte) (i & 0x7f));
			} else {
				output.writeByte((byte) (i >> 21 | 0x80));
				output.writeByte((byte) (i >> 14 | 0x80));
				output.writeByte((byte) (i >> 7 | 0x80));
				output.writeByte((byte) (i & 0x7f));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用一个byte的最高位指定当前byte是否为最后一个字节，0表示是最后一个
	 * 
	 * @param i
	 * @return
	 */
	public static int readVInt(byte[] data, int offset) {
		int ret = 0;
		byte cur = 0;
		int i = 0;
		do {
			cur = data[offset + i];
			i++;
			ret = (ret << 7) | (cur & 0x7f);
		} while ((cur & (1 << 7)) > 0);
		return ret;
	}

	/**
	 * 使用一个byte的最高位指定当前byte是否为最后一个字节，0表示是最后一个
	 * 
	 * @param i
	 * @return
	 */
	public static int readVInt(DataInput data) {
		int ret = 0;
		byte cur = 0;
		do {
			try {
				cur = data.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret = (ret << 7) | (cur & 0x7f);
		} while ((cur & (1 << 7)) > 0);
		return ret;
	}

	public static int readInt(byte[] bytes, int offset) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			dis.skip(offset);
			return dis.readInt();
		} catch (IOException e) {
		}
		return -1;
	}

	public static long readLong(byte[] bytes, int offset) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				bytes));
		try {
			dis.skip(offset);
			return dis.readLong();
		} catch (IOException e) {
		}
		return -1;
	}

	public static byte[] longToBytes(long number) {
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bis);
		try {
			dos.writeLong(number);
		} catch (IOException e) {

		}
		return bis.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 10; i++) {
			byte[] data = writeVInt(i);
			System.out.println(readVInt(data, 0));
		}

		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			ints.add(i * 100);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		ByteUtil.writeVInt(dos, 0);
		dos.writeLong(1);
		writeVInt(dos, 1);

		DataInputStream input = new DataInputStream(new ByteArrayInputStream(
				bos.toByteArray()));
		ByteUtil.readVInt(input);
		input.readLong();
		ByteUtil.readVInt(input);

		verify(696320);
	}

	private static void verify(int data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		ByteUtil.writeVInt(dos, data);
		System.out.println(Arrays.toString(bos.toByteArray()));
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(
				bos.toByteArray()));
		Assert.assertEquals(data, ByteUtil.readVInt(input));
	}
}
