package com.load_balancing;
/**
 * 布谷鸟hash
 *
 * @author gbs
 *
 */
public class CuckooHash {

	private int x[];

	private int y[];

	private int count;

	public CuckooHash() {

	}

	public CuckooHash(int count) {
		x = new int[count];
		y = new int[count];
		this.count = count;
	}

	public int hashx(int key) {
		return key % count;
	}

	public int hashy(int key) {
		return (key / count) % count;
	}

	public boolean insert(int key) {
		int index;
		int value = key;
		int i = 0;
		int tmp;
		while (i < 10) {
			index = hashx(value);
			if (x[index] == 0) {
				x[index] = value;
				return true;
			}

			tmp = x[index];
			x[index] = value;
			value = tmp;

			index = hashy(value);

			if (y[index] == 0) {
				y[index] = value;
				return true;
			}

			tmp = y[index];
			y[index] = value;
			value = tmp;

			i++;
		}
		return false;
	}

	public static void main(String[] args) {
		int[] a = new int[] { 20, 50, 53, 75, 100, 67, 105, 3, 36, 39 };
		CuckooHash cuckooHash = new CuckooHash(11);
		for (int i : a) {
			cuckooHash.insert(i);
		}
	}
}
