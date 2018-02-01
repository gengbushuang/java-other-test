package com.raft.server.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;

public class FileTest {

	public static void main(String[] args) {
		String dataDirectory = "/Users/gbs/tool/git/java-other-test/src/main/java/com/raft/server/config/";
		Path path = Paths.get(dataDirectory);
		
		try {
			String json = Files.toString(new File(path.resolve("init-cluster.json").toString()), Charset.forName("utf-8"));
			ClusterConfiguration configuration = JSON.parseObject(json, ClusterConfiguration.class);
			long lastLogIndex = configuration.getLastLogIndex();
			System.out.println(lastLogIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		FileInputStream stream = null;
//
//		try {
//			stream = new FileInputStream(path.resolve("init-cluster.json").toString());
//			Files.readFirstLine(file, charset)
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

	}
}
