package com.gof.pattern.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainTest {

	public static void main(String[] args) {
		try (InputStream is = MainTest.class.getResourceAsStream("interpreter.txt"); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));) {
			String text;
			while ((text = reader.readLine()) != null) {
				System.out.println("text = \"" + text + "\"");
				Node node = new ProgramNode();
				node.parse(new Context(text));
				System.out.println("node = " + node);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

	}
}
