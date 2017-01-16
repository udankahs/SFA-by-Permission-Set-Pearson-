package com.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/* Owner 			: Udanka H S
 * Email Id			: udanka.hs@cognizant.com
 * Department 		: QEA CRM
 * Organization		: Cognizant Technology Solutions
 */

public class ExcelLib {
	public static int getRowCountofColumn(String xlPath, String sheetName, int ColumnNumber) {

		try {
			String data;
			InputStream is = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet(sheetName);
			Iterator rowIter = sheet.rowIterator();
			Row r = (Row) rowIter.next();
			short lastCellNum = r.getLastCellNum();
			int[] dataCount = new int[lastCellNum];
			int col = 0;
			rowIter = sheet.rowIterator();
			while (rowIter.hasNext()) {
				Iterator cellIter = ((Row) rowIter.next()).cellIterator();
				while (cellIter.hasNext()) {
					Cell cell = (Cell) cellIter.next();
					col = cell.getColumnIndex();
					dataCount[col] += 1;
					DataFormatter df = new DataFormatter();
					data = df.formatCellValue(cell);
				}
			}
			is.close();
			return dataCount[ColumnNumber];

			// FOR(COLUMNNUMBER = 0; COLUMNNUMBER < DATACOUNT.LENGTH;
			// COLUMNNUMBER++) {
			// SYSTEM.OUT.PRINTLN("COL " + COLUMNNUMBER + ": " +
			// DATACOUNT[COLUMNNUMBER]);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int getRowCount(String xlPath, String sheetName) {
		try {
			FileInputStream fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s1 = wb.getSheet(sheetName);
			int rc = s1.getPhysicalNumberOfRows();
			return rc;
		} catch (Exception e) {
			return -1;
		}
	}

	public static String getCellValue(String xlPath, String sheetName, int rowNum, int cellNum) {
		try {
			String u = new DataFormatter().formatCellValue(WorkbookFactory.create(new FileInputStream(xlPath)).getSheet(sheetName).getRow(rowNum).getCell(cellNum));
			return u;
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}

	public static void writeExcel(String output_Path, String sheetName, int roww, int coll, String Valuee)
			throws Exception {
		FileInputStream fis = new FileInputStream(output_Path);
		Workbook wb = WorkbookFactory.create(fis);
		FileOutputStream file1 = new FileOutputStream(output_Path);
		Sheet s1 = wb.getSheet(sheetName);
		org.apache.poi.ss.usermodel.Row rownum1 = s1.getRow(roww);
		rownum1.createCell(coll).setCellValue(Valuee);
		wb.write(file1);
		file1.close();
	}

	public static int getColCount(String xlPath, String sheetName, int row) {
		try {
			FileInputStream fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s1 = wb.getSheet(sheetName);
			int rc = s1.getRow(row).getPhysicalNumberOfCells();
			return rc;
		} catch (Exception e) {
			return -1;
		}

	}

	public static boolean isFileExists(String filePath, String fileName) {
		boolean flag = false;
		File dir = new File(filePath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}

		return flag;
	}
}
