package dase.perf;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提供基于jmx的远程控制功能，主要是控制服务器的安全重启之类的操作。
 * 
 * @author xiafan
 *
 */
public class ServerController implements ServerControllerMBean {
	private AtomicInteger runningOp = new AtomicInteger(0);
	private SERVER_STATUS status = SERVER_STATUS.bootstrap;
	IServerSubscriber server;

	public static interface IServerSubscriber {
		public void onStop();

		public void onSuspend();

		public void onResume();
	}

	public ServerController(IServerSubscriber server) {
		this.server = server;
	}

	public void bootstrap() {
		status = SERVER_STATUS.bootstrap;
	}

	public void running() {
		status = SERVER_STATUS.running;
	}

	public void mark() {
		runningOp.incrementAndGet();
	}

	public void unMark() {
		runningOp.decrementAndGet();
	}

	public String getType() {
		return server.getClass().getName();
	}

	public String getName() {
		return Integer.toString(getPid()) + "_" + Thread.currentThread().getId();
	}

	@Override
	public String getStatus() {
		return status.toString();
	}

	@Override
	public void suspendServer() {
		if (status == SERVER_STATUS.running) {
			status = SERVER_STATUS.suspending;
			waitingOpsToFinish();
			server.onSuspend();
			status = SERVER_STATUS.suspend;
		}
	}

	@Override
	public void resumeServer() {
		if (status == SERVER_STATUS.suspend) {
			server.onResume();
			status = SERVER_STATUS.running;
		}
	}

	@Override
	public void stopServer() {
		if (status == SERVER_STATUS.stoping || status == SERVER_STATUS.termianted) {
			return;
		} else {
			status = SERVER_STATUS.stoping;
			waitingOpsToFinish();
			server.onStop();
			status = SERVER_STATUS.termianted;
		}
	}

	private void waitingOpsToFinish() {
		while (status == SERVER_STATUS.running && runningOp.get() != 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public int getPid() {
		return MetricBasedPerfProfile.getPid();
	}
}
