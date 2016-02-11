package shingle;

public class ShingleFactory {
	public static ITextShingle createShingle() {
		 return new IKTextShingle(null);
		//return new ANSJShingle(null);
	}
}
