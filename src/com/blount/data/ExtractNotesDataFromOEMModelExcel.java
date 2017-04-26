package com.blount.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractNotesDataFromOEMModelExcel {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream("src/com/blount/data/ProductNotes_040617.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		long count = 0;
		long notFoundCount =0 ;
		String oemName = StringUtils.EMPTY;
		String oemModel = StringUtils.EMPTY;
		String oemNotes = StringUtils.EMPTY;
		String oemModelCode = StringUtils.EMPTY;

		StringBuilder oemModelCodeBuilder = null;

		FileWriter fileWriter = new FileWriter("src/com/blount/data/OEMNotesModel.impex");
		try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					oemModelCodeBuilder = new StringBuilder();

					oemName = getValue(xssfRow.getCell(1));
					oemModel = getValue(xssfRow.getCell(2));
					oemNotes = getValue(xssfRow.getCell(3));

					oemModelCode = oemModelCodeBuilder.append(oemName.toLowerCase()).append(" - ")
							.append(oemModel.toLowerCase()).toString();
					String oemModelNotesLine = "";
					oemModelNotesLine = oemModelNotesLine.concat(";").concat(oemModelCode).concat(";").concat(oemNotes);

					if (findExistOEMModel(oemModelCode)) {
						count++;
						bufferedWriter.newLine();
						bufferedWriter.write(oemModelNotesLine);
					} else {
						notFoundCount++;
						System.out.println(oemModelNotesLine);
					}
				}
			}
			System.out.println("Fount Count:" + count);
			System.out.println("Not Fount Count:" + notFoundCount);
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

	private static boolean findExistOEMModel(String oemModelCode) {
		String line = StringUtils.EMPTY;
		boolean isExist = false;
		try (BufferedReader br = new BufferedReader(new FileReader("src/com/blount/data/OEMModelModel.impex"))) {
			while (null != (line = br.readLine())) {
				String oemModelCode1 = line.split(";")[3].trim();
				if (oemModelCode1.equals(oemModelCode)) {
					isExist = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isExist;
	}
}
