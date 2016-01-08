package dase.perf;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.ganglia.GangliaReporter;

import info.ganglia.gmetric4j.gmetric.GMetric;
import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode;

public class MetricBasedPerfProfile {
	public static final String GROUP_NAME = "weibostack";
	private static final MetricRegistry metrics = new MetricRegistry();
	private static final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	public static Timer timer(String name) {
		return metrics.timer(qualifiedName(name));
	}

	public static Meter meter(String name) {
		return metrics.meter(qualifiedName(name));
	}

	public static String qualifiedName(String name) {
		return String.format("%s.%s", GROUP_NAME, name);
	}

	public static int getPid() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		System.out.println("当前进程的标识为：" + name);
		int index = name.indexOf("@");
		int pid = -1;
		if (index != -1) {
			pid = Integer.parseInt(name.substring(0, index));
		}
		return pid;
	}

	public static void registerServer(ServerController controller) throws Exception {
		registerMBean(controller, controller.getType(), controller.getName());
	}

	private static void registerMBean(Object obj, String type, String objName) throws Exception {
		mbs.registerMBean(obj, new ObjectName("dase:type=" + type + ",name=" + objName));
	}

	/**
	 * 
	 * @param host
	 *            "ganglia.example.com"
	 * @param port
	 *            8649
	 * @throws IOException
	 */
	public static void reportForGanglia(String host, short port) throws IOException {
		final GMetric ganglia = new GMetric(host, port, UDPAddressingMode.MULTICAST, 1);
		final GangliaReporter reporter = GangliaReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build(ganglia);
		reporter.start(1, TimeUnit.MINUTES);
	}

	public static void reportForHttp() {

	}
}
