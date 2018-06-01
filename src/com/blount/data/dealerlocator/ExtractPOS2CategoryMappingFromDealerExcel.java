package com.blount.data.dealerlocator;

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

public class ExtractPOS2CategoryMappingFromDealerExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[] categoryNames = { "oep", "forestry", "harvester", "cordless", "logSplitter" };

		InputStream is = new FileInputStream("src/com/blount/data/dealerlocator/DealerExtract_042417.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		for (int i = 0; i < categoryNames.length; i++) {
			FileWriter fileWriter = new FileWriter("src/com/blount/data/dealerlocator/" + "20170613_" + categoryNames[i] + ".impex");
			try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						String posName = getValue(xssfRow.getCell(0));
						String recid = getValue(xssfRow.getCell(21));

						if (StringUtils.isNotBlank(posName) && xssfRow.getCell(2 + i) != null
								&& getValue(xssfRow.getCell(2 + i)).trim().equalsIgnoreCase("Y")) {
							bufferedWriter.newLine();
							bufferedWriter.write(";" + recid + ";" + categoryNames[i]);
						}
					}
				}
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
