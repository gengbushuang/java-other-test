package com.image.six;

public class PixelHSI {
	//RGB色彩空间转换HSI色彩空间
	public double[] rgbToHSI(int[] rgb) {
		// 归一化像素值r=R/(R+B+G),g=G/(R+B+G),b=B/(R+B+G)
		double sum = rgb[0] + rgb[1] + rgb[2];
		double r = rgb[0] / sum;
		double g = rgb[1] / sum;
		double b = rgb[2] / sum;
		// 根据r，g，b值获得归一化HSI值
		// h=cos^(-1){(0.5*[(r-g)+(r-b)])/[(r-g)^(2)+(r-b)*(g-b)]^(1/2)},
		// h∈[0,∏]当b≤g时
		// h=2∏-cos^(-1){(0.5*[(r-g)+(r-b)])/[(r-g)^(2)+(r-b)*(g-b)]^(1/2)},
		// h∈[∏,2∏]当b>g时
		double s1 = ((r - g) + (r - b)) * 0.5;
		double s2 = Math.pow((r - g), 2) + (r - b) * (g - b);
		double s3 = s1 / Math.sqrt(s2);
		double h = 0;
		if (b <= g) {
			h = Math.acos(s3);
		} else if (b > g) {
			h = 2 * Math.PI - Math.acos(s3);
		}
		// s=1-3*min(r,g,b),s∈[0,1]
		// i=(R+G+B)/(3*255),i∈[0,1]
		double s = 1 - 3 * Math.min(r, Math.min(g, b));
		double i = sum / (3.0 * 255.0);
		// 根据HSI的取值范围分别为H∈[0,360]、S∈[0,100]、I∈[0,255]
		// H=h*180/∏;S=s*100;I=i*255
		double H = h * (180 / Math.PI);
		double S = s * 100;
		double I = i * 255;
		return new double[] { H, S, I };
	}
	//HSI色彩空间转换RGB色彩空间
	public int[] hsiToRGB(double[] hsi) {
		//首先归一化HSI值:h=H*(∏/180);s=S/100;i=I/255
		double h = hsi[0] * (Math.PI / 180);
		double s = hsi[1] / 100;
		double i = hsi[2] / 255;
		//计算中间过程三个分量值x、y、z
		//x=i*(1-s)
		//y=i*[1+(s*cos(h)/cos((∏/3)-h))]
		//z=3*i-(x+y)
		double x = i * (1 - s);
		double y = i * (1 + (s * Math.cos(h)) / Math.cos(Math.PI / 3 - h));
		double z = 3 * i - (x + y);
		//a1=2∏/3
		//a2=4∏/3
		//a3=2∏
		double a1 = (2 * Math.PI) / 3;
		double a2 = (4 * Math.PI) / 3;
		double a3 = 2 * Math.PI;
		double r = 0, g = 0, b = 0;
		if (h < a1) {
			//当h<2∏/3时,b=x,r=y,g=z
			b = x;
			r = y;
			g = z;
		} else if (a1 <= h && h < a2) {
			//当2∏/3<=h<4∏/3时,h=h-(2∏/3),r=x,g=y,b=z
			h = h - a1;
			x = i * (1 - s);
			y = i * (1 + (s * Math.cos(h)) / Math.cos(Math.PI / 3 - h));
			z = 3 * i - (x + y);
			r = x;
			g = y;
			b = z;
		} else if (a2 <= h && h < a3) {
			//当4∏/3<=h<2∏时,h=h-(4∏/3),g=x,b=y,r=z
			h = h - a2;
			x = i * (1 - s);
			y = i * (1 + (s * Math.cos(h)) / Math.cos(Math.PI / 3 - h));
			z = 3 * i - (x + y);
			g = x;
			b = y;
			r = z;
		}
		int red = (int) (r * 255);
		int green = (int) (g * 255);
		int blue = (int) (b * 255);
		return new int[] { red, green, blue };
	}

	public static void main(String[] args) {

	}
}
