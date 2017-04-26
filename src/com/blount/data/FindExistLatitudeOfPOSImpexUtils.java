package com.blount.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FindExistLatitudeOfPOSImpexUtils {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/com/blount/data/needToRefactor.impex"))) {
			
			int count = 0;
			StringBuilder addressBuilder = null;
			FileWriter fileWriter = new FileWriter("src/com/blount/data/refacted.impex");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				String line;
				while (null != (line = br.readLine())) {
					count++;

					addressBuilder = new StringBuilder();
					String[] posArray = line.split(";");
					for (int i = 1; i < posArray.length; i++) {
						if (i > 2 && i < 9) {
							continue;
						}
						addressBuilder.append(";").append(posArray[i]);
					}
					bufferedWriter.write(addressBuilder.append(";").toString());
					bufferedWriter.newLine();
//					System.out.println(addressBuilder.append(";").toString());
				}
			}
			System.out.println(count);
		}
	}
}
