package primenumber;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 素数计算
 * 
 * 序号:0 1 2 3 4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48
 * 
 * 数值:3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39 41 43 45 47 49 51 53 55 57 59 61 63 65 67 69 71 73 75 77 79 81 83 85 87 89 91 93 95 97 99
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年11月10日 下午5:30:27
 */
public class PrimeNumber {

	int[] createPrimeNumber(int n) {
		return IntStream.iterate(3, item -> item + 2).limit(n).toArray();
	}
	
	void show(int[] primeNumbers) {
		System.out.println("show:----------------------------");
		for (int i = 0; i < primeNumbers.length; i++) {
			if (primeNumbers[i] == 0) {
				continue;
			}
			System.out.print(primeNumbers[i] + " ");
		}
	}
	
	void filterPrimeNumber(int[] primeNumbers) {
		int lastValue = primeNumbers[primeNumbers.length - 1];
		int index = 0;
		while (true) {
			for (int i = index; i < primeNumbers.length; i++) {
				if (primeNumbers[index] != 0) {
					index = i;
					break;
				}
			}
			int value = 2 * index + 3;
			int tmp = value * value;
			if (tmp > lastValue) {
				show(primeNumbers);
				break;
			} else {
				int indexTmp = (tmp - 3) / 2;
				for (int i = indexTmp; i < primeNumbers.length;) {
					primeNumbers[i] = 0;
					i += value;
				}
			}
			index+=1;
		}
	}

	// 下标对应的值0+3 3+1 3+2+2 3+3+3 4+3+4 5+3+5==>2n+3
	// 值对应的下标0->3 1->5(4) 2->7(5) 3->9(6) 4->11(7) 5->13(8)==>(v-3)/2
	// 每一轮开始的检查的起始下标3->11(8)->23(12)->39(16)->59(20)->83(24)

	public static void main(String[] args) {
		PrimeNumber primeNumber = new PrimeNumber();
		int[] primeNumbers = primeNumber.createPrimeNumber(1000);

		System.out.println(Arrays.toString(primeNumbers));
		primeNumber.filterPrimeNumber(primeNumbers);
		
		
	}
}
