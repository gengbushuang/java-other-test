package com.image.three;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.image.four.ContrastFilter;
import com.image.six.PixelStatisticFilter;

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
	
	PixelStatisticFilter filter = new PixelStatisticFilter();
//	ContrastFilter filter = new ContrastFilter(50);
//	BrightFilter filter = new BrightFilter(1.5f);
//	SaturationFilter filter = new SaturationFilter(0.15);
//	SepiaToneFilter filter = new SepiaToneFilter();
	public void process(){
		this.destImage = filter.filter(sourceImage, null);
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
