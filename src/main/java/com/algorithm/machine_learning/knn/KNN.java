package com.algorithm.machine_learning.knn;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class KNN {

	static {
		String jvmName = System.getProperty("java.vm.name", "").toLowerCase();
		String osName = System.getProperty("os.name", "").toLowerCase();
		String osArch = System.getProperty("os.arch", "").toLowerCase();
		String abiType = System.getProperty("sun.arch.abi", "").toLowerCase();
		if (jvmName.startsWith("dalvik") && osName.startsWith("linux")) {
			osName = "android";
		} else if (jvmName.startsWith("robovm") && osName.startsWith("darwin")) {
			osName = "ios";
			osArch = "arm";
		} else if (osName.startsWith("mac os x") || osName.startsWith("darwin")) {
			osName = "macosx";
		} else {
			int spaceIndex = osName.indexOf(' ');
			if (spaceIndex > 0) {
				osName = osName.substring(0, spaceIndex);
			}
		}
		if (osArch.equals("i386") || osArch.equals("i486") || osArch.equals("i586") || osArch.equals("i686")) {
			osArch = "x86";
		} else if (osArch.equals("amd64") || osArch.equals("x86-64") || osArch.equals("x64")) {
			osArch = "x86_64";
		} else if (osArch.startsWith("aarch64") || osArch.startsWith("armv8") || osArch.startsWith("arm64")) {
			osArch = "arm64";
		} else if ((osArch.startsWith("arm")) && abiType.equals("gnueabihf")) {
			osArch = "armhf";
		} else if (osArch.startsWith("arm")) {
			osArch = "arm";
		}
		String platform = osName + "-" + osArch;
		if ("windows".equals(osName)) {
			System.load(System.getProperty("user.dir") + "/windows_64_dll/mkl_rt.dll");
//			String [] dlls ={"libwinpthread-1.dll","libgcc_s_seh-1.dll","libgomp-1.dll","libquadmath-0.dll","libstdc++-6.dll","libgfortran-3.dll","libopenblas.dll","mkl_rt.dll","libnd4j.dll"};
		}
	}

	public static void main(String[] args) {
		KNN k = new KNN();
		k.classif();
	}

	public void classif() {
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
