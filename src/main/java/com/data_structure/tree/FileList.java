package com.data_structure.tree;

import java.io.File;

public class FileList {

	
	public void filePath(String path,int depth){
		File f = new File(path);
		showName(f,depth);
		if(f.isDirectory()){
			File[] listFiles = f.listFiles();
			for(File file:listFiles){
				filePath(file.getPath(), depth+1);
			}
		}
	}

	private void showName(File f, int depth) {
		for(int i = 0 ; i<depth;i++){
			System.out.print("	");
		}
		System.out.print(f.getName());
		System.out.println();
	}
	
	public void fileSize(String path,int depth){
		
	}
	
	public static void main(String[] args) {
		String path = "/Users/gbs/tmp";
		new FileList().filePath(path, 0);
	}
}
