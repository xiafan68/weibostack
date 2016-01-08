package dase.perf;

public interface ServerControllerMBean {
	public enum SERVER_STATUS {
		bootstrap, running, suspending, suspend, stoping, termianted
	};

	/**
	 * 获取当前运行状态，可能的值包括:boostrapping, running, suspend,terminated
	 * 
	 * @return
	 */
	public String getStatus();

	/**
	 * 暂停执行
	 */
	public void suspendServer();

	/**
	 * 继续执行
	 */
	public void resumeServer();

	/**
	 * 停止执行
	 */
	public void stopServer();

	public int getPid();

}
