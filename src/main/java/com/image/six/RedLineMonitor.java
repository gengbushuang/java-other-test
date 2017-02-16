package com.image.six;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * 鼠标位置捕捉
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 下午5:55:54
 */
public class RedLineMonitor extends MouseAdapter {

	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private ViewCallBack callBack;

	public RedLineMonitor(ViewCallBack callBack) {
		this.callBack = callBack;
		System.out.println("install mouse monitor");
	}

	// @Override
	// public void mouseClicked(MouseEvent e) {// 鼠标单击事件
	// System.out.println("mouseClicked");
	// }

	@Override
	public void mousePressed(MouseEvent e) {// 鼠标按下
		startX = e.getPoint().getX();
		startY = e.getPoint().getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {// 鼠标松开
		endX = e.getPoint().getX();
		endY = e.getPoint().getY();
		startX = endX;
		startY = endY;
	}

	// @Override
	// public void mouseEntered(MouseEvent e) {// 鼠标进入组件
	// // System.out.println("mouseEntered");
	//
	// }

	@Override
	public void mouseExited(MouseEvent e) {// 鼠标退出组件

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		endX = e.getPoint().getX();
		endY = e.getPoint().getY();
		if (Math.abs(startX - endX) >= 0) {
			System.out.println(endX);
			callBack.mooveLine(endX);
		}

	}
}
