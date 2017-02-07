package com.image.one;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class CustomJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3579192776181629515L;

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
//		//单一颜色背景填充
//		g2.setPaint(Color.BLUE);
//		//水平方向线性渐变颜色填充
//		Color sencondColor = new Color(99, 153, 255);
//		GradientPaint hLinePaint = new GradientPaint(0, 0, Color.BLACK, this.getWidth(), 0, sencondColor);
//		g2.setPaint(hLinePaint);
//		//竖直方向线性渐变颜色填充
//		Color controlColor = new Color(99, 153, 255);
//		GradientPaint vLinePaint = new GradientPaint(0, 0, Color.BLACK, 0, this.getHeight(), controlColor);
//		g2.setPaint(vLinePaint);
//		//圆周径向渐变颜色填充
//		float cx = this.getWidth() / 2;
//		float cy = this.getHeight() / 2;
//		//表示半径长度
//		float radius = Math.min(cx, cy);
//		//表示色彩渐变关键帧位置
//		float[] fractions = new float[] { 0.1f, 0.5f, 1.0f };
//		//表示颜色数组
//		Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE };
//		RadialGradientPaint rgp = new RadialGradientPaint(cx, cy, radius, fractions, colors, CycleMethod.NO_CYCLE);
//		g2.setPaint(rgp);
		//填充
//		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//stroke接口
		//创建stroke对象实例
//		float[] dash = {10.0f,5.0f,3.0f};
//		Stroke dashed = new BasicStroke(2.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f,dash,0.0f);
//		//设置stroke对象引用
//		g2.setStroke(dashed);
//		//创建形状
//		Shape rect2D = new RoundRectangle2D.Double(50,50,300,100,10,10);
//		g2.draw(rect2D);
		
		
		
		g2.dispose();
	}

	public static void main(String[] args) {
		JFrame ui = new JFrame("one");
		//关闭事件
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.getContentPane().setLayout(new BorderLayout());
		ui.getContentPane().add(new CustomJPanel(), BorderLayout.CENTER);
		ui.setPreferredSize(new Dimension(380, 380));
		//画图
		ui.pack();
		ui.setVisible(true);
	}

}
