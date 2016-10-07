package com.jmx;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.List;
import java.util.Properties;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.management.ThreadMXBean;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * 用到tools.jar包
 * 根据pid获取进程类的jmx
 * @author gbs
 *
 */
public class JmxTest {
	
	
	private void init() throws AttachNotSupportedException, IOException{
		List<VirtualMachineDescriptor> list = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : list) {
			System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
		}

		String pid = "722";
		VirtualMachine virtualmachine = VirtualMachine.attach(pid);
		String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
		System.out.println("javaHome-->" + javaHome);
		String agentPath = javaHome + File.separator + "jre" + File.separator + "lib" + File.separator + "management-agent.jar";
		System.out.println("agentPath-->" + agentPath);
		File file = new File(agentPath);
		if (!file.exists()) {
			agentPath = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
			file = new File(agentPath);
			if (!file.exists()) {
				throw new IOException("Management agent not found");
			}
		}
		agentPath = file.getCanonicalPath();
		System.out.println("agentPath-->" + agentPath);
		try {
			virtualmachine.loadAgent(agentPath, "com.sun.management.jmxremote");
		} catch (AgentLoadException e) {
			throw new IOException(e);
		} catch (AgentInitializationException agentinitializationexception) {
			throw new IOException(agentinitializationexception);
		}
		Properties properties = virtualmachine.getAgentProperties();
		System.out.println("properties-->" + properties);
		String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");
		System.out.println("address-->" + address);
		virtualmachine.detach();
		System.out.println();

		JMXServiceURL url = new JMXServiceURL(address);
		JMXConnector connector = JMXConnectorFactory.connect(url);
		try{
			threadMX(connector);
		}finally{
			connector.close();
		}
	}
	
	public void threadMX(JMXConnector connector) throws IOException{
		MBeanServerConnection mbeanConn = connector.getMBeanServerConnection();
//		java.lang.management.ThreadMXBean thredMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanConn, "java.lang:type=Threading", java.lang.management.ThreadMXBean.class);
		com.sun.management.ThreadMXBean thredMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanConn, "java.lang:type=Threading", com.sun.management.ThreadMXBean.class);
		long []ids = thredMXBean.getAllThreadIds();
		for(long id : ids) {
			System.out.println(id + "\t" + thredMXBean.getThreadAllocatedBytes(id) + "\t" + thredMXBean.getThreadInfo(id));
		}
		System.out.println(thredMXBean.getCurrentThreadCpuTime());
		System.out.println(thredMXBean.getCurrentThreadUserTime());
		System.out.println(thredMXBean.getDaemonThreadCount());
		System.out.println(thredMXBean.getPeakThreadCount());
		System.out.println(thredMXBean.getThreadCount());
		System.out.println(thredMXBean.getTotalStartedThreadCount());
		System.out.println("==========================>");
		displayThreadInfos(thredMXBean , ids);
	}
	private static void displayThreadInfos(ThreadMXBean thredMXBean , long []ids) {
		ThreadInfo []threadInfos = thredMXBean.getThreadInfo(ids);
		for(ThreadInfo thread : threadInfos) {
			System.out.println(thread.getThreadName() + "\t" 
					+ thread.getLockOwnerId() + "\t" + thread.getThreadState() 
					+ "\t" + thread.getBlockedCount() + "\t" + thread.getBlockedTime() );
			
		}
	}
	

	public static void main(String[] args) throws AttachNotSupportedException, IOException, MalformedObjectNameException, InstanceNotFoundException, IntrospectionException, ReflectionException, AttributeNotFoundException, MBeanException {
		
		new JmxTest().init();
		
//			java.lang.management.ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanConn, "java.lang:type=Threading", java.lang.management.ThreadMXBean.class);
//
//			long[] threadIds = threadMXBean.getAllThreadIds();
//			for (long threadId : threadIds) {
//				// 线程的信息
//				ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
//				// 线程被阻塞的数量
//				threadInfo.getBlockedCount();
//				// 被锁定线程的监控信息
//				MonitorInfo[] monitorInfos = threadInfo.getLockedMonitors();
//				for (MonitorInfo monitorInfo : monitorInfos) {
//					int depth = monitorInfo.getLockedStackDepth();
//					System.out.println("锁定的程度：" + depth);
//				}
//				// 异步锁定的信息
//				LockInfo[] lockinfos = threadInfo.getLockedSynchronizers();
//				// 锁定的信息
//				for (LockInfo lockInfo : lockinfos) {
//					System.out.println("锁定类的名称：" + lockInfo.getClassName());
//				}
//				// 线程的名称
//				String threadName = threadInfo.getThreadName();
//				Thread.State state = threadInfo.getThreadState();
//				System.out.println("线程的名称：" + threadName+",线程的信息：" + state.name());
//				
//			}
//			long cpuTime = threadMXBean.getCurrentThreadCpuTime();
//			long curentTime = threadMXBean.getCurrentThreadUserTime();
//			long threadCount = threadMXBean.getDaemonThreadCount();
//			long peakliveThreadCount = threadMXBean.getPeakThreadCount();
//			long threadCounts = threadMXBean.getThreadCount();
//			System.out.println("当前处于live状态的线程总的数量：" + threadCounts);
//			long totalThreadCount = threadMXBean.getTotalStartedThreadCount();
//			System.out.println("JVM 启动之后，总的自动线程数量：" + totalThreadCount);

	}
}
