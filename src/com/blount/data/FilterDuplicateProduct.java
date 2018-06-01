package com.blount.data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilterDuplicateProduct {
	public static void main(String... args) throws FileNotFoundException, IOException {
		FileWriter fileWriter = new FileWriter("src/com/blount/data/FilteredProducts.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			try (Stream<String> products = Files.lines(
					Paths.get("src/com/blount/data/FilterProducts.impex"),
					Charset.defaultCharset())) {
				products.distinct().forEach(product -> {
					try {
						bufferedWriter.newLine();
						bufferedWriter.write(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}