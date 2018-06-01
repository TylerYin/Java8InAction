package com.blount.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class RefactorPOSImpexUtils {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/com/blount/data/PointOfService.impex"))) {

			int count = 0;
			FileWriter fileWriter = new FileWriter("src/com/blount/data/POSImpex.impex");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				String line;
				while (null != (line = br.readLine())) {
					String[] posArray = line.split(";");
					if (posArray.length == 4
							&& (StringUtils.isNotEmpty(posArray[2]) || StringUtils.isNotEmpty(posArray[3]))) {
						count++;
						bufferedWriter.newLine();
						bufferedWriter.write(line);
					} else {
						System.out.println(line);
					}
				}
			}
			System.out.println(count);
		}
	}
}