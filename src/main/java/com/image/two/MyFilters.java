package com.image.two;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MyFilters extends JPanel{
	
	
	private JButton srcButton;
	private JButton descButton;

	private BufferedImage srcImage;
	private BufferedImage descImage;
	
	public MyFilters(){
		
	}
	
	/**
	 * 用于实现各种色彩空间的转换
	 * 
	 * @Description:TODO
	 * @author gbs
	 * @param bi
	 * @return
	 */
	public BufferedImage doColorGray(BufferedImage bi) {
		// 当前支持的色彩空间有5种。
		// ColorSpace.CS_GRAY灰度功能
		ColorConvertOp filterObj = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		return filterObj.filter(bi, null);
	}

	/**
	 * 黑白功能
	 * @Description:TODO
	 * @author gbs
	 * @param bi
	 * @return
	 */
	public BufferedImage doBinaryImage(BufferedImage bi) {
		//图像灰度化
		bi = doColorGray(bi);
		//
		byte[] threshold = new byte[256];
		for (int i = 0; i < 256; i++) {
			threshold[i] = (i < 128) ? (byte) 0 : (byte) 255;
		}
		BufferedImageOp thresholdOp = new LookupOp(new ByteLookupTable(0, threshold), null);
		return thresholdOp.filter(bi, null);
	}
	/**
	 * 模糊功能
	 * @Description:TODO
	 * @author gbs
	 * @param bi
	 * @return
	 */
	public BufferedImage doBlur(BufferedImage bi){
		//多数情况下使用TYPE_3BYTE_BGR存储方式，不支持，要转换成TYPE_INT_BGR
		if(bi.getType()==BufferedImage.TYPE_3BYTE_BGR){
			bi = convertType(bi,BufferedImage.TYPE_INT_BGR);
		}
		//简单的设置卷积核/卷积模板
		float ninth = 1.0f/9.0f;
		float[]blurKernel = {ninth,ninth,ninth,
				ninth,ninth,ninth,
				ninth,ninth,ninth};
		BufferedImageOp blurFilter = new ConvolveOp(new Kernel(3,3,blurKernel));
		return blurFilter.filter(bi, null);
	}
	
	private BufferedImage convertType (BufferedImage src,int type){
		ColorConvertOp cco = new ColorConvertOp(null);
		BufferedImage dest = new BufferedImage(src.getWidth(),src.getHeight(),type);
		cco.filter(src, dest);
		return dest;
	}
	/**
	 * 
	 * @Description:TODO
	 * @author gbs
	 * @param bi
	 * @param sx
	 * @param sy
	 * @return
	 */
	public BufferedImage doScale(BufferedImage bi,double sx,double sy){
		//AffineTransformOp支持错切、旋转、放缩、平移
		AffineTransformOp atfFilter = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), AffineTransformOp.TYPE_BILINEAR);
		//计算放缩后图像的宽与高
		int nw = (int)(bi.getWidth()*sx);
		int nh = (int)(bi.getHeight()*sy);
		BufferedImage result = new BufferedImage(nw,nh,BufferedImage.TYPE_3BYTE_BGR);
		//实现图像放缩
		atfFilter.filter(bi, result);
		return result;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if(srcImage != null){
			g2d.drawImage(srcImage, 10, 10, 300, 300, null);
		}
		
		if(descImage!=null){
			g2d.drawImage(descImage, 320, 10, 300, 300, null);
		}
		
	}
	
	public void setImage(BufferedImage srcImage) {
		this.srcImage = srcImage;
	}

	public void setDescImage(BufferedImage descImage) {
		this.descImage = descImage;
	}
}
