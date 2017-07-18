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

public class ExtractAddressDataFromDealerExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream("src/com/blount/data/dealerlocator/DealerExtract_042417.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		long count = -1;
		String addressLine = StringUtils.EMPTY;
		StringBuilder addressBuilder = null;
		FileWriter fileWriter = new FileWriter("src/com/blount/data/dealerlocator/AddressOfDealer.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					count++;
					addressBuilder = new StringBuilder(";");

					String posName = getValue(xssfRow.getCell(0));
					if (StringUtils.isBlank(posName)) {
						continue;
					}

					String firstName = getValue(xssfRow.getCell(8));
					String lastName = getValue(xssfRow.getCell(9));
					String phone = getValue(xssfRow.getCell(10));
					String fax = getValue(xssfRow.getCell(11));
					String email = getValue(xssfRow.getCell(12));

					String line1 = getValue(xssfRow.getCell(15));
					String line2 = getValue(xssfRow.getCell(16));
					String city = getValue(xssfRow.getCell(17));
					String state = "US-" + getValue(xssfRow.getCell(18));
					String zipCode = getValue(xssfRow.getCell(19));
					String country = getValue(xssfRow.getCell(20));
					String recid = getValue(xssfRow.getCell(21));

					String addrID = "addr" + count;

					addressLine = addressBuilder.append(firstName).append(";").append(lastName).append(";")
							.append(email).append(";").append(phone).append(";").append(fax).append(";").append(zipCode)
							.append(";").append(line2).append(";").append(line1).append(";").append(city).append(";")
							.append(state).append(";").append(country).append(";").append(addrID).append(";")
							.append(recid).toString();

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
