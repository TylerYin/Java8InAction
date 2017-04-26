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
		InputStream is = new FileInputStream("src/lambdasinaction/chap3/DealerExtract_041717.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		long count = -1;
		String addressLine = StringUtils.EMPTY;
		StringBuilder addressBuilder = null;
		FileWriter fileWriter = new FileWriter("src/lambdasinaction/chap3/pointOfService.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					count++;
					addressBuilder = new StringBuilder(";");

					String posName = getValue(xssfRow.getCell(0));
					String retailStoreFrontYN = getBooleanValue(getValue(xssfRow.getCell(1)));
					String oep = getBooleanValue(getValue(xssfRow.getCell(2)));
					String forestry = getBooleanValue(getValue(xssfRow.getCell(3)));
					String harvester = getBooleanValue(getValue(xssfRow.getCell(4)));
					String cordless = getBooleanValue(getValue(xssfRow.getCell(5)));
					String logSplitter = getBooleanValue(getValue(xssfRow.getCell(6)));
					String dealerType = getValue(xssfRow.getCell(7));

					String website = getValue(xssfRow.getCell(13));
					String approvedWebsite = getValue(xssfRow.getCell(14));
					
					String posType = "STORE";
					String addrID = "addr" + count;

					addressLine = addressBuilder.append(posName).append(";").append(posName).append(";")
							.append(retailStoreFrontYN).append(";").append(oep).append(";").append(forestry).append(";")
							.append(harvester).append(";").append(cordless).append(";").append(logSplitter).append(";")
							.append(dealerType).append(";").append(posType).append(";").append(website)
							.append(";").append(approvedWebsite).append(";").append(addrID).append(";")
							.toString();
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

	private static String getBooleanValue(String booleanValue) {
		if (StringUtils.isEmpty(booleanValue)) {
			return "";
		}
		if ("Y".equals(booleanValue)) {
			return "1";
		}
		return "0";
	}
}
