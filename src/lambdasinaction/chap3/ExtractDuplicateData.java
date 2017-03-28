package lambdasinaction.chap3;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractDuplicateData {
	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("src/lambdasinaction/chap3/OEMParts_032417.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

		// Read the Sheet
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		List<String> oemParts = new ArrayList<>();

		// Read the Row
		for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow != null) {
				XSSFCell oemName = xssfRow.getCell(0);
				XSSFCell oempartNumber = xssfRow.getCell(1);

				if (null == oemName || StringUtils.isBlank(getValue(oemName)) || null == oempartNumber
						|| StringUtils.isBlank(getValue(oempartNumber).trim())) {
					if (null == oemName || StringUtils.isBlank(getValue(oemName))) {
						System.out.println(getValue(oempartNumber));
					} else {
						System.out.println(getValue(oemName));
					}
				} else {
					oemParts.add(StringUtils.trim(getValue(oemName)).toLowerCase() + " - "
							+ StringUtils.trim(getValue(oempartNumber)).toLowerCase());
				}
			}
		}

		findDuplicateDatas(oemParts);
	}

	private static void findDuplicateDatas(List<String> oemParts) throws IOException {
		Long startTime = System.currentTimeMillis();
		List<String> duplicateDatas = new ArrayList<>();

		FileWriter fileWriter = new FileWriter("D:\\Duplicate_OEMParts_032417.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			Set<String> set = new HashSet<String>();

			for (String s : oemParts) {
				if (!set.add(s)) {
					duplicateDatas.add(s);
					bufferedWriter.write(s);
					bufferedWriter.newLine();
				}
			}
		}

		Long endTime = System.currentTimeMillis();
		System.out.println("Cost Time = " + (endTime - startTime));
		System.out.println("Duplicate Data Count = " + duplicateDatas.size());
	}

	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}
}
