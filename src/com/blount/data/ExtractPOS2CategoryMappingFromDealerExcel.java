package com.blount.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class ExtractPOS2CategoryMappingFromDealerExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] categoryNames = { "retailStoreFront", "oep", "forestry", "harvester", "cordless", "logSplitter" };

		for (int i = 0; i < categoryNames.length; i++) {
			try (BufferedReader br = new BufferedReader(new FileReader("src/com/blount/data/needToRefactor.impex"))) {
				int count = 0;
				StringBuilder addressBuilder = null;
				FileWriter fileWriter = new FileWriter("src/com/blount/data/" + categoryNames[i] + ".impex");
				try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
					String line;
					while (null != (line = br.readLine())) {
						String[] posArray = line.split(";");
						for (int k = 1; k < posArray.length; k++) {
							if (k == 3 + i) {
								if (StringUtils.isNotEmpty(posArray[k]) && posArray[k].equals("1")) {
									addressBuilder = new StringBuilder(";");
									addressBuilder.append(posArray[1]).append(";").append(categoryNames[i]);

									count++;
									// System.out.println(addressBuilder.toString());
									bufferedWriter.write(addressBuilder.toString());
									bufferedWriter.newLine();
								}
							}
						}
					}
				}
				System.out.println(categoryNames[i] + " =" + count);
			}
		}
	}
}
