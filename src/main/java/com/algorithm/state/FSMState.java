package com.algorithm.state;

/**
 * FSM状态的转换 
 * 紧跟0后面的1被改成0 
 * input: 011010111 
 * output: 001000011
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年7月11日 下午6:47:56
 */
public class FSMState {

	public static Input inp = Input.LIZ;

	public enum Input {
		LIZ() {
			@Override
			public void input(char c) {

				switch (c) {
				case '1':
					System.out.print("0");
					inp = Input.LIO;
					break;
				default:
					System.out.print(c);
					break;
				}
			}
		},
		LIO() {
			@Override
			public void input(char c) {
				switch (c) {
				case '0':
					inp = Input.LIZ;
					System.out.print(c);
					break;
				default:
					System.out.print(c);
					break;
				}
			}
		};

		public abstract void input(char c);
	}

	public static void show() {
		String sss = "011010111011010111";
		System.out.println(sss);
		int length = sss.length();
		for (int i = 0; i < length; i++) {
			inp.input(sss.charAt(i));
		}
	}

	public static void main(String[] args) {
		FSMState.show();
	}

}
