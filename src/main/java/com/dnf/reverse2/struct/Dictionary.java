package com.dnf.reverse2.struct;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Dictionary {

	// 元数据文件扩展名，包括索引和空白间数据
	private static final String META_SUFFIX = ".ttl";

	Map<String, Integer> indexMap;// 索引信息，键->值在.data文件中的位置
	Queue<Integer> gaps;// 空白空间，值为在.data文件中的位置
	File metaFile; // 元数据

	public Dictionary(String path, String name) throws IOException {
		metaFile = new File(path + name + META_SUFFIX);

		if (metaFile.exists()) {
			loadMeta();
		} else {
			indexMap = new HashMap<>();
			gaps = new ArrayDeque<>();
		}
	}

	private void loadMeta() throws IOException {
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(metaFile)));
		try {
			loadIndex(in);
			loadGaps(in);
		} finally {
			in.close();
		}
	}

	private void loadIndex(DataInputStream in) throws IOException {
		int size = in.readInt();
		// 16*0.75=12
		indexMap = new HashMap<>((int) (size / 0.75f) + 1, 0.75f);
		for (int i = 0; i < size; i++) {
			String key = in.readUTF();
			Integer index = in.readInt();
			indexMap.put(key, index);
		}

	}

	private void loadGaps(DataInputStream in) throws IOException {
		int size = in.readInt();
		gaps = new ArrayDeque<>();
		for (int i = 0; i < size; i++) {
			Integer index = in.readInt();
			gaps.add(index);
		}
	}

	// 保存键值对
	public Integer put(String key){
		Integer index = indexMap.get(key);
		if (index == null) {
			// 获取索引
			index = nextAvailablePos();
			indexMap.put(key, index);
		}
		return index;
	}

	// 获取索引
	public Integer get(String key) {
		return indexMap.get(key);
	}

	private Integer nextAvailablePos() {
		if (gaps.isEmpty()) {
			return indexMap.size();
		} else {
			// 先看是不是有以前空白的索引
			return gaps.poll();
		}
	}

	// 根据键删除数据
	public void remove(String key) {
		Integer index = indexMap.remove(key);
		if (index != null) {
			gaps.offer(index);
		}
	}

	// 刷新数据到库里面
	public void flush() throws IOException {
		saveMeta();
	}

	private void saveMeta() throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(metaFile)));

		try {
			saveIndex(out);
			saveGaps(out);
		} finally {
			out.close();
		}
	}

	private void saveGaps(DataOutputStream out) throws IOException {
		out.writeInt(gaps.size());
		for (Integer pos : gaps) {
			out.writeInt(pos);
		}

	}

	// 保存索引到元文件
	private void saveIndex(DataOutputStream out) throws IOException {
		out.writeInt(indexMap.size());
		for (Map.Entry<String, Integer> entry : indexMap.entrySet()) {
			out.writeUTF(entry.getKey());
			out.writeInt(entry.getValue());
		}

	}

	// 关闭
	public void close() throws IOException {
		flush();
	}
}
