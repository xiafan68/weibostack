package shingle;

import java.io.IOException;
import java.util.List;

public interface ITextShingle {
	/**
	 * 分词
	 * 
	 * @param text
	 * @param isQuery
	 * @return
	 * @throws IOException
	 */
	public List<String> shingling(String text, boolean isQuery) throws IOException;
}
