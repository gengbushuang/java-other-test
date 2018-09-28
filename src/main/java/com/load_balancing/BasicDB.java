package com.load_balancing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BasicDB {

	private static final int MAX_DATA_LENGTH = 1020;
	// 补齐空白
	private static final byte[] ZERO_BYTES = new byte[MAX_DATA_LENGTH];
	// 数据文件扩展名
	private static final String DATA_SUFFIX = ".data";
	// 元数据文件扩展名，包括索引和空白间数据
	private static final String META_SUFFIX = ".meta";

	Map<String, Long> indexMap;// 索引信息，键->值在.data文件中的位置
	Queue<Long> gaps;// 空白空间，值为在.data文件中的位置

	RandomAccessFile db; // 值数据文件
	File metaFile; // 元数据

	public BasicDB(String path, String name) throws IOException {
		File dataFile = new File(path + name + DATA_SUFFIX);
		metaFile = new File(path + name + META_SUFFIX);

		db = new RandomAccessFile(dataFile, "rw");

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
			Long index = in.readLong();
			indexMap.put(key, index);
		}

	}

	private void loadGaps(DataInputStream in) throws IOException {
		int size = in.readInt();
		gaps = new ArrayDeque<>();
		for (int i = 0; i < size; i++) {
			Long index = in.readLong();
			gaps.add(index);
		}
	}

	// 保存键值对
	public void put(String key, byte[] value) throws IOException {
		Long index = indexMap.get(key);
		if (index == null) {
			// 获取索引
			index = nextAvailablePos();
			indexMap.put(key, index);
		}
		writeData(index, value);
	}

	private void writeData(Long index, byte[] data) throws IOException {
		if (data.length > MAX_DATA_LENGTH) {
			throw new IllegalArgumentException(
					"maximum allowed length is " + MAX_DATA_LENGTH + ",data length is " + data.length);
		}
		// 定位数据索引位置
		db.seek(index);
		// 写入数据长度
		db.writeInt(data.length);
		// 写入数据
		db.write(data);
		// 补齐数据后面空白
		db.write(ZERO_BYTES, 0, MAX_DATA_LENGTH - data.length);
	}

	private Long nextAvailablePos() throws IOException {
		if (gaps.isEmpty()) {
			return db.length();
		} else {
			// 先看是不是有以前空白的索引
			return gaps.poll();
		}
	}

	// 根据键获取值
	public byte[] get(String key) throws IOException {
		Long index = indexMap.get(key);
		if (index != null) {
			return getData(index);
		}
		return null;
	}

	private byte[] getData(Long index) throws IOException {
		// 定位索引位置
		db.seek(index);
		// 获取数据长度
		int length = db.readInt();
		// 取出数据
		byte[] data = new byte[length];
		db.readFully(data);
		return data;
	}

	// 根据键删除数据
	public void remove(String key) {
		Long index = indexMap.remove(key);
		if (index != null) {
			gaps.offer(index);
		}
	}

	// 刷新数据到库里面
	public void flush() throws IOException {
		saveMeta();
		// 获取文件描述符
		db.getFD().sync();
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
		for (Long pos : gaps) {
			out.writeLong(pos);
		}

	}

	// 保存索引到元文件
	private void saveIndex(DataOutputStream out) throws IOException {
		out.writeInt(indexMap.size());
		for (Map.Entry<String, Long> entry : indexMap.entrySet()) {
			out.writeUTF(entry.getKey());
			out.writeLong(entry.getValue());
		}

	}

	// 关闭
	public void close() throws IOException {
		flush();
		db.close();
	}

	public static void main(String[] args) throws IOException {
		String path = "D:\\tmp\\";
		String name = "tmp_";
		BasicDB basicDB = new BasicDB(path, name);
		String key = "地域";
		String value = "上海";

		basicDB.put(key, value.getBytes());

		key = "性别";
		value = "男";
		basicDB.put(key, value.getBytes());
		
		basicDB.close();
	}
}
