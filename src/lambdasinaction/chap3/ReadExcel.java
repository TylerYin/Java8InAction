package lambdasinaction.chap3;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream("src/lambdasinaction/chap3/ProductOEMModels_032417.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}

			String line;
			FileWriter fileWriter = new FileWriter("D:\\Difference.impex");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				// Read the Row
				for (int rowNum = 1; rowNum <= 20; rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						XSSFCell partNumber = xssfRow.getCell(0);
						XSSFCell oemName = xssfRow.getCell(1);
						XSSFCell oemPartNumber = xssfRow.getCell(2);

						line = ";" + getValue(partNumber) + ";" + getValue(oemName).toLowerCase() + " - "
								+ getValue(oemPartNumber).toLowerCase();
						// bufferedWriter.write(line);
						// bufferedWriter.newLine();
						System.out.println(line);
					}
				}
			}
		}
	}

	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}
}
