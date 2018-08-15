package com.jemalloc.handler;


public class HandlerPipeline {


	final HandlerContext head;
	final HandlerContext tail;

	public HandlerPipeline() {
		tail = new TailContext(this);
		head = new HeadContext(this);

		head.next = tail;
		tail.prev = head;
	}

	public HandlerPipeline addFirst(String name, Handler handler) {
		final HandlerContext newCtx;

		newCtx = newContext(name, handler);

		addFirst0(newCtx);
		return this;
	}

	private void addFirst0(HandlerContext newCtx) {
		HandlerContext nextCtx = head.next;
		newCtx.prev = head;
		newCtx.next = nextCtx;
		head.next = newCtx;
		nextCtx.prev = newCtx;
	}

	public HandlerPipeline addList(String name, Handler handler) {
		final HandlerContext newCtx;
		newCtx = newContext(name, handler);
		addLast0(newCtx);
		return this;
	}

	private void addLast0(HandlerContext newCtx) {
		HandlerContext prev = tail.prev;
		newCtx.prev = prev;
		newCtx.next = tail;
		prev.next = newCtx;
		tail.prev = newCtx;
	}

	private HandlerContext newContext(String name, Handler handler) {
		return new HandlerContext(this, name, handler);
	}

	public void fireChannelRead(Object msg) {
		HandlerContext.invokeChannelRead(head, msg);
	}

	final class TailContext extends HandlerContext implements Handler {
		TailContext(HandlerPipeline pipeline) {
			super(pipeline, "tail", null);
		}

		@Override
		public Handler handler() {
			return this;
		}

		@Override
		public void channelRead(HandlerContext context, Object msg) {
			onUnhandledInboundMessage(msg);
		}

	}

	final class HeadContext extends HandlerContext implements Handler {

		HeadContext(HandlerPipeline pipeline) {
			super(pipeline, "head", null);
		}

		@Override
		public Handler handler() {
			return this;
		}

		@Override
		public void channelRead(HandlerContext context, Object msg) {
			context.fireChannelRead(msg);
		}
	}

	protected void onUnhandledInboundMessage(Object msg) {
		//System.out.println("Discarded inbound message {} that reached at the tail of the pipeline. " + "Please check your pipeline configuration.", msg);
	}
}
