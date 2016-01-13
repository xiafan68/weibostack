package weibo.rttree;

/**
 * 为无法确定时间戳的微博选择时间的接口类
 * @author xiafan
 *
 */
public interface ITimeStampGenerator {
	/**
	 * 在start和end的区间中选择一个时间
	 * @param start
	 * @param end
	 * @return
	 */
	public long chooseTs(long start, long end);
}
