package weibo;

/**
 * 不确定性对象，两个对象通过equals方法比较可能相同，但是它们中可能某一个包含更加全面的信息，这个时候就需要进行合并
 * @author xiafan
 *
 */
public interface UncertainObject{
	/**
	 * 两个对象是否确定相同
	 * @param other
	 * @return
	 * 			
	 */
	public boolean certain();

	/**
	 * 合并另外一个对象的信息
	 * @param other
	 * @return
	 */
	public void reconcile(UncertainObject other);
}
