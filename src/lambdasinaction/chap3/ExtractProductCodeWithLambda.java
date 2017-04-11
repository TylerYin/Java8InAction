package lambdasinaction.chap3;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ExtractProductCodeWithLambda {
	public static void main(String... args) throws FileNotFoundException, IOException {
		FileWriter fileWriter = new FileWriter("src/lambdasinaction/chap3/Product2OEMModel3.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			try (Stream<String> product2OEMModellines = Files.lines(
					Paths.get("src/lambdasinaction/chap3/product-oem-part-relation.impex"), Charset.defaultCharset())) {
				product2OEMModellines.map(product2OEMModelline -> product2OEMModelline.split(";")[1])
						.forEach(productCode -> {
							try {
								bufferedWriter.newLine();
								bufferedWriter.write(productCode.trim());
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}