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

public class FindAddedDataInNewImpex {
	public static void main(String... args) throws FileNotFoundException, IOException {
		FileWriter fileWriter = new FileWriter("src/com/blount/data/difference/AddedDataImpex.txt");
		try (Stream<String> newLines = Files.lines(Paths.get("src/com/blount/data/difference/NewImpex.txt"),
				Charset.defaultCharset())) {
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				newLines.forEach(newLine -> {
					try (Stream<String> oldlines = Files.lines(Paths.get("src/com/blount/data/difference/OldImpex.txt"),
							Charset.defaultCharset())) {
						Optional<String> addedDataCode = oldlines
								.filter(oldline -> oldline.trim().equals(newLine.trim())).findAny();
						if (!addedDataCode.isPresent()) {
							bufferedWriter.newLine();
							bufferedWriter.write(newLine);
							System.out.println(newLine);
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
