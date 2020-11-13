import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFromExcel {
    public void readExcel(Path filePath,String fileName,String sheetName) throws IOException {

        //Create an object of File class to open xlsx file
        File file = new File(filePath+"\\"+fileName);

        //Create an object of FileInputStream class to read startExcel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook startExcel = null;

        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
            startExcel = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of HSSFWorkbook class
            startExcel = new HSSFWorkbook(inputStream);
        }

        //Read sheet inside the workbook by its name
        Sheet sheetFromExcel = startExcel.getSheet(sheetName);

        Stream<String> stream = (Stream<String>) startExcel.getSheet(sheetName);

        //Find number of rows in startExcel file
        int rowCount = sheetFromExcel.getLastRowNum()-sheetFromExcel.getFirstRowNum();

        //Create a loop over all the rows of startExcel file to read it
        for (int i = 0; i < rowCount+1; i++) {
            Row row = sheetFromExcel.getRow(i);

            //Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++) {
                //Print Excel data in console
                System.out.print(row.getCell(j).getStringCellValue()+"|| ");
            }
            System.out.println();
        }

    }

    //Main function is calling readExcel function to read data from excel file

    public static void main(String...strings) throws IOException{

        //Create an object of ReadGuru99ExcelFile class
        ReadFromExcel objExcelFile = new ReadFromExcel();

        //Prepare the path of excel file
        Path getFilePath = Paths.get("C:\\workspace");

        //Call read file method of the class to read data
        objExcelFile.readExcel(getFilePath,"Book1.xlsx","Sheet1");

    }
}
