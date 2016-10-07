package com.jmx.manage;


public enum JvmMetricsInfo {

	JvmMetrics("JVM related metrics etc."), // record info
	  // metrics
	  MemNonHeapUsedM("Non-heap memory used in MB"),
	  MemNonHeapCommittedM("Non-heap memory committed in MB"),
	  MemNonHeapMaxM("Non-heap memory max in MB"),
	  MemHeapUsedM("Heap memory used in MB"),
	  MemHeapCommittedM("Heap memory committed in MB"),
	  MemHeapMaxM("Heap memory max in MB"),
	  MemMaxM("Max memory size in MB"),
	  GcCount("Total GC count"),
	  GcTimeMillis("Total GC time in milliseconds"),
	  ThreadsNew("Number of new threads"),
	  ThreadsRunnable("Number of runnable threads"),
	  ThreadsBlocked("Number of blocked threads"),
	  ThreadsWaiting("Number of waiting threads"),
	  ThreadsTimedWaiting("Number of timed waiting threads"),
	  ThreadsTerminated("Number of terminated threads"),
	  LogFatal("Total number of fatal log events"),
	  LogError("Total number of error log events"),
	  LogWarn("Total number of warning log events"),
	  LogInfo("Total number of info log events");

	  private final String desc;

	  JvmMetricsInfo(String desc) { this.desc = desc; }

	  public String description() { return desc; }

	// public String toString() {
	// return Objects.toStringHelper(this)
	// .add("name", name()).add("description", desc)
	// .toString();
	// }
}
