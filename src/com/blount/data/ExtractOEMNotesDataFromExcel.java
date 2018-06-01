package com.blount.data;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractOEMNotesDataFromExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream("src/com/blount/data/ProductNotes_040617.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		long count = -1;
		String addressLine = StringUtils.EMPTY;
		StringBuilder addressBuilder = null;
		FileWriter fileWriter = new FileWriter("src/com/blount/data/ProductNotes_040617.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					count++;
					addressBuilder = new StringBuilder(";");

					String partNumber = getValue(xssfRow.getCell(0)).trim();
					String oemName = getValue(xssfRow.getCell(1)).trim().toLowerCase();
					String oemModel = getValue(xssfRow.getCell(2)).trim().toLowerCase();
					String note = getValue(xssfRow.getCell(3));

					addressLine = addressBuilder.append(partNumber).append(";").append(oemName).append(" - ")
							.append(oemModel).append(";").append(note).append(";").toString();
					bufferedWriter.newLine();
					bufferedWriter.write(addressLine);
				}
			}
			System.out.println(count);
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
