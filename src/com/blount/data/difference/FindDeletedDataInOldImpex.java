package com.blount.data.difference;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FindDeletedDataInOldImpex {
	public static void main(String... args) throws FileNotFoundException, IOException {
		FileWriter fileWriter = new FileWriter("src/com/blount/data/difference/DeletedDataImpex.txt");
		try (Stream<String> oldLines = Files.lines(Paths.get("src/com/blount/data/difference/OldImpex.txt"),
				Charset.defaultCharset())) {
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				oldLines.forEach(oldLine -> {
					try (Stream<String> newlines = Files.lines(Paths.get("src/com/blount/data/difference/NewImpex.txt"),
							Charset.defaultCharset())) {
						Optional<String> deletedDataCode = newlines
								.filter(newline -> newline.trim().equals(oldLine.trim())).findAny();
						if (!deletedDataCode.isPresent()) {
							bufferedWriter.newLine();
							bufferedWriter.write(oldLine);
							System.out.println(oldLine);
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