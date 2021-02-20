package com.logsearch.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.logsearch.demo.controller.RestControllers;

public class Services {
	private static final Logger log = LoggerFactory.getLogger(RestControllers.class);
	// List<Map<String, String>> returnList;

	public List<Map<String, String>> callingMethodListFilesForFolder(String path) {
		// returnList = new ArrayList<>();
		// final File folder = new File(path);
		List<Map<String, String>> returnMsg = null;
		try {
			returnMsg = listFilesForFolder2(path);
		} catch (Exception e) {
			log.info("exception:{}", e);
		}
		log.info("inside callingMethodListFilesForFolder return:{}", returnMsg);
		return returnMsg;
	}

//	private void listFilesForFolder(File folder) {
//		log.info("inside listFilesForFolder folder path:{}", folder.getPath());
//		for (final File fileEntry : folder.listFiles()) {
//     	Map<String, String> allfilesAndDir = new HashMap<>();
//			if (fileEntry.isDirectory()) {
//				listFilesForFolder(fileEntry);
//			} else {
//				allfilesAndDir.put("name", fileEntry.getName());
//				allfilesAndDir.put("dir", fileEntry.getAbsolutePath());
//				returnList.add(allfilesAndDir);
//			}
//			log.info("inside listFilesForFolder folder path:{}", fileEntry.getName());
//		}
//	}

	public List<Map<String, String>> listFilesForFolder2(String path) {
		List<Map<String, String>> returnList = new ArrayList<>();
		final File folder = new File(path);
		for (final File fileEntry : folder.listFiles()) {
			Map<String, String> allfilesAndDir = new HashMap<>();
			String dateTimeOfFile=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fileEntry.lastModified());
			allfilesAndDir.put("name", fileEntry.getName());
			allfilesAndDir.put("dir", fileEntry.getAbsolutePath());
			allfilesAndDir.put("datetime", dateTimeOfFile);
			returnList.add(allfilesAndDir);

		}
		return returnList;
	}

	public Map<String, String> readLogFile(String fileName, String filePath) {
		FileInputStream fin = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			fin = new FileInputStream(filePath + fileName);
			Scanner scanner = new Scanner(fin);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				stringBuffer.append(line+"|");
			}
			scanner.close();
		} catch (IOException e) {
			log.error("exception {}", e);
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				log.error("exception {}", e);
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("name", fileName);
		map.put("dir", filePath + fileName);
		map.put("fileContent", stringBuffer.toString());
		return map;
	}
	
/*	public Map<String, String> readLogFile(String fileName, String filePath) {
		FileInputStream fin = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			fin = new FileInputStream(filePath + fileName);
			Scanner scanner = new Scanner(fin);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.contains("INFO")) {
					if(line.contains("INFO")) {
					stringBuffer.append("<div style=\"background-color:#e7ffce;padding: 0.2em;\"><span style=\"color:black;font-weight:bold;\">"+line.substring(0,line.indexOf("INFO"))+"</span><span>"+line.substring(line.indexOf("INFO"), line.length())+"</span></div>");
					}else if(!line.contains("INFO")) {
						stringBuffer.append("<div>"+line+"</div>");
					}
				}
				if(line.contains("DEBUG")) {
					if(line.contains("DEBUG")) {
					stringBuffer.append("<div style=\"background-color:#ceddff;padding: 0.2em;\"><span style=\"color:black;font-weight:bold;\">"+line.substring(0,line.indexOf("DEBUG"))+"</span><span>"+line.substring(line.indexOf("DEBUG"), line.length())+"</span></div>");
					}else if(!line.contains("DEBUG")) {
						stringBuffer.append("<div>"+line+"</div>");
					}
				}
			}
			scanner.close();
		} catch (IOException e) {
			log.error("exception {}", e);
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				log.error("exception {}", e);
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("name", fileName);
		map.put("dir", filePath + fileName);
		map.put("fileContent", stringBuffer.toString());
		return map;
	}*/

	public List<Map<String, String>> searchLogWString(String str, String filePath) {
		log.info("filpaths searchLogWString params :::{},{}", str, filePath);
		List<Map<String, String>> returnMsg = new ArrayList<>();
		//getting all log file names
		List<Map<String, String>> allLogsPath = listFilesForFolder2(filePath);
		
		try {
			for (Map<String, String> f : allLogsPath) {
				StringBuffer stringBuffer = new StringBuffer();
				FileInputStream fin = new FileInputStream(filePath + f.get("name"));

				// searching each file
				Scanner scanner = new Scanner(fin);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.contains(str)) {
						stringBuffer.append(line);
					}
				}
				scanner.close();
				if (stringBuffer.toString().length() > 0) {
					// setting in map for each file where content is found
					Map<String, String> map = new HashMap<>();
					map.put("fileName", f.get("name"));
					map.put("dir", filePath + f.get("name"));
					map.put("fileContent", stringBuffer.toString());
					returnMsg.add(map);
				}
				fin.close();
			}
		} catch (IOException e) {
			log.error("exception {}", e);
		}

		return returnMsg;
	}

	public List<Map<String, String>> getAllLogWithDate(String date, String logPath) {
		log.info("filpaths getAllLogWithDate params :::{},{}", date, logPath);
		List<Map<String, String>> returnList = new ArrayList<>();
		final File folder = new File(logPath);
		for (final File fileEntry : folder.listFiles()) {
			String dateTimeOfFile=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fileEntry.lastModified());
			String dateOfFile=new SimpleDateFormat("dd-MM-yyyy").format(fileEntry.lastModified());
			log.info("dateOfFile {} of file {}",dateOfFile,fileEntry.getName());
			if(dateOfFile.equals(date))
			{
				Map<String, String> allfilesAndDir = new HashMap<>();
				allfilesAndDir.put("name", fileEntry.getName());
				allfilesAndDir.put("dir", fileEntry.getAbsolutePath());
				allfilesAndDir.put("datetime", dateTimeOfFile);
				returnList.add(allfilesAndDir);
			}
		}
		return returnList;
	}
	
//	public String makeJson(String data) {
//		return 
//	}
}
