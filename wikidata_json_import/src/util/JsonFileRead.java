package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class JsonFileRead {
	/*
	 * @author JohnsonFu
	 * 
	 * @date 11-1-2017
	 * 
	 */
	public static String readFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String currentString = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				currentString = currentString + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return currentString;
	}
}