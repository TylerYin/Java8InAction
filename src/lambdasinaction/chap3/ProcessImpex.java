package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessImpex {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/lambdasinaction/chap3/bulletPoint.impex"))) {
			int count = 0;
			FileWriter fileWriter = new FileWriter("D:\\bulletPoint.impex2");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				String line;
				int num = 10000;
				while (null != (line = br.readLine())) {
					count++;

					String productCode = line.split(";")[1];
					String bulletPoint = line.split(";")[2];

					System.out.println(productCode);
					String[] bulletPoints = bulletPoint.split("\\|");

					String bulletPointLine = "";
					for (int i = 0; i < bulletPoints.length; i++) {
						if (i == 0) {
							bulletPointLine = bulletPoints[i].substring(1, bulletPoints[i].length());
						} else if (i == bulletPoints.length - 1) {
							bulletPointLine = bulletPoints[i].substring(0, bulletPoints[i].length() - 1);
						} else {
							bulletPointLine = bulletPoints[i];
						}
						
						if(bulletPointLine.startsWith("\"") && bulletPointLine.endsWith("\"")){
							bulletPointLine = bulletPointLine.substring(2, bulletPoints[i].length()-2);
						}
						bulletPointLine = ";" + num++ + ";" + productCode + ";" + bulletPointLine;

						bufferedWriter.write(bulletPointLine);
						bufferedWriter.newLine();
						System.out.println(bulletPointLine);
					}
				}
			}
			System.out.println(count);
		}
	}
}
