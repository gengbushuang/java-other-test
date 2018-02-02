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
import java.util.Calendar;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;

public class FileTest {

	public static void main(String[] args) {
		long timeInMillis = Calendar.getInstance().getTimeInMillis();
		System.out.println(timeInMillis);
		Random random = new Random(timeInMillis);
		int c = 5000-3000+1;
		System.out.println(c);
		int nextInt = random.nextInt(c);
		System.out.println(nextInt);
		System.out.println(3000+nextInt);
	}
}
