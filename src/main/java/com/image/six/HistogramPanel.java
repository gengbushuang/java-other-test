package com.image.six;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
/**
 * 展现直方图的组件
 * @Description:TODO
 * @author gbs
 * @Date 2017年2月16日 下午5:56:42
 */
public class HistogramPanel extends JPanel implements ViewCallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2507914393523388974L;
	private BufferedImage destImage;
	private int[] histogram;
	private Dimension size;
	private double linePos;
	private int threshold;
	private JButton okBtn;

	public HistogramPanel(BufferedImage destImage, int[] histogram) {
		this.destImage = destImage;
		this.histogram = histogram;
		this.size = new Dimension(destImage.getWidth(), destImage.getHeight());
		okBtn = new JButton("OK");
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
	}
	
	public double getLinePos() {
		return linePos;
	}



	public void setLinePos(double linePos) {
		this.linePos = linePos;
	}



	public int getThreshold() {
		return threshold;
	}



	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}



	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
		if (destImage != null) {
			renderHistogramWithLine(destImage);
			g2d.drawImage(destImage, 0, 0, destImage.getWidth(), destImage.getHeight(), null);
		}
	}

	private void renderHistogramWithLine(BufferedImage destImage) {
		Dimension size = getSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();
		double maxFrequency = 0;
		for (int i = 0; i < histogram.length; i++) {
			maxFrequency = Math.max(maxFrequency, histogram[i]);
		}

		Graphics2D g2d = destImage.createGraphics();
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, width, height);

		g2d.setPaint(Color.BLACK);
		g2d.drawLine(50, 50, 50, height - 50);
		g2d.drawLine(50, height - 50, width - 50, height - 50);

		g2d.drawString("0", 50, height - 30);
		g2d.drawString("255", width - 50, height - 30);
		g2d.drawString("0", 20, height - 50);
		g2d.drawString("" + maxFrequency, 20, 50);

		double xunit = (width - 100.0) / 256.0d;
		double yunit = (height - 100.0) / maxFrequency;

		for (int i = 0; i < histogram.length; i++) {
			double xp = 50 + xunit * i;
			double yp = yunit * histogram[i];
			Rectangle2D rect2d = new Rectangle2D.Double(xp, height - 50 - yp, xunit, yp);
			g2d.fill(rect2d);
		}

		if ((linePos - 50) >= 0 && (width - linePos) >= 50) {
			threshold = (int) ((linePos - 50) / xunit);
			linePos = 50 + xunit * threshold;
			g2d.setPaint(Color.RED);
			g2d.drawLine((int) linePos, 50, (int) linePos, height - 50);
			g2d.drawString("阀值:" + threshold, (int) linePos - 10, 50);
		}

	}

	public void showUI() {
		JDialog ui = new JDialog();
		ui.getContentPane().setLayout(new BorderLayout());
		ui.getContentPane().add(this, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(okBtn);
		ui.getContentPane().add(okBtn, BorderLayout.SOUTH);
		ui.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ui.pack();
		ui.setVisible(true);
	}

	public void setupActionListener(ActionListener a) {
		this.okBtn.addActionListener(a);
	}

	@Override
	public void mooveLine(double position) {
		this.linePos = position;
		this.repaint();
	}
}
