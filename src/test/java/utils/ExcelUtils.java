package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private File fileFromClassPath;

    //Excel WorkBook
    private XSSFWorkbook excelWBook;

    //Excel Sheet
    private XSSFSheet excelWSheet;

    //Excel cell
    private XSSFCell cell;

    //Excel row
    private XSSFRow row;

    private Map<String, Integer> columnMap;


    /**
     * @param relativePath path relative to src\test\resources .
     * @param sheetName
     */
    public void init(String relativePath, String sheetName) {

        fileFromClassPath = ProjectUtil.getFileFromClassPath(relativePath);

        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(fileFromClassPath);
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e) {
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        columnMap = new HashMap<>(); //Create map
        XSSFRow row = excelWSheet.getRow(0);  //Get first row
        short minColIx = row.getFirstCellNum(); //get the first column index for a row
        short maxColIx = row.getLastCellNum(); //get the last column index for a row
        for (short colIx = minColIx; colIx < maxColIx; colIx++) { //loop from first to last index
            XSSFCell cell = row.getCell(colIx); //get the cell
            columnMap.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
    }

    //This method reads the test testdata from the Excel cell.
    //We are passing row number and column number as parameters.
    public String getCellData(int RowNum, int ColNum) {
        cell = excelWSheet.getRow(RowNum).getCell(ColNum);
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    //This method takes row number as a parameter and returns the testdata of given row number.
    public XSSFRow getRowData(int RowNum) {
        try {
            row = excelWSheet.getRow(RowNum);
            return row;
        } catch (Exception e) {
            throw (e);
        }
    }

    public void writeExcel(List<SearchResults> searchResultsList, String excelFilePath) {
        Workbook wrkbook = new XSSFWorkbook();
        Sheet sht = wrkbook.createSheet();

        int rowCount = 0;
        for (SearchResults result : searchResultsList) {
            Row row = sht.createRow(rowCount++);
            writeSearchResults(result, row);
        }

        try(FileOutputStream outputStream = new FileOutputStream(excelFilePath)){
            wrkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeSearchResults(SearchResults result, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(result.getTitle());

        cell = row.createCell(1);
        cell.setCellValue(result.getPrice());

        cell = row.createCell(2);
        cell.setCellValue(result.getAddress());

        cell = row.createCell(3);
        cell.setCellValue(result.getInfo());
    }


}