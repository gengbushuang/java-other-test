package com.image.three;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.image.seven.ZoomFilter;
import com.image.six.BinaryFilter;
import com.image.six.GayEFilter;
import com.image.six.HistogramDataExtractor;
import com.image.six.HistogramEFilter;
import com.image.six.HistogramPanel;
import com.image.six.RedLineMonitor;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7838000642719162271L;
	
	private BufferedImage sourceImage;
	private BufferedImage destImage;
	
	public ImagePanel(){
		
	}
	
	public BufferedImage getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(BufferedImage sourceImage) {
		this.sourceImage = sourceImage;
	}

	public BufferedImage getDestImage() {
		return destImage;
	}

	public void setDestImage(BufferedImage destImage) {
		this.destImage = destImage;
	}
	
//	RGBHistogramFilter filter = new RGBHistogramFilter();
//	ContrastFilter filter = new ContrastFilter(50);
//	BrightFilter filter = new BrightFilter(1.5f);
//	SaturationFilter filter = new SaturationFilter(0.15);
//	SepiaToneFilter filter = new SepiaToneFilter();
//	HistogramDataExtractor extractor = new HistogramDataExtractor();
//	BinaryFilter filter = new BinaryFilter(); 
//	HistogramEFilter filter = new HistogramEFilter();
//	GayEFilter filter = new GayEFilter();
	ZoomFilter filter = new ZoomFilter(ZoomFilter.K_TIMES_ZOOM);
	public void process(){
		filter.setTimes(3);
		this.destImage = filter.filter(sourceImage, null);
		////
//		this.destImage = extractor.filter(sourceImage, null);
//		int[] histogram = extractor.getHistogram();
//		final HistogramPanel uPanel = new HistogramPanel(this.destImage,histogram);
//		RedLineMonitor lineMonitor = new RedLineMonitor(uPanel);
//		uPanel.addMouseListener(lineMonitor);
//		uPanel.addMouseMotionListener(lineMonitor);
//		uPanel.setupActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				extractor.setThreshold(uPanel.getThreshold());
//				destImage = extractor.filter(sourceImage, null);
//				sss();
//			}
//			
//		});
//		uPanel.showUI();
		
	}
	
	public void sss(){
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
//		int width = sourceImage.getWidth();
//		if(width>)
//		int height = sourceImage.getHeight();
		if(sourceImage!=null){
			g2d.drawImage(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
			if(destImage!=null){
				g2d.drawImage(destImage,sourceImage.getWidth()+10,0,destImage.getWidth(),destImage.getHeight(),null);
			}
		}
	}

}
