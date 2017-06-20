package com.gof.pattern.builder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HTMLBuilder extends Builder {

	private String filename;
	private PrintWriter writer;

	public HTMLBuilder() {

	}

	@Override
	public void makeTitle(String title) {
		filename = title + ".html";
		try {
			writer = new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		writer.println("<html><head><title>" + title + "</title></head<body>");
		writer.println("<h1>" + title + "</h1>");

	}

	@Override
	public void makeString(String str) {
		writer.println("<p>" + str + "</p>");
	}

	@Override
	public void makeItems(String[] items) {
		writer.println("<ul>");
		for (int i = 0; i < items.length; i++) {
			writer.println("<li>" + items[i] + "</li>");
		}
		writer.println("</ul>");
	}

	@Override
	public void close() {
		writer.println("</body></html>");
		writer.close();
	}

	public String getResult() {
		return filename;
	}

}
