package lambdasinaction.chap3;

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
		try (Stream<String> oemModelLines = Files.lines(Paths.get("src/lambdasinaction/chap3/OEMModel1.txt"),
				Charset.defaultCharset())) {
			FileWriter fileWriter = new FileWriter("src/lambdasinaction/chap3/OEMModel_Duplicate.txt");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				oemModelLines.forEach(productCode -> {
					try (Stream<String> product2OEMModellines = Files.lines(
							Paths.get("src/lambdasinaction/chap3/OEMModel2.txt"), Charset.defaultCharset())) {
						long appearTimes = product2OEMModellines
								.filter(oemProduct2OEMModel -> oemProduct2OEMModel.trim().equals(productCode.trim())).count();
						if (appearTimes > 1) {
							bufferedWriter.newLine();
							bufferedWriter.write(productCode);
							System.out.println(productCode);
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