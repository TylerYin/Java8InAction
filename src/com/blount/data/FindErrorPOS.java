package com.blount.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindErrorPOS {
	public static void main(String... args) throws FileNotFoundException, IOException {
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("src/com/blount/data/FindErrorPOS.impex"))) {
			String line = null;
			while (null != (line = br.readLine())) {
				if (line.startsWith(";#")) {
					count++;
				}
			}
		}
		System.out.println(count);
	}
}