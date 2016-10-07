package com.jmx.manage.test;

/**
 * This interface represents a source counter mbean. Any class implementing
 * this interface must sub-class
 * {@linkplain com.jmx.manage.test.apache.flume.instrumentation.MonitoredCounterGroup}. This
 * interface might change between minor releases. Please see
 * {@linkplain org.apache.flume.instrumentation.SourceCounter} class.
 */
public interface SourceCounterMBean {

  long getEventReceivedCount();

  long getEventAcceptedCount();

  long getAppendReceivedCount();

  long getAppendAcceptedCount();

  long getAppendBatchReceivedCount();

  long getAppendBatchAcceptedCount();

  long getStartTime();

  long getStopTime();

  String getType();

  long getOpenConnectionCount();
}
