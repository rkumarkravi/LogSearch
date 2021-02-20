package com.extra.rk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {
		
		// searchLog("log_application.log");
		//final File folder = new File("/logs");listFilesForFolder(folder);
		listFilesForFolder2("logs/");
		//searchLogWString("log_application.log","dispatcherServlet");
	}

	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println("-------------");
				System.out.println("DIR:" + fileEntry.getAbsolutePath() + ", Name:" + fileEntry.getName());
				System.out.println("-------------");
			}
		}
	}
	
	public static void listFilesForFolder2(String path) {
		final File folder = new File(path);
		for (final File fileEntry : folder.listFiles()) {
			
				System.out.println("-------------");
				System.out.println("DIR:" + fileEntry.getAbsolutePath() + ", Name:" + fileEntry.getName()+ ","
						+ " last modified:" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fileEntry.lastModified()));
				System.out.println("-------------");
			
		}
	}

	public static void searchLog(final String filename) throws IOException {
		FileInputStream fin = new FileInputStream("logs/" + filename);
		int i = 0;
		while ((i = fin.read()) != -1) {
			System.out.print((char) i);
		}
		fin.close();
	}

	public static void searchLogWString(final String filename, final String str) throws IOException {
		FileInputStream fin = new FileInputStream("logs/" + filename);
		Scanner scanner = new Scanner(fin);
		while (scanner.hasNext()) {
			if(scanner.nextLine().contains(str)) {
			System.out.println(scanner.nextLine());
			}
		}
		scanner.close();
		fin.close();
	}

}
