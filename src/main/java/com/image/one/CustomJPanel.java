package com.image.one;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
		
		//////stroke接口,图像的描边修饰
		//创建stroke对象实例
//		float[] dash = {10.0f,5.0f,3.0f};
//		Stroke dashed = new BasicStroke(2.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f,dash,0.0f);
//		//设置stroke对象引用
//		g2.setStroke(dashed);
//		//创建形状
//		Shape rect2D = new RoundRectangle2D.Double(50,50,300,100,10,10);
//		g2.draw(rect2D);
		
		
		//////Texture Fill接口,纹理填充
//		Rectangle2D rect = new Rectangle2D.Double(10, 10, 200, 200);
//		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//		//rect截取image实例作为纹理的区域
//		//使用TexturePaint完成对矩形区域填充
//		TexturePaint tp = new TexturePaint(image, rect);
//		Shape rect2D = new RoundRectangle2D.Double(50,50,300,100,10,10);
//		g2.setPaint(tp);
//		g2.fill(rect2D);
		
		//////Font属性
//		//反锯齿
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		//设置画笔颜色
//		g2.setPaint(Color.BLUE);
//		try {
//			g2.setFont(loadFont());
//		} catch (FontFormatException | IOException e) {
//			e.printStackTrace();
//		}
//		g2.drawString("我是谁", 50, 50);
		
		//////GeneralPath与自定义几何形状
		//反锯齿
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//五角星的5个点坐标
		int x1 = this.getWidth()/2;
		int y1 = 20;
		int x2 = this.getWidth()/5;
		int y2 = this.getHeight()-20;
		int x3=x2*4;
		int y3 = this.getHeight()-20;
		int x4 = 20;
		int y4 = this.getHeight()/3;
		int x5=this.getWidth()-20;
		int y5=y4;
		//定义画点的顺序
		int xlPoints[]={x1,x2,x5,x4,x3};
		int ylPoints[]={y1,y2,y5,y4,y3};
		//设置填充颜色
		g2.setPaint(Color.RED);
		//实例化GeneralPath对象
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,xlPoints.length);
		//
		polygon.moveTo(xlPoints[0], ylPoints[0]);
		//顺序画出剩下点
		for(int i = 1; i<xlPoints.length;i++){
			polygon.lineTo(xlPoints[i], ylPoints[i]);
		}
		//调用形成一个封闭几何形状
		polygon.closePath();
		//
		g2.draw(polygon);
		g2.dispose();
	}
	
	public Font loadFont() throws FontFormatException, IOException{
		String fontFileName = "/tff/1.ttf";
		System.out.println(this.getClass().getResource("/"));
		InputStream is = this.getClass().getResourceAsStream(fontFileName);
		System.out.println(is);
		Font actionJson = Font.createFont(Font.TRUETYPE_FONT, is);
		Font actionJsonBase = actionJson.deriveFont(Font.BOLD,16);
		return actionJsonBase;
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
