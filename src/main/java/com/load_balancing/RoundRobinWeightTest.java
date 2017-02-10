package com.load_balancing;

public class RoundRobinWeightTest {

	public static int[] server = new int[4];// 机器序号：权重
	public static int cw = 0;
	public static int number = 2;// 当前SERVER的序号,开始是-1
	public static int max;// 最大权重
	public static int min;
	public static int gcd;// 最大公约数

	static {
		init();
		max = getMaxWeight(server);
		min=getMinWeight(server);
		gcd = gcd(server);
	}

	public static void init() {
		server[0] = 3;
		server[1] = 4;
		server[2] = 6;
		server[3] = 7;
	}

	/**
	 * 求最大公约数
	 * 
	 * @param array
	 * @return
	 */
	public static int gcd(int[] ary) {

		int min = ary[0];

		for (int i = 0; i < ary.length; i++) {
			if (ary[i] < min) {
				min = ary[i];
			}
		}
		while (min >= 1) {
			boolean isCommon = true;
			for (int i = 0; i < ary.length; i++) {
				if (ary[i] % min != 0) {
					isCommon = false;
					break;
				}
			}
			if (isCommon) {
				break;
			}
			min--;
		}
		System.out.println("gcd=" + min);
		return min;
	}

	/**
	 * 求最大值，权重
	 * 
	 * @return
	 */

	public static int getMaxWeight(int[] ary) {
		int max = 0;
		for (int i = 0; i < ary.length; i++) {
			if (max < ary[i]) {
				max = ary[i];
			}
		}
		return max;
	}
	
	public static int getMinWeight(int[] ary) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < ary.length; i++) {
			if (min > ary[i]) {
				min = ary[i];
			}
		}
		return min;
	}

	/**
	 * 获取请求的SERVER序号
	 * 
	 * @return
	 */
	public static Integer next() {

		while (true) {
			number = (number + 1) % server.length;
			if (number == server.length-1) {
				cw = cw + gcd;// cw比较因子，从最大权重开始，以最大公约数为步长递减
				if (cw > max) {//
					cw = min;
//					if (cw == 0)
//						return null;
				}
			}
			if (server[number] >= cw){
//				System.out.println("number=" + number);
//			 	System.out.println("cw=" + cw);
				return number;
			}
		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.print(server[RoundRobinWeightTest.next()] + ",");
		}
	}
}
