package com.algorithm.machine_learning.knn;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nd4j.linalg.api.complex.IComplexNDArray;
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
			// String [] dlls
			// ={"libwinpthread-1.dll","libgcc_s_seh-1.dll","libgomp-1.dll","libquadmath-0.dll","libstdc++-6.dll","libgfortran-3.dll","libopenblas.dll","mkl_rt.dll","libnd4j.dll"};
		}
	}

	public static void main(String[] args) throws IOException {
		KNN k = new KNN();
		// float[] x = new float[] { 0, 0 };
//		INDArray y = Nd4j.create(new float[] { 1, 1.1f, 1, 1, 0, 0, 0, 0.1f }, new int[] { 4, 2 });
		// String[] labels = { "A", "A", "B", "B" };
//		int k = 3;
		// k.classify0(x,y,labels);
		k.file2matrix("F:/git/java-other-test/target/classes/com/algorithm/machine_learning/knn/datingTestSet.txt");
	}

	public void file2matrix(String fileName) throws IOException {
//		String path = this.getClass().getResource(fileName).getPath();
//		System.out.println("path-->" + path);
//		 System.out.println(new String(Files.readAllBytes(Paths.get(path))));
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		System.out.println("file size->"+lines.size());
		INDArray y = Nd4j.create(lines.size(), 3);
		String [] labels = new String[lines.size()];
		for(int i = 0;i<lines.size();i++){
			String line = lines.get(i);
			String[] line_split = StringUtils.split(line, "\t");
//			System.out.println("line_split-->" + Arrays.toString(line_split));
			y.putRow(i, Nd4j.create(createFloat(line_split)));
			labels[i] = line_split[3];
		}
		System.out.println(y);
		
		float[] x = new float[] { 0, 0 ,0};
		classify0(x,y, labels,50);
	}

	public float[] createFloat(String[] line_split){
		return new float[]{Float.parseFloat(line_split[0]),Float.parseFloat(line_split[1]),Float.parseFloat(line_split[2])};
	}
	public void classify0(float[] x,INDArray y, String[] labels,int k) {
		System.out.println("---------------------arr1---------------------");
		System.out.println(y);
		// 获取第一维长度
		int dataSize = y.shape()[0];
		System.out.println("dataSize-->" + dataSize);

		float[] f = x;
		INDArray arr2 = Nd4j.create(dataSize, f.length);
		for (int i = 0; i < dataSize; i++) {
			arr2.putRow(i, Nd4j.create(f));
		}
		System.out.println("---------------------arr2---------------------");
		System.out.println(arr2);

		// x-y
		INDArray sub = arr2.sub(y);
		System.out.println("---------------------sub---------------------");
		System.out.println(sub);

		// 平方
		INDArray pow = Transforms.pow(sub, 2);
		System.out.println("---------------------pow---------------------");
		System.out.println(pow);

		// 每一列的sum
		INDArray sum = pow.sum(1);
		System.out.println("---------------------sum---------------------");
		System.out.println(sum);
		// 开方
		INDArray sqrt = Transforms.sqrt(sum);
		System.out.println("---------------------sqrt---------------------");
		System.out.println(sqrt);

		float[] stride = toFloats(sqrt);
		System.out.println("sqrt float-->" + Arrays.toString(stride));
		int[] indexs = toIndexs(stride);
		System.out.println("indexs-->" + Arrays.toString(indexs));

		for (int i = 0; i < k; i++) {
			String lable = labels[indexs[i]];
			System.out.println("lable-->" + lable);
		}
	}

	private int[] toIndexs(float[] stride) {
		int[] indexs = new int[stride.length];
		Arrays.parallelSetAll(indexs, i -> i);

		for (int i = 0, j = i; i < stride.length - 1; j = ++i) {
			float ai = stride[i + 1];
			int bi = indexs[i + 1];
			while (ai < stride[j]) {
				stride[j + 1] = stride[j];
				indexs[j + 1] = indexs[j];
				if (j-- == 0) {
					break;
				}
			}
			stride[j + 1] = ai;
			indexs[j + 1] = bi;
		}
		return indexs;
	}

	public float[] toFloats(INDArray n) {
		if (n instanceof IComplexNDArray)
			throw new IllegalArgumentException("Unable to convert complex array");
		n = n.linearView();
		float[] ret = new float[n.length()];
		for (int i = 0; i < n.length(); i++)
			ret[i] = n.getFloat(i);
		return ret;
	}

}
