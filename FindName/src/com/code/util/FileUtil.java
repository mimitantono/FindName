/**
 * Name: FileUtil
 * @author mimitantono
 * Purpose: 
 * Version information: 8:58:44 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * 
 * @author mimi
 */
public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class.getName());

	private static boolean REMOVE = true;

	/**
	 * This method reads the content of a file and return its content.
	 * @param fileName full path or relative path of a file
	 * @return content of the file
	 */
	public static String readFromFile(String fileName) {
		StringBuffer result = new StringBuffer();
		FileReader fr;
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			boolean eof = false;
			String data;
			while (!eof) {
				data = br.readLine();
				if (result != null && result.length() > 0) {
					result.append("\n");
				}
				if (data != null) {
					result.append(data);
				} else {
					eof = true;
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			log.info(Trace.getStackTrace(e));
		} catch (IOException e) {
			log.info(Trace.getStackTrace(e));
		}
		log.debug("Read from file : " + fileName + "\n" + result);
		return result.toString();
	}

	public static void writeToFile(String fileName, String data) {
		log.info("Write to file : " + fileName + "\n" + data);
		FileWriter fw;
		try {
			File file = new File(fileName);
			fw = new FileWriter(file.getAbsolutePath());
			log.info(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(data);
			bw.close();
			fw.close();
		} catch (IOException e) {
			log.error(Trace.getStackTrace(e));
		}
	}

	public static void remove(String fileName) {
		if (REMOVE) {
			File file = new File(fileName);
			if (!file.delete()) {
				log.info("failed to delete " + fileName);
			}
		}
	}

	public static String readFromUrl(InputStream is) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while (str != null) {
				str = in.readLine();
				sb.append(str);
			}
		} catch (IOException e) {
			log.error(Trace.getStackTrace(e));
		}
		return sb.toString();
	}
}
