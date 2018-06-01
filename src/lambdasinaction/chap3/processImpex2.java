package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class processImpex2 {
	public static void main(String... args) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("src/lambdasinaction/chap3/Edmund.impex"))) {
			int count = 0;
			FileWriter fileWriter = new FileWriter("D:\\needAddClassification.impex2");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				String line;
				while (null != (line = br.readLine())) {
					count++;
					String productCodeEdmund = line.split(";")[1];
					boolean isFind=false;
					String line2;
					try (BufferedReader br1 = new BufferedReader(new FileReader("src/lambdasinaction/chap3/Jack.impex"))) {
						while (null != (line2 = br1.readLine())) {
							String productCodeJack = line2.split(";")[1];
							if(productCodeEdmund.trim().equals(productCodeJack.trim()))
							{
								isFind=true;
								break;
							}
						}
					}
					
					if(!isFind){
						bufferedWriter.write(line);
						bufferedWriter.newLine();
					}
				}
			}
			System.out.println(count);
		}
	}
}
