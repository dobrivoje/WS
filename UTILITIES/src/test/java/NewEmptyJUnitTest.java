
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

public class NewEmptyJUnitTest {

    @Test
    public void test_Suma() {
        FileInputStream fis;
        String excelFilePath = "D:\\Users\\dprtenjak\\Desktop\\Projekti\\SOFTWARE\\WS\\Vanja Potrosnje\\vanja izvestaj NAV.xlsx";

        Workbook workbook;
        Sheet sheet;

        double suma = 0;

        try {
            fis = new FileInputStream(new File(excelFilePath));

            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);

            for (Row r : sheet) {
                for (Cell c : r) {
                    if (c.getCellType() == Cell.CELL_TYPE_NUMERIC && c.getColumnIndex() == 10) {
                        suma += c.getNumericCellValue();
                    }
                }
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            System.err.println("greška : ");
            System.err.println(e.getMessage());
        }

        System.err.println("Suma : " + Double.toString(suma));
        // Assert.assertEquals(64070792.26432997, suma);
    }
}
