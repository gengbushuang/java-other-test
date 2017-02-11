package com.image.one;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyFilterUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1l;
	public static final String GRAY_CMD = "灰度";
	public static final String BINARY_CMD = "黑白";
	public static final String BLUR_CMD = "模糊";
	public static final String ZOOM_CMD = "放缩";
	public static final String BROWSER_CMD = "选择...";

	private JButton grayBtn;
	private JButton binaryBtn;
	private JButton blurBtn;
	private JButton zoomBtn;
	private JButton browserBtn;
	private MyFilters filters;

	private BufferedImage srcImage;

	public MyFilterUI() {
		this.setTitle("JAVA 2D BufferedImageOp");
		this.grayBtn = new JButton(GRAY_CMD);
		this.binaryBtn = new JButton(BINARY_CMD);
		this.blurBtn = new JButton(BLUR_CMD);
		this.zoomBtn = new JButton(ZOOM_CMD);
		this.browserBtn = new JButton(BROWSER_CMD);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(this.grayBtn);
		btnPanel.add(this.binaryBtn);
		btnPanel.add(this.blurBtn);
		btnPanel.add(this.zoomBtn);
		btnPanel.add(this.browserBtn);

		filters = new MyFilters();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(filters, BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);

		setupActionListener();
	}

	private void setupActionListener() {
		this.grayBtn.addActionListener(this);
		this.binaryBtn.addActionListener(this);
		this.blurBtn.addActionListener(this);
		this.zoomBtn.addActionListener(this);
		this.browserBtn.addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (srcImage == null) {
			JOptionPane.showMessageDialog(this, "请先选择图像文件");
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				srcImage = ImageIO.read(f);
				filters.setImage(srcImage);
				filters.setDescImage(srcImage);
				filters.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		if (GRAY_CMD.equals(e.getActionCommand())) {
			BufferedImage doColorGray = filters.doColorGray(srcImage);
			filters.setDescImage(doColorGray);
			filters.repaint();
		} else if (BINARY_CMD.equals(e.getActionCommand())) {
			BufferedImage doBinaryImage = filters.doBinaryImage(srcImage);
			filters.setDescImage(doBinaryImage);
			filters.repaint();
		} else if (BLUR_CMD.equals(e.getActionCommand())) {
			BufferedImage doBlur = filters.doBlur(srcImage);
			filters.setDescImage(doBlur);
			filters.repaint();
		} else if (ZOOM_CMD.equals(e.getActionCommand())) {
			BufferedImage doScale = filters.doScale(srcImage, 1.5, 1.5);
			filters.setDescImage(doScale);
			filters.repaint();
		} else if (BROWSER_CMD.equals(e.getActionCommand())) {
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				srcImage = ImageIO.read(f);
				filters.setImage(srcImage);
				filters.setDescImage(srcImage);
				filters.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		MyFilterUI ui = new MyFilterUI();
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setPreferredSize(new Dimension(800,600));
		ui.pack();
		ui.setVisible(true);
	}
}
