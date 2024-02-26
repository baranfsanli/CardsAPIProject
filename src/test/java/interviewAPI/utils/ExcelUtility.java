package interviewAPI.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	private static Workbook book;
	private static Sheet sheet;

	private static void openExcel(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			book = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadSheet(String sheetName) {
		sheet = book.getSheet(sheetName);
	}

	private static int rowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	private static int columnCount(int rowIndex) {
		return sheet.getRow(rowIndex).getLastCellNum();
	}

	private static String cellData(int rowIndex, int columnIndex) {
		return sheet.getRow(rowIndex).getCell(columnIndex).toString();
	}

	// Return excel sheet data into a 2D-Object Array
	public static Object[][] excelIntoArray(String filePath, String sheetName) {
		openExcel(filePath);
		loadSheet(sheetName);
		int rowNumber = rowCount();
		int columnNumber = columnCount(0);
		Object[][] data = new Object[rowNumber - 1][columnNumber];

		// iterate over rows
		for (int row = 1; row < rowNumber; row++) {
			// iterate over columns
			for (int col = 0; col < columnNumber; col++) {
				// what is the value of row in the first iteration?? Ans: 1
				// is this the first time I am adding info to the data 2D array? Yes
				// then I want to add the info at index 0, not index 1 ---> row - 1 = 0
				data[row - 1][col] = cellData(row, col);
			}
		}

		// return the 2D array object
		return data;
	}

	
	
	public static List<Map<String, String>> excelIntoListOfMaps(String filePath, String sheetName) {

		openExcel(filePath);
		loadSheet(sheetName);

		List<Map<String, String>> list = new ArrayList<>();

		// iterate over rows
		for (int row = 1; row < rowCount(); row++) {
			Map<String, String> rowMap = new LinkedHashMap<>();
			// iterate over columns: fill the map
			for (int col = 0; col < columnCount(row); col++) {
				String key = cellData(0, col);
				String value = cellData(row, col);
				rowMap.put(key, value);
			}
			// add the map to the list
			list.add(rowMap);
		}

		return list;

	}

}
