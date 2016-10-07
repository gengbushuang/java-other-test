package com.jmx.manage.test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.management.OperatingSystemMXBean;

public class ManageUtils {

	private static MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

	public static void aaa() {
		Set<ObjectInstance> queryMBeans = null;
		try {
			queryMBeans = mbeanServer.queryMBeans(null, null);
		} catch (Exception ex) {
		}
		System.out.println(queryMBeans);
		for (ObjectInstance obj : queryMBeans) {
			System.out.println(obj.getObjectName().toString());
		}
	}

	public static void http() throws IOException, InstanceNotFoundException, IntrospectionException, ReflectionException {
		String host = "localhost"; // or some A.B.C.D
		int port = 1234;
		String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
		JMXServiceURL serviceUrl = new JMXServiceURL(url);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceUrl, null);
		try {
			MBeanServerConnection mbeanConn = jmxConnector.getMBeanServerConnection();
			// now query to get the beans or whatever
			Set<ObjectName> beanSet = mbeanConn.queryNames(null, null);
			for (ObjectName obj : beanSet) {
				// System.out.println(obj.getDomain());
				if (!obj.getDomain().toString().startsWith("com.geng")) {
					continue;
				}

				MBeanAttributeInfo[] attrs = mbeanConn.getMBeanInfo(obj).getAttributes();
				String strAtts[] = new String[attrs.length];
				for (int i = 0; i < strAtts.length; i++) {
					strAtts[i] = attrs[i].getName();
					System.out.println(strAtts[i]);
				}

				AttributeList attrList = mbeanConn.getAttributes(obj, strAtts);
				String component = obj.toString().substring(obj.toString().indexOf('=') + 1);
				for (Object attr : attrList) {
					Attribute localAttr = (Attribute) attr;
					if (localAttr.getName().equalsIgnoreCase("type")) {
						component = localAttr.getValue() + "." + component;
					}
					System.out.println(localAttr.getName() + "," + localAttr.getValue().toString());
				}
			}
		} finally {
			jmxConnector.close();
		}
	}

	public static void main(String[] args) throws IOException, InstanceNotFoundException, IntrospectionException, ReflectionException, MalformedObjectNameException, AttributeNotFoundException, MBeanException {
		String host = "localhost"; // or some A.B.C.D
		int port = 1234;
		String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
		JMXServiceURL serviceUrl = new JMXServiceURL(url);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceUrl, null);
		MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

		ObjectName managerObjName = new ObjectName("java.lang:type=Threading");
		Set<ObjectName> s =  mbsc.queryNames(managerObjName, null);
		for (ObjectName obj : s) {
			System.out.println(obj.getCanonicalName());
			
			MBeanAttributeInfo[] attrs = mbsc.getMBeanInfo(obj).getAttributes();
			String strAtts[] = new String[attrs.length];
			for (int i = 0; i < strAtts.length; i++) {
				strAtts[i] = attrs[i].getName();
//				System.out.println(strAtts[i]);
			}
			AttributeList attrList = mbsc.getAttributes(obj, strAtts);
			String component = obj.toString().substring(obj.toString().indexOf('=') + 1);
			for (Object attr : attrList) {
				Attribute localAttr = (Attribute) attr;
				if (localAttr.getName().equalsIgnoreCase("type")) {
					component = localAttr.getValue() + "." + component;
				}
				System.out.println(localAttr.getName() + "," + localAttr.getValue().toString());
			}
			
//			ObjectName objname = new ObjectName(obj.getCanonicalName());
//			System.out.print("objectName:" + objname);
//			System.out.print(",最大会话数:" + mbsc.getAttribute(objname, "maxActiveSessions") + ",");
//			System.out.print("会话数:" + mbsc.getAttribute(objname, "activeSessions") + ",");
//			System.out.println("活动会话数:" + mbsc.getAttribute(objname, "sessionCounter"));
		}
		// MemoryMXBean memBean = ManagementFactory.newPlatformMXBeanProxy(mbs,
		// ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
		// // 获取远程opretingsystemmxbean
		// com.sun.management.OperatingSystemMXBean opMXbean =
		// ManagementFactory.newPlatformMXBeanProxy(mbs,
		// ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
		// OperatingSystemMXBean.class);
		// while(true){
		// Long start = System.currentTimeMillis();
		// long startT = opMXbean.getProcessCpuTime();
		// /** Collect data every 5 seconds */
		// try {
		// TimeUnit.SECONDS.sleep(5);
		// } catch (InterruptedException e) {
		// }
		// Long end = System.currentTimeMillis();
		// long endT = opMXbean.getProcessCpuTime();
		// //end - start 即为当前采集的时间单元，单位ms
		// //endT - startT 为当前时间单元内cpu使用的时间，单位为ns
		// //所以：double ratio =
		// (entT-startT)/1000000.0/(end-start)/opMXbean.getAvailableProcessors()
		// double ratio =
		// (endT-startT)/1000000.0/(end-start)/opMXbean.getAvailableProcessors();
		// System.out.println(ratio);
		// }
		// try {
		// TimeUnit.SECONDS.sleep(5);
		// } catch (InterruptedException e) {
		// }
		//
		// MemoryUsage heap = memBean
		// etHeapMemoryUsage();
		// MemoryUsage nonHeap = memBean
		// etNonHeapMemoryUsage();
		// long heapSizeUsed = heap.getUsed();//堆使用的大小
		// long nonHeapSizeUsed = nonHeap.getUsed();
		// long heapCommitedSize = heap.getCommitted();
		// long nonHeapCommitedSize = nonHeap.getCommitted();
	}
}
