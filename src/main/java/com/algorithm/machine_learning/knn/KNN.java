package com.algorithm.machine_learning.knn;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class KNN {

	public static void main(String[] args) {
		INDArray arr1 = Nd4j.create(new float[] { 1, 1.1f, 1, 1, 0, 0, 0, 0.1f }, new int[] { 4, 2 });
		System.out.println("---------------------arr1---------------------");
		System.out.println(arr1);
		// 获取第一维长度
		int dataSize = arr1.shape()[0];
		System.out.println("dataSize-->" + dataSize);

		float[] f = new float[] { 3, 1 };
		INDArray arr2 = Nd4j.create(dataSize, f.length);
		for (int i = 0; i < dataSize; i++) {
			arr2.putRow(i, Nd4j.create(f));
		}
		System.out.println("---------------------arr2---------------------");
		System.out.println(arr2);
		
		INDArray sub = arr2.sub(arr1);
		System.out.println("---------------------sub---------------------");
		System.out.println(sub);
		
		INDArray mul = sub.mul(sub);
		System.out.println("---------------------mul---------------------");
		System.out.println(mul);
		
		INDArray sum = mul.sum(1);
		System.out.println("---------------------sum---------------------");
		System.out.println(sum);
		
		INDArray sqrt = Transforms.sqrt(sum);
		System.out.println("---------------------sqrt---------------------");
		System.out.println(sqrt);
		
	}

}
