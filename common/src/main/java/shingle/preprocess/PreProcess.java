package shingle.preprocess;

import java.util.List;

/**
 * 按照某个规则抽出去一些字符串，处理过的原始文本作为list的最后一个返回
 * 
 * @author xiafan
 * 
 */
public interface PreProcess {
	/**
	 * 
	 * @param text
	 * @param retain
	 *            是否需要保留被识别的内容
	 * @return
	 */
	List<String> preProcess(String text, boolean retain);
}
