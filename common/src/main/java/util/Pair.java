package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Pair<T1, T2> implements Serializable {
	public T1 arg0;
	public T2 arg1;

	public Pair() {

	}

	public Pair(T1 start, T2 end) {
		arg0 = start;
		arg1 = end;
	}

	@Override
	public int hashCode() {
		return arg0.hashCode() + arg1.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			return arg0.equals(((Pair<T1, T2>) other).arg0)
					&& arg1.equals(((Pair<T1, T2>) other).arg1);
		}
		return false;
	}

	@Override
	public String toString() {
		return arg0 + "_" + arg1;
	}

	public void write(DataOutput output) throws IOException {
		output.write(String
				.format("%s\t%s\n", arg0.toString(), arg1.toString()).getBytes(
						"UTF-8"));
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		HashMap<Integer, Pair<Integer, Integer>> test = new HashMap<Integer, Pair<Integer, Integer>>();
		test.put(1, new Pair<Integer, Integer>(1, 1));
		ByteArrayOutputStream aos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(aos);
		oos.writeObject(test);
		ByteArrayInputStream ais = new ByteArrayInputStream(aos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(ais);
		HashMap<Integer, Pair<Integer, Integer>> answer = (HashMap<Integer, Pair<Integer, Integer>>) ois
				.readObject();
		System.out.println(answer);
	}
}
