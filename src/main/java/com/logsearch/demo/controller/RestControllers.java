package com.logsearch.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.logsearch.demo.service.Services;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestControllers {
	private static final Logger log = LoggerFactory.getLogger(RestControllers.class);
	@Value("${myapp.logPath}")
	private String logpath;

	@RequestMapping(value="/allLogs",produces = "application/json")
	@ResponseBody
	public List<Map<String, String>> allFilesFromDir() {
		Services services = new Services();
		return services.callingMethodListFilesForFolder(logpath);
	}

	@RequestMapping(value="/searchlog",produces = "application/json")
	@ResponseBody
	public List<Map<String, String>> searchLogFileName(@RequestParam("searchString") String searchString) throws IOException {
		log.info("called searchLogFileName with search string {}", searchString);
		Services services = new Services();
		return services.searchLogWString(searchString, logpath);
	}

	@RequestMapping(value="/getlogfileWName",produces = "application/json")
	@ResponseBody
	public Map<String, String> getLogFileName(@RequestParam("filename") String filename) {
		log.info("called getlogfile with fileName {}", filename);
		Services services = new Services();
		return services.readLogFile(filename, logpath);
	}
	@RequestMapping(value="/getAllLogsByDate",produces = "application/json")
	@ResponseBody
	public List<Map<String, String>> getAllLogsByDate(@RequestParam("date") String date) {
		log.info("called getlogfile with date {}", date);
		Services services = new Services();
		return services.getAllLogWithDate(date, logpath);
	}
}
