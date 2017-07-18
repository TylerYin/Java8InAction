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

public class ExtractPOSDataFromDealerExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream("src/com/blount/data/DealerExtract_042417.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		long count = -1;
		String addressLine = StringUtils.EMPTY;
		StringBuilder addressBuilder = null;
		FileWriter fileWriter = new FileWriter("src/com/blount/data/pointOfService.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					count++;
					addressBuilder = new StringBuilder();

					String posName = getValue(xssfRow.getCell(0));
					if (StringUtils.isBlank(posName)) {
						continue;
					}

					String recid = getValue(xssfRow.getCell(21));

					addressLine = addressBuilder
							.append(recid)
							.append(";").append(posName.trim()).toString();
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
