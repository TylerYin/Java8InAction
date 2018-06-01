package lambdasinaction.chap3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void main(String... args) throws FileNotFoundException, IOException {
		final String filename = "inventory-22299.csv";
		Pattern pattern = Pattern.compile("^(inventory-)\\d+.csv");
		Matcher matcher = pattern.matcher(filename);
		System.out.println("This Regex pattern verify result = " + matcher.matches());
	}
}