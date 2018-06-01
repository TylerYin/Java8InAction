package com.blount.data.difference;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractProductMissingUOMFromExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		FileWriter fileWriter = new FileWriter("src/com/blount/data/difference/ExtractedDataImpex.txt");
		try (Stream<String> newLines = Files.lines(Paths.get("src/com/blount/data/difference/NewImpex.txt"),
				Charset.defaultCharset())) {
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				newLines.forEach(newLine -> {
					InputStream is;
					try {
						is = new FileInputStream("src/com/blount/data/difference/ProductBasicAttributes_051917.xlsx");
						XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
						XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
						for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
							XSSFRow xssfRow = xssfSheet.getRow(rowNum);
							if (xssfRow != null) {
								String code = getValue(xssfRow.getCell(0));
								if (code.trim().equals(newLine.trim())) {
									String addressLine = "";
									String size = getValue(xssfRow.getCell(2));
									String teeth = getValue(xssfRow.getCell(3));
									addressLine = addressLine.concat(";").concat(code).concat(";").concat(size)
											.concat(";").concat(teeth);
									bufferedWriter.newLine();
									bufferedWriter.write(addressLine);
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	private static String getValue(XSSFCell xssfCell) {
		String xssFCellValue = StringUtils.EMPTY;
		if (xssfCell != null) {
			if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				xssFCellValue = String.valueOf(xssfCell.getBooleanCellValue());
			} else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				xssFCellValue = String.valueOf(xssfCell.getNumericCellValue());
			} else {
				xssFCellValue = String.valueOf(xssfCell.getStringCellValue());
			}

			if (StringUtils.isNotBlank(xssFCellValue)) {
				return xssFCellValue.trim();
			}
		}
		return StringUtils.EMPTY;
	}
}
