package com.image.two;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainUI extends JFrame implements ActionListener{

	public static final String IMAGE_CMD = "选择图像...";
	public static final String PROCESS_CMD = "处理";
	
	private JButton imgBtn;
	private JButton processBtn;
	private ImagePanel imagePanel;
	
	private BufferedImage srcImage;
	
	public MainUI(){
		setTitle("JFrame UI");
		imgBtn = new JButton(IMAGE_CMD);
		processBtn = new JButton(PROCESS_CMD);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.add(imgBtn);
		btnPanel.add(processBtn);
		
		imagePanel = new ImagePanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(imagePanel, BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);

		setupActionListener();
	}
	
	private void setupActionListener() {
		this.imgBtn.addActionListener(this);
		this.processBtn.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(SwingUtilities.isEventDispatchThread()){
			System.out.println("Event Dispath Thread!!");
		}
		
		if(srcImage == null){
			JOptionPane.showMessageDialog(this, "请先选择图像文件");
			try {
				JFileChooser chooser = new JFileChooser();
				setFileTypeFilter(chooser);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				if(f!=null){
					srcImage = ImageIO.read(f);
					imagePanel.setSourceImage(srcImage);
					imagePanel.repaint();
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		if(IMAGE_CMD.equals(e.getActionCommand())){
			JOptionPane.showMessageDialog(this, "请先选择图像文件");
			try {
				JFileChooser chooser = new JFileChooser();
				setFileTypeFilter(chooser);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				if(f!=null){
					srcImage = ImageIO.read(f);
					imagePanel.setSourceImage(srcImage);
					imagePanel.repaint();
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			imagePanel.repaint();
		}else if(PROCESS_CMD.equals(e.getActionCommand())){
			imagePanel.process();
			imagePanel.repaint();
		}
	}
	
	public void setFileTypeFilter(JFileChooser chooser){
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images","jpg","png");
		chooser.setFileFilter(filter);
	}
	
	public void openview(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800,600));
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		MainUI ui = new MainUI();
		ui.openview();
	}

}
