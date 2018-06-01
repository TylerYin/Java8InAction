package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class processImpex3 {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/lambdasinaction/chap3/needAddProfessional.impex"))) {
			int count = 0;
			FileWriter fileWriter = new FileWriter("D:\\needCasual.impex");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				String line;
				while (null != (line = br.readLine())) {
					count++;
					String productCodeEdmund = line.split(";")[1];
					boolean isFind = false;
					String line2;
					try (BufferedReader br1 = new BufferedReader(
							new FileReader("src/lambdasinaction/chap3/casual.impex"))) {
						while (null != (line2 = br1.readLine())) {
							String productCodeJack = line2.split("\\t")[0];
							String professionalValue = line2.split("\\t")[1];
							if (productCodeEdmund.trim().equals(productCodeJack.trim())) {
								isFind = true;

								int appearNumber = appearNumber(line, ";");
								if(line.contains("585212"))
								{
									System.out.println("==================="+appearNumber);
								}
								if (appearNumber < 12) {
									System.out.println(line);
									String needAddComm = " ;";
									for (int i = 0; i < 12 - appearNumber - 1; i++) {
										needAddComm += " ;";
									}
									line = line.concat(needAddComm);
								}

								if ("NULL".equals(professionalValue.trim())) {
									line = line.concat(" ;");
								} else if (professionalValue.trim().equals("1")) {
									line = line.concat("1;");
								} else {
									line = line.concat("0;");
								}
								break;
							}
						}
					}

					if (!isFind) {
						line = line.concat(" ;");
					}
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
			}
			System.out.println(count);
		}
	}

	private static int appearNumber(String srcText, String findText) {
		int count = 0;
		int index = 0;
		while ((index = srcText.indexOf(findText, index)) != -1) {
			index = index + findText.length();
			count++;
		}
		return count;
	}
}
