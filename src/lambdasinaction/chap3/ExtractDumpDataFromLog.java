package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ExtractDumpDataFromLog {
	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/lambdasinaction/chap3/dumpLog.txt"))) {
			int count = 0;
			int notFoundLineCount = 0;
			FileWriter fileWriter = new FileWriter("src/lambdasinaction/chap3/extractedDumpData.txt");
			FileWriter formatWrongFileWriter = new FileWriter("src/lambdasinaction/chap3/formatWrongData.txt");
			try (BufferedWriter bufferedFormatWrongFileWriter = new BufferedWriter(formatWrongFileWriter)) {
				try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
					String line;
					while (null != (line = br.readLine())) {
						int startIndex = line.indexOf("resolve value '");
						if (startIndex > 0) {
							count++;
							startIndex += 15;
							int endIndex = line.indexOf("' for attribute");
							bufferedWriter.write(line.substring(startIndex, endIndex));
							bufferedWriter.newLine();
						} else {
							notFoundLineCount++;
							bufferedFormatWrongFileWriter.write(line);
							bufferedFormatWrongFileWriter.newLine();
						}
					}
				}
			}
			System.out.println("Extract Dumped Data Count = " + count);
			System.out.println("Not Found Line Count = " + notFoundLineCount);
		}
	}
}