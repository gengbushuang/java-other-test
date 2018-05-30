package com.dnf.reverse2.struct;

import java.io.File;
import java.util.Map;
import java.util.Queue;

public class AssignFile {

	// 元数据文件扩展名，包括索引和空白间数据
	private static final String META_SUFFIX = ".asg";

	Map<String, Integer> indexMap;// 索引信息，键->值在.data文件中的位置
	Queue<Integer> gaps;// 空白空间，值为在.data文件中的位置
	File metaFile; // 元数据

	public AssignFile(String path, String name) {
		metaFile = new File(path + name + META_SUFFIX);
	}
}
