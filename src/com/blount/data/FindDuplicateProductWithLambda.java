package com.blount.data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FindDuplicateProductWithLambda {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (Stream<String> oemModelLines = Files.lines(Paths.get("src/lambdasinaction/chap3/Product2OEMModel1.impex"),
				Charset.defaultCharset())) {
			FileWriter fileWriter = new FileWriter("src/lambdasinaction/chap3/DuplicateMoreThan100OEMPart.txt");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				oemModelLines.forEach(productCode -> {
					try (Stream<String> product2OEMModellines = Files.lines(
							Paths.get("src/lambdasinaction/chap3/Product2OEMModel3.impex"), Charset.defaultCharset())) {
						long appearTimes = product2OEMModellines
								.filter(oemProduct2OEMModel -> oemProduct2OEMModel.equals(productCode.trim())).count();
						if (appearTimes > 100) {
							String outputLine = "Product code = " + productCode + ", appear times : " + appearTimes;
							bufferedWriter.newLine();
							bufferedWriter.write(outputLine);
							System.out.println(outputLine);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}