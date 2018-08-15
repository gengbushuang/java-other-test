package com.jemalloc.handler;

public class HandlerContext {

	volatile HandlerContext next;
	volatile HandlerContext prev;

	private Handler handler;

	private HandlerPipeline pipeline;

	private String name;

	public HandlerContext(HandlerPipeline pipeline, String name, Handler handler) {

		this.name = name;

		this.pipeline = pipeline;
		this.handler = handler;
	}

	public Handler handler() {
		return handler;
	}

	public HandlerContext fireChannelRead(final Object msg) {
		invokeChannelRead(findContext(), msg);
		return this;
	}

	private HandlerContext findContext() {
		HandlerContext ctx = this;
		ctx = ctx.next;
		return ctx;
	}

	static void invokeChannelRead(final HandlerContext next, Object msg) {
		next.invokeChannelRead(msg);
	}

	private void invokeChannelRead(Object msg) {
		handler().channelRead(this, msg);
	}
}
