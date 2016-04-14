package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class testExcel {

    public static void main(String[] args) {
        FileInputStream fis;
        String excelFilePath = "D:\\Users\\dprtenjak\\Desktop\\Projekti\\SOFTWARE\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";

        Workbook workbook;
        Sheet sheet;

        try {
            fis = new FileInputStream(new File(excelFilePath));

            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.println(cell.getStringCellValue());
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                    }

                    System.out.print(" | ");
                }

                System.out.println("");
                System.out.println("");
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            System.err.println("greška : ");
            System.err.println(e.getMessage());
        }

    }

}
