package com.jmx.manage;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;


public class ManggerTest {
	static final float M = 1024*1024;
	
	final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	
	 final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	 
	private void getMemoryUsage() {
		MemoryUsage memNonHeap = memoryMXBean.getNonHeapMemoryUsage();
		MemoryUsage memHeap = memoryMXBean.getHeapMemoryUsage();
		Runtime runtime = Runtime.getRuntime();
		System.out.println(("Non-heap memory used in MB "+memNonHeap.getUsed() / M));
		System.out.println(("Non-heap memory committed in MB "+memNonHeap.getCommitted() / M));
		System.out.println(("Non-heap memory max in MB "+memNonHeap.getMax() / M));
		System.out.println(("Heap memory used in MB " +memHeap.getUsed() / M));
		System.out.println(("Heap memory committed in MB "+memHeap.getCommitted() / M));
		System.out.println(("Heap memory max in MB " +memHeap.getMax() / M));
		System.out.println(("Max memory size in M "+runtime.maxMemory() / M));
	}
	
	private void getThreadUsage() {
		int threadsNew = 0;
		int threadsRunnable = 0;
		int threadsBlocked = 0;
		int threadsWaiting = 0;
		int threadsTimedWaiting = 0;
		int threadsTerminated = 0;
		long threadIds[] = threadMXBean.getAllThreadIds();
		for (ThreadInfo threadInfo : threadMXBean.getThreadInfo(threadIds, 0)) {
			if (threadInfo == null) {
				continue;
			} // race protection
			System.out.println(threadInfo.getThreadState());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ManggerTest m = new ManggerTest();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				final List l = new ArrayList();
				while(true){
//					System.out.println(System.currentTimeMillis());
//					try {
//						Thread.sleep(300);
//					} catch (Exception e) {
//						e.printStackTrace();
//					};
				}
			}
		};
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();
		
		for(int i=0;i<6;i++){
			Thread t1 = new Thread(r);
			t1.setDaemon(true);
			t1.start();
		System.out.println("--------------------");
		Thread.sleep(2000);
		m.getThreadUsage();
		System.out.println("===================");
		m.getMemoryUsage();
		}
		System.out.println("--------------------");
		m.getThreadUsage();
		System.out.println("===================");
		m.getMemoryUsage();
	}
}

