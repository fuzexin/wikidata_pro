package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import conn.DatabaseConnection;
import material.Entity;

public class ProgramEnter {
	public static void main(String[] args) {
		String path = "C://Users//fuzexin//Documents//研究生学习文档//现代数据库//wikidata.json";
		File file = new File(path);
		BufferedReader reader = null;
		DatabaseConnection connMySql = new DatabaseConnection();
		connMySql.getConn();
		int flag = 0;
		System.out.println("wikidata import processing!");
		System.out.println("please wait ..............................");
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			Long beginTime = System.currentTimeMillis();
			while ((tempString = reader.readLine()) != null) {
				if (Entity.isEntity(tempString)) {
					Entity tempEntity = new Entity();

					if (tempEntity.extract(tempString)) {
						if (connMySql.recieveEntity(tempEntity)) {
							flag++;
						}
					}
					if (flag >= 10000) {
						connMySql.insertIntoTable();
						flag = 0;
						Long endTime = System.currentTimeMillis();
						System.out.println("pst+batch：" + (endTime - beginTime) / 60000 + "分钟");
					}

				}
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
		if (flag > 0) {
			connMySql.insertIntoTable();
		}
		System.out.println("wikidata import from file json to MySQL database success!");

	}

}
